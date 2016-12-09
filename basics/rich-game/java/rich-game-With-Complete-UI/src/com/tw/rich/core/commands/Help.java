package com.tw.rich.core.commands;

import com.tw.rich.core.Reportable;
import com.tw.rich.core.player.Player;

/**
 * Created by pzzheng on 12/6/16.
 */
public class Help extends Command {
    private Reportable reportable;

    public Help(Reportable reportable) {
        this.reportable = reportable;
    }

    @Override
    public Command execute(Player player) {
        reportable.showHelp();
        return null;
    }

    @Override
    public Command respond(Player player, Command response) {
        return null;
    }
}
