package com.tw.rich.core.commands;

import com.tw.rich.core.Dice;

/**
 * Created by pzzheng on 11/27/16.
 */
public class CommandFactory {
    public static Command Roll(Dice dice) {
        return new Roll(dice);
    }
}
