package com.tw.rich.core.places;

import com.tw.rich.core.player.Player;
import com.tw.rich.core.commands.Command;

/**
 * Created by pzzheng on 11/27/16.
 */
public class Hospital extends Place {
    public static int HOSPITAL_DAYS = 3;

    @Override
    public Command comeHere(Player player) {
        player.moveTo(this);
        player.endTurn();
        return null;
    }
}
