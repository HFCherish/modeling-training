package com.tw.rich.core.places;

import com.tw.rich.core.messages.Message;
import com.tw.rich.core.player.Player;
import com.tw.rich.core.assistenceItems.Tool;
import com.tw.rich.core.commands.Command;
import com.tw.rich.core.commands.CommandFactory;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by pzzheng on 11/27/16.
 */
public class ToolHouse extends Place {
    private final List<Tool> tools;

    public ToolHouse() {
        this.tools = asList(Tool.values());
    }

    @Override
    public Command comeHere(Player player) {
        player.moveTo(this);
        player.addMessage(Message.TO_BUY_TOOL);
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
