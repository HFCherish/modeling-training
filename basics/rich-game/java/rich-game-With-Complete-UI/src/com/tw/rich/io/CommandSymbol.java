package com.tw.rich.io;

import com.tw.rich.core.Game;
import com.tw.rich.core.SimpleDice;
import com.tw.rich.core.assistenceItems.Tool;
import com.tw.rich.core.commands.Command;
import com.tw.rich.core.commands.CommandFactory;

/**
 * Created by pzzheng on 11/27/16.
 */
public interface CommandSymbol {
    CommandSymbol ROLL = (i, game) -> CommandFactory.Roll(SimpleDice.get());
    CommandSymbol QUERY = (i, game) -> CommandFactory.Query;
    CommandSymbol HELP = (i, game) -> CommandFactory.Help;
    CommandSymbol QUIT = (i, game) -> CommandFactory.Quit;
    CommandSymbol ROBOT = (i, game) -> CommandFactory.UseTool(Tool.ROBOT, 0);
    CommandSymbol SELLTOOL = (i, game) -> CommandFactory.SellTool(Tool.findToolById(Integer.valueOf(i)));
    CommandSymbol SELL = (i, game) -> CommandFactory.SellEstate(DefaultMap.getEstateById(i));
    CommandSymbol BLOCK = (i, game) -> CommandFactory.UseTool(Tool.BLOCK, Integer.valueOf(i));
    CommandSymbol BOMB = (i, game) -> CommandFactory.UseTool(Tool.BOMB, Integer.valueOf(i));


    static Command convertToCommand(String command, Game game){
        //use reflection
        String[] split = command.trim().toUpperCase().split("\\s+");
        String commandSymbolName = split[0];
        String selection = null;
        if(split.length>1) {
            selection = split[1];
        }

        try {
            Object commandSymbol = CommandSymbol.class.getDeclaredField(commandSymbolName).get(null);
            return ((CommandSymbol)commandSymbol).getCommand(selection, game);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    Command getCommand(String selection, Game game);
}
