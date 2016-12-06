package com.tw.rich.core.commands;

import com.tw.rich.core.player.Player;
import com.tw.rich.core.assistenceItems.Tool;
import com.tw.rich.core.places.ToolHouse;

/**
 * Created by pzzheng on 11/28/16.
 */
public class BuyTool extends Command {

    public BuyTool() {
    }

    @Override
    public Command execute(Player player) {
        return null;
    }

    @Override
    public Command respond(Player player, Command response) {
        if (((Selection) response).getSelection() != -1) {
            player.getAsset().buyTool(Tool.findToolById(((Selection) response).getSelection()));
            if (player.getAsset().getPoints() >= ((ToolHouse) player.currentPlace()).cheapestTool().getValue()) {
                player.waitForResponse();
                return this;
            }
        }
        player.endTurn();
        return null;
    }

}
