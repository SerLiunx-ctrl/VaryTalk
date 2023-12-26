package com.serliunx.varytalk.api.common;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis相关功能接口
 * @author SerLiunx
 * @since 1.0
 */
public interface RedisApi {

    /**
     * 判断redis中是否存在指定的键
     * @param key 键
     * @return 存在返回真，否则返回假
     */
    boolean hasKey(String key);

    /**
     * 返回所有符合模板的键
     * @param pattern 模板
     * @return 键集合
     */
    Set<String> keys(String pattern);

    /**
     * 根据指定键值的剩余时间、0为永久存在
     * @param key 键
     * @return 剩余时间
     */
    Long getExpire(String key);

    /**
     * 设置指定键值的TTL
     * @param key 键
     * @param time 时间
     * @param timeUnit 时间单位
     */
    void setExpire(String key, long time, TimeUnit timeUnit);

    /**
     * 删除指定的键
     * @param key 键
     * @return 删除的数量(特殊情况下为null, 比如事务)
     */
    Long delete(String...key);

    /**
     * 根据key获取普通值（字符串）
     * @param key 键
     * @return 结果
     */
    Object get(String key);

    /**
     * 根据key获取普通值（字符串）
     * @param key 键
     * @param clazz 类
     * @return 结果
     * @param <T> 类
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 放入值
     * @param key 键
     * @param value 值
     */
    void put(String key, Object value);

    /**
     * 放入值
     * @param key 键
     * @param value 值
     * @param time 时间(以秒为单位)
     */
    void put(String key, Object value, long time);

    /**
     * 放入值
     * @param key 键
     * @param value 值
     * @param time 时间
     * @param timeUnit 时间单位
     */
    void put(String key, Object value, long time, TimeUnit timeUnit);

    /**
     * 设置值, 根据原键值存在的情况下返回不同的结果
     * @param key 键
     * @param value 值
     * @param time 时间
     * @param timeUnit 单位
     * @return 如果存在原键返回假、否则返回真.
     */
    Boolean setIfAbsent(String key, Object value, long time, TimeUnit timeUnit);

    /**
     * 向指定键值的hash表中放入数据, 不存在会创建
     * @param key 键值
     * @param hashKey hash表的键
     * @param value hash表的值
     * @param time 时间
     * @param timeUnit 时间单位
     */
    void putHashKeyValue(String key, String hashKey, Object value, long time, TimeUnit timeUnit);

    /**
     * 向指定键值的hash表中放入数据, 不存在会创建
     * @param key 键值
     * @param hashKey hash表的键
     * @param value hash表的值
     * @param time 时间(以秒为单位)
     */
    void putHashKeyValue(String key, String hashKey, Object value, long time);
}
