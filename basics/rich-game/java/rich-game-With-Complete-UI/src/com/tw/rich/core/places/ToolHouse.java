package com.tw.rich.core.places;

import com.tw.rich.core.Player;
import com.tw.rich.core.assistenceItems.Tool;
import com.tw.rich.core.commands.Command;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by pzzheng on 11/27/16.
 */
public class ToolHouse extends Place {
    private final List<Tool> tools;

    public ToolHouse(Tool... tools) {
        this.tools = asList(tools);
    }

    @Override
    public Command comeHere(Player player) {
        player.moveTo(this);
        if(player.getAsset().getPoints() < tools.stream().min((a,b) -> a.getValue() - b.getValue()).get().getValue()) {
            player.endTurn();
        }
        return null;
    }
}
