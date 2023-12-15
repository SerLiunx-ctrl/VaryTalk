package com.serliunx.vartalk.plugin.core;

import com.serliunx.vartalk.plugin.base.VaryTalkPlugin;
import com.serliunx.vartalk.plugin.event.Event;

/**
 * VaryTalk插件核心类
 * <li> 提供一系列插件会使用的方法
 * <li> 注册监听器、发布事件等.
 * @author SerLiunx
 * @since 1.0
 */
public final class VaryTalk {

    private VaryTalk(){}

    public static void disablePlugin(VaryTalkPlugin plugin){

    }

    public static void disablePlugins(VaryTalkPlugin...plugins){
        for (VaryTalkPlugin plugin : plugins) {
            disablePlugin(plugin);
        }
    }

    public static void registerListeners(VaryTalkPlugin plugin, Listener...listeners){

    }

    public static void publishEvent(Event event){

    }

    public static void publishEvents(Event...events){
        for (Event event : events) {
            publishEvent(event);
        }
    }
}
