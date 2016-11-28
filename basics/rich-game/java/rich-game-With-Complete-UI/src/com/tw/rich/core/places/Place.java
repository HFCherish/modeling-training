package com.tw.rich.core.places;

import com.tw.rich.core.player.Player;
import com.tw.rich.core.assistenceItems.Tool;
import com.tw.rich.core.commands.Command;

/**
 * Created by pzzheng on 11/27/16.
 */
public abstract class Place {
    Tool tool;

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public abstract Command comeHere(Player player);
}
