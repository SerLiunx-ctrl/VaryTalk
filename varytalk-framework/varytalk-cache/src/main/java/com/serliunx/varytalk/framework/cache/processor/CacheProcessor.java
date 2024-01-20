package com.serliunx.varytalk.framework.cache.processor;

import com.serliunx.varytalk.framework.cache.annotation.Cache;
import com.serliunx.varytalk.framework.cache.annotation.CacheRefresh;
import com.serliunx.varytalk.framework.cache.annotation.TagEntity;
import com.serliunx.varytalk.framework.cache.annotation.TagValue;
import com.serliunx.varytalk.framework.cache.support.FieldIndexHolder;
import com.serliunx.varytalk.framework.cache.support.ValueIndexHolder;
import com.serliunx.varytalk.framework.core.tool.AopUtils;
import com.serliunx.varytalk.framework.core.tool.RedisUtils;
import com.serliunx.varytalk.framework.core.tool.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存注解处理器
 * @author SerLiunx
 * @since 1.0
 */
@Aspect
@Component
@SuppressWarnings("all")
public class CacheProcessor {

    private static final Set<Class<?>> PRIMITIVE_CLASSES = new HashSet<>();
    private static final String REFRESH_TAG = "REFRESH";
    private static final String TAG_VALUE = "VERSION OF THIS DATA IS OUT OF DATE!";
    private final Map<Method, FieldIndexHolder> entityFields = new HashMap<>(32);
    private final Map<Method, ValueIndexHolder> valueFields = new HashMap<>(32);

    /**
     * 缓存key前缀
     */
    @Value("${talk-system.redis-prefix.main-prefix}")
    private String PREFIX;
    private static final String KEY_DELIMITER = ":";
    private final RedisUtils redisUtils;

    static{
        PRIMITIVE_CLASSES.add(String.class);
        PRIMITIVE_CLASSES.add(Integer.class);
        PRIMITIVE_CLASSES.add(Long.class);
        PRIMITIVE_CLASSES.add(Double.class);
        PRIMITIVE_CLASSES.add(Float.class);
        PRIMITIVE_CLASSES.add(Byte.class);
        PRIMITIVE_CLASSES.add(Short.class);
        PRIMITIVE_CLASSES.add(int.class);
        PRIMITIVE_CLASSES.add(long.class);
        PRIMITIVE_CLASSES.add(double.class);
        PRIMITIVE_CLASSES.add(float.class);
        PRIMITIVE_CLASSES.add(byte.class);
        PRIMITIVE_CLASSES.add(short.class);
    }

    public CacheProcessor(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @Around("@annotation(com.serliunx.varytalk.framework.cache.annotation.CacheRefresh)")
    public Object processCachePrefresh(ProceedingJoinPoint joinPoint){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        CacheRefresh cacheRefresh = method.getAnnotation(CacheRefresh.class);
        try{
            String cacheKey = cacheRefresh.value();
            String key = cacheKey.isEmpty() ? generateKey(joinPoint, cacheRefresh.method()) : cacheKey;
            String tag = null;
            if(hasTag(method)){
                tag = gennerateTag(joinPoint.getArgs(), method);
            }
            //匹配到了tag, 代表着该方法需要使用tag区别不同数据
            if(tag != null){
                key = new StringBuilder(key).append(KEY_DELIMITER).append(tag).toString();
            }
            //检测缓存中是否存在该键值, 没有该键值不会放置更新标记
            if(redisUtils.get(key) == null){
                return joinPoint.proceed();
            }
            key = key + KEY_DELIMITER + REFRESH_TAG;
            //放置更新标记
            redisUtils.put(key, TAG_VALUE);
            return joinPoint.proceed();
        }catch (Throwable t){
            t.printStackTrace();
            throw new RuntimeException(t.getMessage());
        }
    }

    @Around("@annotation(com.serliunx.varytalk.framework.cache.annotation.Cache)")
    public Object processCache(ProceedingJoinPoint joinPoint){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        Cache cache = method.getAnnotation(Cache.class);
        try{
            String cacheKey = cache.value();
            int time = cache.time();
            TimeUnit timeUnit = cache.timeUnit();
            String key = cacheKey.isEmpty() ? generateKey(joinPoint, signature) : cacheKey;
            String tag = null;
            if(hasTag(method)){
                tag = gennerateTag(joinPoint.getArgs(), method);
            }
            //匹配到了tag, 代表着该方法需要使用tag区别不同数据
            if(tag != null){
                key = new StringBuilder(key).append(KEY_DELIMITER).append(tag).toString();
            }
            //检查是否需要更新缓存
            String updateKey = key + KEY_DELIMITER + REFRESH_TAG;
            if(redisUtils.get(updateKey) != null){
                Object object = joinPoint.proceed();
                redisUtils.put(key, object, time, timeUnit);
                redisUtils.delete(updateKey);
                return object;
            }
            Object cacheData = getCache(key);
            Object result = null;
            if(cacheData != null){
                if (cache.forceRefresh()){
                    result = joinPoint.proceed();
                    //放入缓存
                    redisUtils.put(key, result, time, timeUnit);
                    return result;
                }
                return cacheData;
            }else{
                result = joinPoint.proceed();
                //放入缓存
                redisUtils.put(key, result, time, timeUnit);
            }
            return result;
        }catch (Throwable t){
            t.printStackTrace();
            throw new RuntimeException(t.getMessage());
        }
    }

    /**
     * 根据方法和类型生成缓存中的键值
     */
    private String generateKey(ProceedingJoinPoint point, MethodSignature signature){
        StringBuilder keyBuilder = new StringBuilder(PREFIX);
        keyBuilder.append(camelToUnderline(point.getTarget().getClass().getSimpleName()) + KEY_DELIMITER);
        keyBuilder.append(camelToUnderline(signature.getMethod().getName()));
        return keyBuilder.toString();
    }

    /**
     * 根据方法名和类型生成缓存中的键值
     */
    private String generateKey(ProceedingJoinPoint point, String methodName){
        StringBuilder keyBuilder = new StringBuilder(PREFIX);
        keyBuilder.append(camelToUnderline(point.getTarget().getClass().getSimpleName()) + KEY_DELIMITER);
        keyBuilder.append(camelToUnderline(methodName));
        return keyBuilder.toString();
    }

    /**
     * 根据指定方法中的参数是否有标注了TagEntity和TabValue注解
     */
    private boolean hasTag(Method method){
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            if(parameter.getAnnotation(TagEntity.class) != null ||
                    parameter.getAnnotation(TagValue.class) != null){
                return true;
            }
        }
        return false;
    }

