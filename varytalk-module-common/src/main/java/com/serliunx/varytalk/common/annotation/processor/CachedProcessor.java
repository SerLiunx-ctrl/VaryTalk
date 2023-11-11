package com.serliunx.varytalk.common.annotation.processor;

import com.serliunx.varytalk.common.annotation.CacheRefresh;
import com.serliunx.varytalk.common.annotation.Cached;
import com.serliunx.varytalk.common.config.autoconfiguer.SystemAutoConfigurer;
import com.serliunx.varytalk.common.exception.ServiceException;
import com.serliunx.varytalk.common.util.AopUtils;
import com.serliunx.varytalk.common.util.RedisUtils;
import com.serliunx.varytalk.common.util.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 缓存相关注解处理器
 * @author SerLiunx
 * @since 1.0
 */
@Aspect
@Component
public class CachedProcessor {

    private static final String REFRESH_TAG = "REFRESH_TAG";

    private final RedisUtils redisUtils;
    private final SystemAutoConfigurer systemAutoConfigurer;

    public CachedProcessor(RedisUtils redisUtils,
                           SystemAutoConfigurer systemAutoConfigurer) {
        this.redisUtils = redisUtils;
        this.systemAutoConfigurer = systemAutoConfigurer;
    }

    @SuppressWarnings("all")
    @Around("com.serliunx.varytalk.common.aop.PointCutDefinition.cacheUpdatePoint()")
    public Object updateCache(ProceedingJoinPoint joinPoint){
        try {
            if(!preCheck(joinPoint, CacheRefresh.class)){
                return null;
            }
            MethodSignature signature = (MethodSignature)joinPoint.getSignature();
            Method method = signature.getMethod();
            CacheRefresh cacheRefresh = method.getAnnotation(CacheRefresh.class);
            Object result = joinPoint.proceed();
            String key;
            if(cacheRefresh.needTag()){
                Class<?> clazz = cacheRefresh.clazz();
                Object arg = AopUtils.getArg(joinPoint.getArgs(), clazz);
                Field field = AopUtils.getField(clazz, cacheRefresh.propertyName(), true);
                field.setAccessible(true);
                Object property = field.get(arg);
                key = systemAutoConfigurer.getRedisPrefix().getMainPrefix() +
                        StringUtils.camelToUnderline(method.getDeclaringClass().getSimpleName()) +
                        ":" + StringUtils.camelToUnderline(cacheRefresh.value())
                        + ":" + property.toString() + ":" + REFRESH_TAG;
            }else{
                key = systemAutoConfigurer.getRedisPrefix().getMainPrefix() +
                        StringUtils.camelToUnderline(method.getDeclaringClass().getSimpleName()) +
                        ":" + StringUtils.camelToUnderline(cacheRefresh.value())
                        + ":" + REFRESH_TAG;
            }
            redisUtils.put(key, 1);
            return result;
        }catch (Throwable e){
            e.printStackTrace();
            throw new ServiceException("缓存异常, 请联系管理员! " + e.getMessage(), 400);
        }
    }

    @SuppressWarnings("all")
    @Around("com.serliunx.varytalk.common.aop.PointCutDefinition.cachedPoint()")
    public Object setCache(ProceedingJoinPoint joinPoint){
        try {
            if(!preCheck(joinPoint, Cached.class)){
                return null;
            }
            MethodSignature signature = (MethodSignature)joinPoint.getSignature();
            Method method = signature.getMethod();
            Cached cached = method.getAnnotation(Cached.class);
            String key = cached.value();
            if(key.isEmpty()){
                key = buildKey(method, cached, joinPoint);
            }
            //检查缓存是否需要更新
            String updateKey = systemAutoConfigurer.getRedisPrefix().getMainPrefix() +
                    StringUtils.camelToUnderline(method.getDeclaringClass().getSimpleName()) + ":" +
                    StringUtils.camelToUnderline(method.getName()) +
                    (cached.index() != -1 ? (":" + getTag(cached.index(), joinPoint)) : "") + ":" + REFRESH_TAG;

            if(redisUtils.hasKey(updateKey)){
                Object result = joinPoint.proceed();
                redisUtils.put(key, result, cached.time(), cached.timeUnit());
                //更新完之后删除更新标记
                redisUtils.delete(updateKey);
                return result;
            }
            // 缓存中取该值, 如果存在, 则直接返回
            Object o = redisUtils.get(key);
            if(o != null){
                return o;
            }
            // 缓存中不存在, 正常执行方法并将结果放到缓存中.
            Object result = joinPoint.proceed();
            redisUtils.put(key, result, cached.time(), cached.timeUnit());
            return result;
        }catch (Throwable e){
            e.printStackTrace();
            throw new ServiceException("缓存异常, 请联系管理员! " + e.getMessage(), 400);
        }
    }



    @SuppressWarnings("all")
    private <T extends Annotation> boolean preCheck(ProceedingJoinPoint joinPoint, Class<T> annotation){
        Signature original = joinPoint.getSignature();
        if(!(original instanceof MethodSignature signature)){
            return false;
        }
        Method method = signature.getMethod();
        T result = method.getAnnotation(annotation);
        return result != null;
    }

    private String buildKey(Method method, Cached cached, ProceedingJoinPoint joinPoint){
        String tag = "";
        if(cached.index() != -1){
            tag = getTag(cached.index(), joinPoint);
        }
        return systemAutoConfigurer.getRedisPrefix().getMainPrefix() + StringUtils.camelToUnderline(method.getDeclaringClass()
                .getSimpleName()) + ":" + StringUtils.camelToUnderline(method.getName()) + (tag.isEmpty() ? "" : (":" + tag));
    }

    private String getTag(int index, ProceedingJoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        Class<?>[] classes = AopUtils.getClassArray(args);
        if(classes[index].equals(String.class) || classes[index].equals(Long.class) ||
                classes[index].equals(Integer.class) || classes[index].equals(int.class) ||
                classes[index].equals(long.class)){
            return args[index].toString();
        }
        return "";
    }
}
