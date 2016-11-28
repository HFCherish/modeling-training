package com.tw.rich.core.commands;

import com.tw.rich.core.player.Player;
import com.tw.rich.core.places.Estate;

/**
 * Created by pzzheng on 11/28/16.
 */
public class BuyEstate extends Command {
    private Estate estate;

    public BuyEstate(Estate estate) {
        this.estate = estate;
    }

    @Override
    public Command execute(Player player) {
        return null;
    }

    @Override
    public Command respond(Player player, Command response) {
        if (response.equals(CommandFactory.SayYes)) {
            player.getAsset().buyEstate(estate);
            estate.sellTo(player);
        }
        player.endTurn();
        return null;
    }
}
