package com.serliunx.vartalk.plugin;

import com.serliunx.vartalk.plugin.base.VaryTalkPlugin;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Slf4j
public final class PluginLoader {

    private final Map<String , VaryTalkPlugin> pluginMap = new HashMap<>();

    private static final PluginLoader INSTANCE = new PluginLoader();
    private static final String PLUGINS_DIRECTORY = "plugins";
    private static final String PLUGIN_FILE_FULL_NAME = "plugin.yml";

    private PluginLoader(){}

    public static PluginLoader getInstance(){
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
    public synchronized void enable(){
        pluginMap.forEach(
                (key, value) -> {
                    try {
                        value.onEnable();
                        log.info("{} 已启用.", key);
                    }catch (Exception e){
                        value.disable();
                        value.onDisable();
                        throw new RuntimeException(e.getMessage());
                    }
                }
        );
    }

    /**
     * 将系统的bean容器挂载到插件中
     * @param applicationContext 系统bean容器
     */
    public synchronized void attachApplicationContext(ConfigurableApplicationContext applicationContext){
        pluginMap.forEach(
                (key, value) -> value.attachApplicationContext(applicationContext)
        );
    }

    @SuppressWarnings("all")
    private void loadContent(File jarFile){
        try (JarFile file = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = file.entries();
            PluginContext pluginContext = null;
            URL[] urls = {new URL("file:" + jarFile.getPath())};
            URLClassLoader classLoader = new URLClassLoader(new URL[]{jarFile.toURI().toURL()});
            //加载jar包内的类
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();

                //识别插件主类
                if (entry.getName().equals(PLUGIN_FILE_FULL_NAME)) {
                    Yaml yaml = new Yaml();
                    InputStream inputStream = file.getInputStream(entry);
                    pluginContext = yaml.loadAs(inputStream, PluginContext.class);
                }
                if (entry.getName().endsWith(".class")) {
                    String className = entry.getName().replace("/", ".").replace(".class",
                            "");
                    try {
                        // 加载类
                        Class<?> clazz = classLoader.loadClass(className);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(pluginContext == null){
                return;
            }
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
                plugin.setMain(pluginContext.main);
                plugin.setName(pluginContext.name);
                plugin.setAuthor(new String[]{"test"});
                plugin.setVersion(pluginContext.getVersion());
                plugin.onLoad();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            classLoader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有被系统成功识别并成功加载的插件
     * @return 所有已加载的插件
     */
    public Set<VaryTalkPlugin> getPlugins(){
        return new HashSet<>(pluginMap.values());
    }

    @Getter
    @Setter
    public static class PluginContext{
        public String name;
        public String version;
        public String main;
    }
}
