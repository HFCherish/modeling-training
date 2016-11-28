package com.tw.rich.core.commands;

import com.tw.rich.core.player.Player;
import com.tw.rich.core.places.Estate;

/**
 * Created by pzzheng on 11/28/16.
 */
public class UpgradeEstate extends Command {
    private Estate estate;

    public UpgradeEstate(Estate estate) {
        this.estate = estate;
    }

    @Override
    public Command execute(Player player) {
        return null;
    }

    @Override
    public Command respond(Player player, Command response) {
        if(response.equals(CommandFactory.SayYes)) {
            player.getAsset().addFunds(-estate.getEmptyPrice());
            estate.upgrade();
        }
        player.endTurn();
        return null;
    }
}
