package com.serliunx.varytalk.cache.api;

import com.serliunx.varytalk.cache.annotation.Cache;
import com.serliunx.varytalk.cache.annotation.CacheRefresh;
import com.serliunx.varytalk.cache.annotation.TagEntity;
import com.serliunx.varytalk.cache.annotation.TagValue;
import lombok.Getter;
import lombok.Setter;

/**
 * 缓存信息封装
 * @author SerLiunx
 * @since 1.0
 */
@Getter
@Setter
public class CacheInformationWrapper {

    public CacheInformationWrapper(String key, boolean usingTag, String tag) {
        this(key, usingTag, tag, null, null, null, null);
    }

    public CacheInformationWrapper(String key, boolean usingTag, String tag, Cache cache,
                                   CacheRefresh cacheRefresh, TagEntity tagEntity, TagValue tagValue) {
        this.key = key;
        this.usingTag = usingTag;
        this.tag = tag;
        this.cache = cache;
        this.cacheRefresh = cacheRefresh;
        this.tagEntity = tagEntity;
        this.tagValue = tagValue;
    }

    /**
     * 缓存键值
     */
    private String key;

    /**
     * 是否使用标签区分相同键值下的不同数据
     */
    private boolean usingTag;

    /**
     * 标签值(仅支持字符串)
     * <li> 可能为null
     */
    private String tag;

    /**
     * 缓存方法的注解
     * <li> 可能为null
     */
    private Cache cache;

    /**
     * 缓存刷新方法的注解
     */
    private CacheRefresh cacheRefresh;

    /**
     * 缓存标签的实体
     * <li> 可能为null
     */
    private TagEntity tagEntity;

    /**
     * 缓存标签(原始类及其包装类)、也可能为缓存标签{@link TagEntity}的某个属性
     * <li> 可能为null
     */
    private TagValue tagValue;
}
