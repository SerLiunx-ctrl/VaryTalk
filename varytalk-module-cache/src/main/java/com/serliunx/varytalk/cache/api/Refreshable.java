package com.serliunx.varytalk.cache.api;

import com.serliunx.varytalk.cache.annotation.CacheRefresh;

/**
 * 当注解{@link CacheRefresh}无法达到你想要的效果时.
 * <p>可以实现该接口来刷新缓存.
 * @author SerLiunx
 * @since 1.0
 * @see CacheInformationWrapper
 */
public interface Refreshable {

    /**
     * 更新缓存的钩子方法
     * @param informationWrapper 缓存信息封装
     * @return 更新成功返回真、否则返回假(务必按照代码执行情况返回)
     */
    boolean refresh(CacheInformationWrapper informationWrapper);
}
