package com.serliunx.vartalk.plugin;

import com.serliunx.vartalk.plugin.base.VaryTalkPlugin;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Slf4j
public final class PluginLoader {

    private final Map<String , VaryTalkPlugin> pluginMap = new HashMap<>();

    private static volatile PluginLoader INSTANCE;
    private static final String PLUGINS_DIRECTORY = "plugins";
    private static final String PLUGIN_FILE_FULL_NAME = "plugin.yml";

    private PluginLoader(){}

    public static synchronized PluginLoader getInstance(){
        if(INSTANCE == null){
            INSTANCE = new PluginLoader();
        }
        return INSTANCE;
    }

    /**
     * 预载入所有插件
     */
    public synchronized void load(){
        log.info("开始载入插件...");
        int count = 0;
        File pluginsDir = new File(PLUGINS_DIRECTORY);
        File[] plugins = pluginsDir.listFiles(
                (dir, name) -> name.toLowerCase().endsWith(".jar")
        );
        if(plugins != null){
            for (File plugin : plugins) {
                loadContent(plugin);
                count++;
            }
        }
        log.info("插件载入完毕, 共加载插件数量 -> {}", count);
    }

    /**
     * 启用所有插件
     */
    public void enable(){
        pluginMap.forEach(
                (key, value) -> value.enabled()
        );
    }

    @SuppressWarnings("all")
    private void loadContent(File jarFile){
        try (JarFile file = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = file.entries();
            PluginContext pluginContext = null;
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().equals(PLUGIN_FILE_FULL_NAME)) {
                    Yaml yaml = new Yaml();
                    InputStream inputStream = file.getInputStream(entry);
                    pluginContext = yaml.loadAs(inputStream, PluginContext.class);
                    break;
                }
            }
            if(pluginContext == null){
                return;
            }
            URL[] urls = {new URL("file:" + jarFile.getPath())};
            URLClassLoader classLoader = new URLClassLoader(urls);
            try {
                Class<?> mainClass = classLoader.loadClass(pluginContext.getMain());
                Class<VaryTalkPlugin> clazz = VaryTalkPlugin.class;
                boolean assignableFrom = clazz.isAssignableFrom(mainClass);
                if(!assignableFrom){
                    return;
                }
                Constructor<?> constructor = mainClass.getConstructor();
                VaryTalkPlugin plugin = (VaryTalkPlugin)constructor.newInstance();
                //调用插件的onLoad方法进行必要的初始化
                pluginMap.put(pluginContext.getName(), plugin);
                plugin.onLoad();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            classLoader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Getter
    @Setter
    public static class PluginContext{
        public String name;
        public String version;
        public String main;
    }
}
