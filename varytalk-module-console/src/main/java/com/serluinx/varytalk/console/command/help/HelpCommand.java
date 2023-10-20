package com.serluinx.varytalk.console.command.help;

import com.serluinx.varytalk.console.CommandHandler;
import com.serluinx.varytalk.console.ConsoleMenu;
import com.serluinx.varytalk.console.command.Command;

/**
 * 帮助信息
 * @author SerLiunx
 * @since 1.0
 */
public class HelpCommand extends Command {

    public HelpCommand() {
        super("help", "帮助");
    }

    @Override
    public boolean handleCommand(String command) {
        CommandHandler commandHandler = ConsoleMenu.getInstance().getCommandHandler();
        for (Command cmd : commandHandler.getCommands()) {
            cmd.showSyntax();
        }
        return false;
    }
}
