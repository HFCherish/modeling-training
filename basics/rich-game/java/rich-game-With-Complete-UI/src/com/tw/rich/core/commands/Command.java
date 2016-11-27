package com.tw.rich.core.commands;

import com.tw.rich.core.Player;

/**
 * Created by pzzheng on 11/27/16.
 */
public interface Command {
    Player.Status execute(Player player);
    Player.Status respond(Player player, Command response);
}
