package com.serliunx.varytalk.framework.core.tool;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Bean转换相关工具方法
 * @author SerLiunx
 * @since 1.0
 */
public final class BeanUtils {

    private BeanUtils(){throw new UnsupportedOperationException();}

    private static final Map<Class<?>, Map<String, Field>> fieldsCacheMap = new ConcurrentHashMap<>(32);

    /**
     * 复制集合对象属性
     * @param source 源列表
     * @param target 目标类型
     * @param supplier 集合
     * @return 新对象集合
     * @param <C> 集合类型
     * @param <E> 新对象类型
     */
    public static <C extends Collection<E>, E> C toBean(Collection<?> source, Class<E> target,
                                                        Supplier<C> supplier){
        return source.stream()
                .map(s -> toBean(s, target))
                .collect(Collectors.toCollection(supplier));
    }

    /**
     * 复制对象属性(List列表)
     * @param source 源列表
     * @param target 目标类型
     * @return 新列表
     * @param <E> 新对象类型
     */
    public static <E> List<E> toBean(List<?> source, Class<E> target){
        return toBean(source, target, ArrayList::new);
    }

    /**
     * 复制对象属性
     * @param source 源对象
     * @param target 目标对象类型
     * @return 新对象
     * @param <T> 新对象类型
     */
    public static <T> T toBean(Object source, Class<T> target){
        try {
            Constructor<T> constructor = target.getConstructor();
            T t = constructor.newInstance();
            return copyField(source, t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取指定类中的所有属性
     * <li> 会将私有属性设置为可访问的
     * @param clazz 类
     * @return 属性
     */
    private static Map<String, Field> getDeclaredAccessibleFields(Class<?> clazz){
        Map<String, Field> fields = fieldsCacheMap.get(clazz);
        if(fieldsCacheMap.get(clazz) == null){
            fields = Arrays.stream(clazz.getDeclaredFields()).peek(f -> {
                try {
                    f.setAccessible(true);
                } catch (Exception e) {
                    throw new RuntimeException("属性获取异常!");
                }
            }).collect(Collectors.toMap(Field::getName, f -> f));
            fieldsCacheMap.put(clazz, fields);
        }
        return fields;
    }

    /**
     * 获取指定对象中的所有属性
     * <li> 会将私有属性设置为可访问的
     * @param object 对象
     * @return 属性
     */
    private static Map<String, Field> getDeclaredAccessibleFields(Object object){
        return getDeclaredAccessibleFields(object.getClass());
    }

    @SuppressWarnings("unchecked")
    private static <T> T copyField(Object source, Object target){
        Map<String, Field> sourceFields = getDeclaredAccessibleFields(source);
        Map<String, Field> targetFields = getDeclaredAccessibleFields(target);
        targetFields.forEach((k, v)-> {
            try {
                Field resultField = sourceFields.get(k);
                if(resultField != null){
                    v.set(target, resultField.get(source));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return (T) target;
    }
}
