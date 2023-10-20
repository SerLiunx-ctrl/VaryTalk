package com.serluinx.varytalk.console.command.bean.children;

import com.serluinx.varytalk.console.ConsoleMenu;
import com.serluinx.varytalk.console.command.Command;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 查看系统内所有bean信息
 * @author SerLiunx
 * @since 1.0
 */
public class BeanListCommand extends Command {

    public BeanListCommand() {
        super("list", "查看bean列表");
    }

    @Override
    public boolean handleCommand(String command) {
        ConfigurableApplicationContext applicationContext = ConsoleMenu.getInstance().getApplicationContext();
        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        return false;
    }
}
