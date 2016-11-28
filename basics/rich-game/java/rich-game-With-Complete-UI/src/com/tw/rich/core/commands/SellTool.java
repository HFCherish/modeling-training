package com.tw.rich.core.commands;

import com.tw.rich.core.Player;
import com.tw.rich.core.assistenceItems.Tool;

/**
 * Created by pzzheng on 11/27/16.
 */
public class SellTool extends Command {

    private Tool tool;

    public SellTool(Tool tool) {
        this.tool = tool;
    }

    @Override
    public Command execute(Player player) {
        if(player.getAsset().hasTool(tool)) {
            player.getAsset().sellTool(tool);
        }
        return null;
    }

    @Override
    public Command respond(Player player, Command response) {
        return null;
    }
}
