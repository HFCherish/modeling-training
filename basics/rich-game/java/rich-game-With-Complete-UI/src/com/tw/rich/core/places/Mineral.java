package com.tw.rich.core.places;

import com.tw.rich.core.player.Player;
import com.tw.rich.core.commands.Command;

/**
 * Created by pzzheng on 11/27/16.
 */
public class Mineral extends Place {
    private int points;

    public Mineral(int points) {
        this.points = points;
    }

    @Override
    public Command comeHere(Player player) {
        player.moveTo(this);
        player.getAsset().addPoints(points);
        player.endTurn();
        return null;
    }
}
