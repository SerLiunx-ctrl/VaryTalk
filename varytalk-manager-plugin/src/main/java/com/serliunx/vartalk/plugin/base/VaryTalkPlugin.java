package com.serliunx.vartalk.plugin.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 插件基类、所有插件必须实现该类
 * @author SerLiunx
 * @since 1.0
 */
public class VaryTalkPlugin {

    private final Logger logger = LoggerFactory.getLogger(VaryTalkPlugin.class);

    /**
     * 插件名
     */
    private String name;

    /**
     * 插件作者
     */
    private String[] author;

    /**
     * 插件版本
     */
    private String version;

    /**
     * 插件主类
     */
    private String main;

    private volatile boolean enable = true;

    /**
     * 启用插件时调用该方法(第二执行顺序)
     * <li> 已执行完Boot启动器、Spring容器已启动、所有预定义的Bean已加载
     * <li> 此时所有插件已经执行完onLoad()方法.
     */
    public void onEnable(){
        throw new UnsupportedOperationException();
    }

    /**
     * 禁用插件时调用该方法(最后执行)
     */
    public void onDisable(){
        throw new UnsupportedOperationException();
    }

    /**
     * 加载插件时调用该方法(第一执行顺序)
     * <li> 此时Spring容器还未初始化、Boot程序还未执行.
     * <li> 不建议在此处调用其它插件、因为可能还未加载完毕
     */
    public void onLoad(){
        throw new UnsupportedOperationException();
    }

    /**
     * 容器注册, 如果你的插件需要使用系统内的Bean容器, 可以将其父容器设置为该方法的参数
     * <li> 在{@link VaryTalkPlugin#onEnable()}执行前执行.
     * @param applicationContext 系统容器
     */
    public void attachApplicationContext(ConfigurableApplicationContext applicationContext){
        throw new UnsupportedOperationException();
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final String[] getAuthor() {
        return author;
    }

    public final void setAuthor(String[] author) {
        this.author = author;
    }

    public final String getVersion() {
        return version;
    }

    public final void setVersion(String version) {
        this.version = version;
    }

    public final String getMain() {
        return main;
    }

    public final void setMain(String main) {
        this.main = main;
    }

    public final boolean isEnable() {
        return enable;
    }

    public final void disable() {
        this.enable = false;
    }

    public final void enable() {
        this.enable = true;
    }

    protected final Logger getLogger(){
        return this.logger;
    }
}
