package com.tw.rich.core.commands;

import com.tw.rich.core.Dice;
import com.tw.rich.core.assistenceItems.Gift;
import com.tw.rich.core.assistenceItems.Tool;
import com.tw.rich.core.places.Estate;

/**
 * Created by pzzheng on 11/27/16.
 */
public class CommandFactory {
    public static Command SayYes = new SimpleCommand();
    public static Command BuyTool = new BuyTool();
    public static Command GetGift = new GetGift();
    public static Command Query = new SimpleCommand();
    public static Command Help = new SimpleCommand();
    public static Command Quit = new SimpleCommand();

    public static Command Roll(Dice dice) {
        return new Roll(dice);
    }

    public static Command BuyEstate(Estate estate) {
        return new BuyEstate(estate);
    }

    public static Command SellTool(Tool tool) {
        return new SellTool(tool);
    }

    public static Command UseTool(Tool tool, int steps) {
        return new UseTool(tool, steps);
    }

    public static Command SellEstate(Estate estate) {
        return new SellEstate(estate);
    }

    public static Command UpgradeEstate(Estate estate) {
        return new UpgradeEstate(estate);
    }

    public static Command BuyTool(Tool tool) {
        return new BuyTool(tool);
    }

    public static Command GetGift(Gift gift) {
        return new GetGift(gift);
    }
}
