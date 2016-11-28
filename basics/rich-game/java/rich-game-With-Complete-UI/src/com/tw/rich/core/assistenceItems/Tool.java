package com.tw.rich.core.assistenceItems;

import com.tw.rich.core.Player;
import com.tw.rich.core.places.Hospital;

/**
 * Created by pzzheng on 11/27/16.
 */
public enum Tool {
    BLOCK(50) {
        @Override
        public void encounter(Player player) {

        }
    }, BOMB(50) {
        @Override
        public void encounter(Player player) {
            player.getGame().getMap().getHospital().comeHere(player);
            player.stuckFor(Hospital.HOSPITAL_DAYS);
        }
    }, ROBOT(30) {
        @Override
        public void encounter(Player player) {

        }
    };

    int value;

    Tool(int value) {
        this.value = value;
    }

    public abstract void encounter(Player player);

    public int getValue() {
        return value;
    }
}
