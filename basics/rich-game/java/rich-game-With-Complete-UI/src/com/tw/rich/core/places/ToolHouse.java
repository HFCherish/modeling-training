package com.tw.rich.core.places;

import com.tw.rich.core.Player;
import com.tw.rich.core.assistenceItems.Tool;
import com.tw.rich.core.commands.Command;
import com.tw.rich.core.commands.CommandFactory;

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
        if(player.getAsset().getPoints() >= cheapestTool().getValue()) {
            player.waitForResponse();
            return CommandFactory.BuyTool;
        }
        player.endTurn();
        return null;
    }

    public Tool cheapestTool() {
        return tools.stream().min((a, b) -> a.getValue() - b.getValue()).get();
    }
}
