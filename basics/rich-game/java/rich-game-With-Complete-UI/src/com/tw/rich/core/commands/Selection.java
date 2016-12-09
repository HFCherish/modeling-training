package com.tw.rich.core.commands;

import com.tw.rich.core.player.Player;

/**
 * Created by pzzheng on 12/6/16.
 */
public class Selection extends Command {

    private int selection;

    public Selection(int selection) {
        this.selection = selection;
    }

    public int getSelection() {
        return selection;
    }

    @Override
    public Command execute(Player player) {
        return null;
    }

    @Override
    public Command respond(Player player, Command response) {
        return null;
    }
}
