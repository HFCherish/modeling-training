package com.tw.rich.core.commands;

import com.tw.rich.core.Dice;
import com.tw.rich.core.places.Estate;

/**
 * Created by pzzheng on 11/27/16.
 */
public class CommandFactory {
    public static Command SayYes = new SimpleCommand();

    public static Command Roll(Dice dice) {
        return new Roll(dice);
    }

    public static Command BuyEstate(Estate estate) {
        return new BuyEstate(estate);
    }

    public static Command UpgradeEstate(Estate estate) {
        return new UpgradeEstate(estate);
    }
}
