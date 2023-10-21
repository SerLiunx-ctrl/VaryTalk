package com.serluinx.varytalk.console.command;

import java.util.ArrayList;
import java.util.List;

/**
 * 指令
 * @author SerLiunx
 * @since 1.0
 */
public abstract class Command{

    protected String name;
    protected String description;
    protected List<Command> children = new ArrayList<>();

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addChild(Command...children){
        this.children.addAll(List.of(children));
    }

    public abstract boolean handleCommand(String command);

    public void handleCommand(String[] commands){
        if(hasChildren() && commands.length > 1){
            boolean matchedChild = false;
            for (Command child : children) {
                if(child.getName().equals(commands[1])){
                    matchedChild = true;
                    String[] commandsCut = new String[commands.length - 1];
                    System.arraycopy(commands, commands.length - 1, commandsCut, 0,
                            commands.length - 1);
                    child.handleCommand(commandsCut);
                    break;
                }
            }
            if(!matchedChild){
                childNotFound();
            }
        }else{
            handleCommand(name);
        }
    }

    public void childNotFound(){
        System.out.println("指令参数有误..");
    }

    public void showSyntax(){
        System.out.println(name + " - " + description);
        if(hasChildren()){
            System.out.println("\t参数:");
            for (Command child : children) {
                System.out.print("\t");
                child.showSyntax();
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Command> getChildren() {
        return children;
    }

    public void setChildren(List<Command> children) {
        this.children = children;
    }

    private boolean hasChildren(){
        return children != null && !children.isEmpty();
    }
}
