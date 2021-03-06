package com.tw.rich.core.assistenceItems;

import com.tw.rich.core.messages.Message;
import com.tw.rich.core.player.Player;
import com.tw.rich.core.commands.Command;
import com.tw.rich.core.places.Hospital;

import java.util.Arrays;

/**
 * Created by pzzheng on 11/27/16.
 */
public enum Tool {
    BLOCK(50) {
        @Override
        public Command encounter(Player player) {
            player.addMessage(Message.STOP_AT_BLOCK);
            return player.currentPlace().comeHere(player);
        }

        @Override
        public boolean useThis(Player player, int steps) {
            return player.getGame().getMap().setTool(this, player.currentPlace(), steps);
        }
    }, BOMB(50) {
        @Override
        public Command encounter(Player player) {
            player.addMessage(Message.ENCOUNTER_BOMB);
            player.stuckFor(Hospital.HOSPITAL_DAYS);
            return player.getGame().getMap().getHospital().comeHere(player);
        }

        @Override
        public boolean useThis(Player player, int steps) {
            return player.getGame().getMap().setTool(this, player.currentPlace(), steps);
        }
    }, ROBOT(30) {
        @Override
        public Command encounter(Player player) {
            return null;
        }

        @Override
        public boolean useThis(Player player, int steps) {
            return player.getGame().getMap().useRobot(player.currentPlace());
        }
    };

    int value;

    /**
     * @param id the id in Tool enum, started from 1
     * @return
     */
    public static Tool findToolById(int id) {
        return Arrays.stream(Tool.values()).filter(tool1 -> tool1.ordinal()+1 == id).findAny().orElse(null);
    }

    Tool(int value) {
        this.value = value;
    }

    public abstract Command encounter(Player player);

    public int getValue() {
        return value;
    }

    public abstract boolean useThis(Player player, int steps);
}
