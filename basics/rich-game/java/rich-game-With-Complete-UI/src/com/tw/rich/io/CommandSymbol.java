package com.tw.rich.io;

import com.tw.rich.core.SimpleDice;
import com.tw.rich.core.assistenceItems.Tool;
import com.tw.rich.core.commands.Command;
import com.tw.rich.core.commands.CommandFactory;
import com.tw.rich.core.map.GameMap;

/**
 * Created by pzzheng on 11/27/16.
 */
public interface CommandSymbol {
    CommandSymbol ROLL = i -> CommandFactory.Roll(SimpleDice.get());
    CommandSymbol QUERY = i -> CommandFactory.Query;
    CommandSymbol HELP = i -> CommandFactory.Help;
    CommandSymbol QUIT = i -> CommandFactory.Quit;
    CommandSymbol ROBOT = i -> CommandFactory.UseTool(Tool.ROBOT, 0);
//    CommandSymbol SELLTOOL = i -> CommandFactory.SellTool(ToolHouse, 0);

    static Command convertToCommand(String command, GameMap map){
        //use reflection
        String[] split = command.trim().toUpperCase().split("\\s+");
        String commandSymbolName = split[0];
        int selection = 0;
        if(split.length>1) {
            selection = Integer.valueOf(split[1]);
        }

        try {
            Object commandSymbol = CommandSymbol.class.getDeclaredField(commandSymbolName).get(null);
            return ((CommandSymbol)commandSymbol).getCommand(selection);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    Command getCommand(int selection);
}
