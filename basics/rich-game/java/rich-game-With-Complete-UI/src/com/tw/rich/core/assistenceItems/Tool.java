package com.tw.rich.core.assistenceItems;

import com.tw.rich.core.Player;

/**
 * Created by pzzheng on 11/27/16.
 */
public enum Tool {
    BLOCK(50) {
        @Override
        void encounter(Player player) {

        }
    }, BOMB(50) {
        @Override
        void encounter(Player player) {

        }
    }, ROBOT(30) {
        @Override
        void encounter(Player player) {

        }
    };

    int value;

    Tool(int value) {
        this.value = value;
    }

    abstract void encounter(Player player);
}
