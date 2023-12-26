package com.serliunx.vartalk.plugin;

import com.serliunx.vartalk.plugin.base.VaryTalkPlugin;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * VaryTalk插件核心类
 * <li> 提供一系列插件会使用的方法
 * <li> 注册监听器、发布事件等.
 * @author SerLiunx
 * @since 1.0
 */
public final class VaryTalk {

    private VaryTalk(){}

    /**
     * 根据插件名获取插件信息
     * @param pluginName 插件名
     * @return 插件信息(不存在则返回null)
     */
    public static VaryTalkPlugin findPluginByName(String pluginName){
        return getPlugins()
                .stream()
                .filter(p -> p.getName().equals(pluginName))
                .findAny()
                .orElse(null);
    }

    /**
     * 根据作者获取插件信息
     * @param author 作者名
     * @return 插件信息(不存在则返回null)
     */
    public static Set<VaryTalkPlugin> findPluginsByAuthor(String author){
        return findPluginsByFilter(p -> Arrays.asList(p.getAuthor()).contains(author));
    }

    /**
     * 获取所有已启用的插件
     * @return 所有已启用的插件
     */
    public static Set<VaryTalkPlugin> findPluginsEnabled(){
        return findPluginsByFilter(VaryTalkPlugin::isEnable);
    }

    public static Set<VaryTalkPlugin> findPluginsByFilter(Predicate<? super VaryTalkPlugin> filter){
        return getPlugins()
                .stream()
                .filter(filter)
                .collect(Collectors.toCollection(HashSet::new));
    }

    /**
     * 启用指定插件
     * @param plugin 插件
     */
    public static void enablePlugin(VaryTalkPlugin plugin){
        plugin.enable();
    }

    /**
     * 启用多个插件
     * @param plugins 插件数组
     */
    public static void enablePlugins(VaryTalkPlugin...plugins){
        for (VaryTalkPlugin plugin : plugins) {
            enablePlugin(plugin);
        }
    }

    public static void disablePlugin(VaryTalkPlugin plugin){
        plugin.disable();
    }

    public static void disablePlugins(VaryTalkPlugin...plugins){
        for (VaryTalkPlugin plugin : plugins) {
            disablePlugin(plugin);
        }
    }

    private static Set<VaryTalkPlugin> getPlugins(){
        PluginLoader pluginLoader = PluginLoader.getInstance();
        return pluginLoader.getPlugins();
    }
}