    /**
     * 根据方法参数生成键值中的tag，用于区分同一个方法的缓存。如不同用户的全部关注用户
     */
    private String gennerateTag(Object[] args, Method method){
        //检查内存中字段的缓存
        FieldIndexHolder fieldIndexHolder = entityFields.get(method);
        if(fieldIndexHolder != null){
            return fieldIndexHolder.getTagValue().value() + KEY_DELIMITER + args[fieldIndexHolder.getIndex()].toString();
        }
        ValueIndexHolder valueIndexHolder = valueFields.get(method);
        if(valueIndexHolder != null){
            return valueIndexHolder.getTagValue().value() + KEY_DELIMITER + args[valueIndexHolder.getIndex()].toString();
        }

        Parameter[] parameters = method.getParameters();
        TagValue tagValue = null;
        TagEntity tagEntity = null;
        //寻找注解
        int index = 0;
        for (Parameter parameter : parameters) {
            if(parameter.getAnnotation(TagValue.class) != null
                    || parameter.getAnnotation(TagEntity.class) != null){

                tagValue = parameter.getAnnotation(TagValue.class);
                tagEntity = parameter.getAnnotation(TagEntity.class);
                break;
            }
            index++;
        }
        Class<?>[] classes = AopUtils.getClassArray(args);
        //处理原始类型的参数值
        if(tagValue != null){
            if(isPrimitive(classes[index])){
                try {
                    ValueIndexHolder holder = new ValueIndexHolder()
                            .setTagValue(tagValue)
                            .setIndex(index);
                    valueFields.put(method, holder);
                    return tagValue.value() + KEY_DELIMITER + args[index].toString();
                }catch (Exception e){
                    throw new RuntimeException(e.getMessage());
                }
            }else {
                String msg = String.format("%s 不是原始类型! 不能使用注解 %s", classes[index].getName(),
                        TagValue.class.getName());
                throw new RuntimeException(msg);
            }
        }
        //处理一般(复杂)类型的参数值
        if(tagEntity != null){
            Class<?> clazz = classes[index];
            Field[] fields = clazz.getDeclaredFields();
            Field fieldMatched = null;
            TagValue entityTagValue = null;
            for (Field field : fields) {
                if(field.getAnnotation(TagValue.class) != null){
                    fieldMatched = field;
                    fieldMatched.setAccessible(true);
                    entityTagValue = field.getAnnotation(TagValue.class);
                    break;
                }
            }
            if(fieldMatched == null){
                String msg = String.format("复杂类型使用了注解 %s 后, 它的属性中必须有一个字段要使用注解 %s 标记!",
                        TagEntity.class.getName(), TagValue.class.getName());
                throw new RuntimeException(msg);
            }
            //判断TagValue注解所标注的属性是不是原始类型及其包装类
            if (!isPrimitive(fieldMatched.getType())) {
                String msg = String.format("%s 不是原始类型! 不能使用注解 %s", classes[index].getName(),
                        TagValue.class.getName());
                throw new RuntimeException(msg);
            }
            try {
                FieldIndexHolder holder = new FieldIndexHolder()
                        .setField(fieldMatched)
                        .setIndex(index)
                        .setTagValue(entityTagValue);
                entityFields.put(method, holder);
                Object data = fieldMatched.get(args[index]);
                return data != null ? (entityTagValue.value() + KEY_DELIMITER + data.toString()) : null;
            }catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return null;
    }

    /**
     * 判断输入的类型是否是原始类型、或者是其包装类、String等
     */
    private boolean isPrimitive(Class<?> clazz){
        return PRIMITIVE_CLASSES.contains(clazz);
    }

    private Object getCache(String fullKey){
        return redisUtils.get(fullKey);
    }

    private String camelToUnderline(String source){
        return StringUtils.camelToUnderline(source);
    }
}
