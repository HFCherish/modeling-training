package com.tw.rich.io;

import com.tw.rich.core.SimpleDice;
import com.tw.rich.core.assistenceItems.Tool;
import com.tw.rich.core.commands.Command;
import com.tw.rich.core.commands.CommandFactory;

/**
 * Created by pzzheng on 11/27/16.
 */
public interface CommandSymbol {
    CommandSymbol ROLL = () -> CommandFactory.Roll(SimpleDice.get());
    CommandSymbol QUERY = () -> CommandFactory.Query;
    CommandSymbol HELP = () -> CommandFactory.Help;
    CommandSymbol ROBOT = () -> CommandFactory.UseTool(Tool.ROBOT, 0);
//    CommandSymbol ROBOT = () -> CommandFactory.UseTool(Tool.ROBOT, 0);

    static Command convertToCommand(String command){
        //use reflection
        String commandSymbolName = command.trim().toUpperCase().replaceAll("\\s+", "_");
        try {
            Object commandSymbol = CommandSymbol.class.getDeclaredField(commandSymbolName).get(null);
            return ((CommandSymbol)commandSymbol).getCommand();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    Command getCommand();
}
