package com.serliunx.vartalk.plugin.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 插件基类、所有插件必须实现该类
 * @author SerLiunx
 * @since 1.0
 */
public class VaryTalkPlugin {

    private final Logger logger = LoggerFactory.getLogger(VaryTalkPlugin.class);

    /**
     * 启用插件时调用该方法(第二执行顺序)
     * <li> 已执行完Boot启动器、Spring容器已启动、所有预定义的Bean已加载
     * <li> 此时所有插件已经执行完onLoad()方法.
     */
    public void enabled(){}

    /**
     * 禁用插件时调用该方法(最后执行)
     */
    public void disabled(){}

    /**
     * 加载插件时调用该方法(第一执行顺序)
     * <li> 此时Spring容器还未初始化、Boot程序还未执行.
     * <li> 不建议在此处调用其它插件、因为可能还未加载完毕
     */
    public void onLoad(){}

    protected final Logger getLogger(){
        return this.logger;
    }
}
