package com.tw.rich.core.commands;

import com.tw.rich.core.Dice;
import com.tw.rich.core.player.Player;
import com.tw.rich.core.places.Place;

/**
 * Created by pzzheng on 11/27/16.
 */
public class Roll extends Command {
    private Dice dice;

    public Roll(Dice dice) {

        this.dice = dice;
    }

    @Override
    public Command execute(Player player) {
        Place currentPlace = player.getGame().getMap().move(player.currentPlace(), dice.next());
        if(currentPlace.getTool() != null) {
            player.moveTo(currentPlace);
            return currentPlace.getTool().encounter(player);
        }
        return currentPlace.comeHere(player);
    }

    @Override
    public Command respond(Player player, Command response) {
        return null;
    }
}
