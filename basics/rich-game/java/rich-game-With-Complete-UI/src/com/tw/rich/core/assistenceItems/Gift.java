package com.tw.rich.core.assistenceItems;

import com.tw.rich.core.Player;

/**
 * Created by pzzheng on 11/27/16.
 */
public enum Gift {
    FUND_CARD(2000) {
        @Override
        void getThis(Player player) {

        }
    }, POINT_CARD(200) {
        @Override
        void getThis(Player player) {

        }
    }, LUCKY_GOD(5) {
        @Override
        void getThis(Player player) {

        }
    };
    int value;

    Gift(int value) {
        this.value = value;
    }

    abstract void getThis(Player player);

    public int getValue() {
        return value;
    }
}
