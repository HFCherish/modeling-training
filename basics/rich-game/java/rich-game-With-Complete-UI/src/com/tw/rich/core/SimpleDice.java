package com.tw.rich.core;

import java.util.Random;

/**
 * Created by pzzheng on 12/6/16.
 */
public class SimpleDice implements Dice{
    static final Dice dice = new SimpleDice();
    public static Dice get() {
        return dice;
    }

    @Override
    public int next() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }
}
