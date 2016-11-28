package com.tw.rich.core.commands;

import com.tw.rich.core.player.Player;
import com.tw.rich.core.assistenceItems.Tool;
import com.tw.rich.core.places.ToolHouse;

/**
 * Created by pzzheng on 11/28/16.
 */
public class BuyTool extends Command {
    private Tool tool;

    public BuyTool(Tool tool) {
        this.tool = tool;
    }

    public BuyTool() {
    }

    @Override
    public Command execute(Player player) {
        return null;
    }

    @Override
    public Command respond(Player player, Command response) {
        if (((BuyTool) response).tool != null) {
            player.getAsset().buyTool(((BuyTool) response).tool);
            if (player.getAsset().getPoints() >= ((ToolHouse) player.currentPlace()).cheapestTool().getValue()) {
                player.waitForResponse();
                return this;
            }
        }
        player.endTurn();
        return null;
    }

}
