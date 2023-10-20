package com.serluinx.varytalk.console;

import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

/**
 * 控制台输入控制
 * @author SerLiunx
 * @since 1.0
 */
public class ConsoleMenu {

    private final ConfigurableApplicationContext applicationContext;
    private final CommandHandler commandHandler = new CommandHandler();
    private static ConsoleMenu instance;

    public ConsoleMenu(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public boolean input(){
        Scanner scanner = new Scanner(System.in);
        String command = null;
        while(!"stop".equals(command)){
            System.out.print("\n" + "> ");
            command = scanner.nextLine();
            commandHandler.handleCommand(command);
        }
        return false;
    }

    public static ConsoleMenu build(ConfigurableApplicationContext applicationContext){
        ConsoleMenu menu = new ConsoleMenu(applicationContext);
        return instance = menu;
    }

    public ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public static ConsoleMenu getInstance(){
        if(instance == null){
            throw new RuntimeException("控制台输入控制初始化失败..");
        }
        return instance;
    }
}
