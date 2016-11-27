package com.tw.rich.core.places;

import com.tw.rich.core.Player;

/**
 * Created by pzzheng on 11/27/16.
 */
public class Estate extends Place {
    @Override
    Player.Status comeHere(Player player) {
        return null;
    }

    public enum Type{
        EMPTY {
            @Override
            Player.Status comeHere(Player player, Estate estate) {
                return null;
            }
        }, OTHER {
            @Override
            Player.Status comeHere(Player player, Estate estate) {
                return null;
            }
        }, OWN {
            @Override
            Player.Status comeHere(Player player, Estate estate) {
                return null;
            }
        };

        abstract Player.Status comeHere(Player player, Estate estate);
    }
}
