package com.tw.rich.core.places;

import com.tw.rich.core.Player;
import com.tw.rich.core.assistenceItems.Tool;

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

    abstract Player.Status comeHere(Player player);
}
