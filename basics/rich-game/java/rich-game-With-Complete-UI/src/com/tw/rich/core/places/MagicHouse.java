package com.tw.rich.core.places;

import com.tw.rich.core.Player;
import com.tw.rich.core.commands.Command;

/**
 * Created by pzzheng on 11/27/16.
 */
public class MagicHouse extends Place {
    @Override
    public Command comeHere(Player player) {
        player.moveTo(this);
        player.endTurn();
        return null;
    }
}
