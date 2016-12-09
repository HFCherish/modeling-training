package com.tw.rich.core.commands;

import com.tw.rich.core.Reportable;
import com.tw.rich.core.player.Player;

/**
 * Created by pzzheng on 12/6/16.
 */
public class Query extends Command {
    private Reportable reportable;

    public Query(Reportable reportable) {
        this.reportable = reportable;
    }

    @Override
    public Command execute(Player player) {
        reportable.showPlayer(player);
        return null;
    }

    @Override
    public Command respond(Player player, Command response) {
        return null;
    }
}
