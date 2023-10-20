package com.serluinx.varytalk.console;

import com.serluinx.varytalk.console.command.Command;
import com.serluinx.varytalk.console.command.bean.BeanCommand;
import com.serluinx.varytalk.console.command.help.HelpCommand;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author SerLiunx
 * @since 1.0
 */
public class CommandHandler {

    private final List<Command> commands = new ArrayList<>();

    public CommandHandler() {
        init();
    }

    public void init(){
        commands.add(new BeanCommand());
        commands.add(new HelpCommand());
    }

    public void handleCommand(String command){
        String[] resolveCommand = resolveCommand(command);
        if(resolveCommand.length == 0){
            return;
        }
        for (Command cmd : commands) {
            if(cmd.getName().equals(resolveCommand[0])){
                cmd.handleCommand(resolveCommand);
                break;
            }
        }
    }

    public List<Command> getCommands() {
        return commands;
    }

    /**
     * 解析指令及参数
     * @param command 原始指令串
     * @return 指令及其参数
     */
    private String[] resolveCommand(String command){
        if(command.startsWith(" ")){
            return new String[]{};
        }
        return command.split(" ");
    }
}
