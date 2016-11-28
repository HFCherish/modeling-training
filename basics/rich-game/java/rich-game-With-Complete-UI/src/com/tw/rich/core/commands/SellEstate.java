package com.tw.rich.core.commands;

import com.tw.rich.core.player.Player;
import com.tw.rich.core.places.Estate;

/**
 * Created by pzzheng on 11/27/16.
 */
public class SellEstate extends Command {
    private Estate estate;

    public SellEstate(Estate estate) {
        this.estate = estate;
    }

    @Override
    public Command execute(Player player) {
        if(player.getAsset().hasEstate(estate)) {
            player.getAsset().sellEstate(estate);
            estate.empty();
        }
        return null;
    }

    @Override
    public Command respond(Player player, Command response) {
        return null;
    }
}
