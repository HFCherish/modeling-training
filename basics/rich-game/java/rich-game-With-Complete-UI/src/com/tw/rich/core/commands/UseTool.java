package com.tw.rich.core.commands;

import com.tw.rich.core.player.Player;
import com.tw.rich.core.assistenceItems.Tool;

/**
 * Created by pzzheng on 11/28/16.
 */
public class UseTool extends Command {
    private final Tool tool;
    private final int steps;

    public UseTool(Tool tool, int steps) {
        this.tool = tool;
        this.steps = steps;
    }

    @Override
    public Command execute(Player player) {
        if(player.getAsset().hasTool(tool) && tool.useThis(player, steps)) {
            player.getAsset().useTool(tool);
        }
        return null;
    }

    @Override
    public Command respond(Player player, Command response) {
        return null;
    }
}
