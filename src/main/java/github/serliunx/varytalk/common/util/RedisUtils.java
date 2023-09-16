package github.serliunx.varytalk.common.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 判断redis中是否存在指定的键
     * @param key 键
     * @return 存在返回真，否则返回假
     */
    public boolean hasKey(String key){
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public Set<String> keys(String pattern){
        return redisTemplate.keys(pattern);
    }

    /**
     * 根据指定键值的剩余时间、0为永久存在
     * @param key 键
     * @return 剩余时间
     */
    public Long getExpire(String key){
        return redisTemplate.getExpire(key);
    }

    /**
     * 设置指定键值的TTL
     * @param key 键
     * @param time 时间
     * @param timeUnit 时间单位
     */
    public void setExpire(String key, long time, TimeUnit timeUnit){
        redisTemplate.expire(key, time, timeUnit);
    }

    /**
     * 设置指定键值的TTL
     * @param key 键
     * @param time 时间(以秒为单位)
     */
    public void setExpire(String key, long time){
        setExpire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 删除指定的键
     * @param key 键
     * @return 删除的数量(特殊情况下为null, 比如事务)
     */
    public Long delete(String...key){
        if(key == null || key.length == 0){
            return 0L;
        }
        Long delCounts = null;
        if(key.length == 1){
            redisTemplate.delete(key[0]);
        }else{
            delCounts = redisTemplate.delete(List.of(key));
        }
        return delCounts;
    }

    /**
     * 根据key获取普通值（字符串）
     * @param key 键
     * @return 结果
     */
    public Object get(String key){
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 放入值
     * @param key 键
     * @param value 值
     */
    public void put(String key, Object value){
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 放入值
     * @param key 键
     * @param value 值
     * @param time 时间(以秒为单位)
     */
    public void put(String key, Object value, long time){
        put(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 放入值
     * @param key 键
     * @param value 值
     * @param time 时间
     * @param timeUnit 时间单位
     */
    public void put(String key, Object value, long time, TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    /**
     * 向指定键值的hash表中放入数据, 不存在会创建
     * @param key 键值
     * @param hashKey hash表的键
     * @param value hash表的值
     * @param time 时间
     * @param timeUnit 时间单位
     */
    public void putHashKeyValue(String key, String hashKey, Object value, long time, TimeUnit timeUnit){
        redisTemplate.opsForHash().put(key, hashKey, value);
        //设置TTL
        if(time > 0){
            setExpire(key, time, timeUnit);
        }
    }

    /**
     * 向指定键值的hash表中放入数据, 不存在会创建
     * @param key 键值
     * @param hashKey hash表的键
     * @param value hash表的值
     * @param time 时间(以秒为单位)
     */
    public void putHashKeyValue(String key, String hashKey, Object value, long time){
        putHashKeyValue(key, hashKey, value, time, TimeUnit.SECONDS);
    }
}
