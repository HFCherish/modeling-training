package com.tw.rich.core.assistenceItems;

import com.tw.rich.core.player.Player;

/**
 * Created by pzzheng on 11/27/16.
 */
public enum Gift {
    FUND_CARD(2000) {
        @Override
        public void getThis(Player player) {
            player.getAsset().addFunds(value);
        }
    }, POINT_CARD(200) {
        @Override
        public void getThis(Player player) {
            player.getAsset().addPoints(value);
        }
    }, LUCKY_GOD(5) {
        @Override
        public void getThis(Player player) {
            player.getLuckyGod();
        }
    };
    int value;

    Gift(int value) {
        this.value = value;
    }

    public abstract void getThis(Player player);

    public int getValue() {
        return value;
    }
}
