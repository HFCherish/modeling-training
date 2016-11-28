package com.tw.rich.core.assistenceItems;

import com.tw.rich.core.Player;
import com.tw.rich.core.commands.Command;
import com.tw.rich.core.places.Hospital;

/**
 * Created by pzzheng on 11/27/16.
 */
public enum Tool {
    BLOCK(50) {
        @Override
        public Command encounter(Player player) {
            return player.currentPlace().comeHere(player);
        }
    }, BOMB(50) {
        @Override
        public Command encounter(Player player) {
            player.stuckFor(Hospital.HOSPITAL_DAYS);
            return player.getGame().getMap().getHospital().comeHere(player);
        }
    }, ROBOT(30) {
        @Override
        public Command encounter(Player player) {
            return null;
        }
    };

    int value;

    Tool(int value) {
        this.value = value;
    }

    public abstract Command encounter(Player player);

    public int getValue() {
        return value;
    }
}
