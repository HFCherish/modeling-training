package com.tw.rich.core.commands;

import com.tw.rich.core.player.Player;

/**
 * Created by pzzheng on 11/27/16.
 */
public abstract class Command {
    public abstract Command execute(Player player);
    public abstract Command respond(Player player, Command response);
}
