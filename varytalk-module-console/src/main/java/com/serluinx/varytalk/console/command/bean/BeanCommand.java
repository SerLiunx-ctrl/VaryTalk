package com.serluinx.varytalk.console.command.bean;

import com.serluinx.varytalk.console.command.Command;
import com.serluinx.varytalk.console.command.bean.children.BeanListCommand;

/**
 * Bean 相关指令
 * @author SerLiunx
 * @since 1.0
 */
public class BeanCommand extends Command{

    public BeanCommand() {
        super("bean", "bean相关指令");

        addChild(new BeanListCommand());
    }

    @Override
    public boolean handleCommand(String command) {
        System.out.println("参数过少....");
        return true;
    }
}
