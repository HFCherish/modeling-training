package com.tw.rich.core.places;

import com.tw.rich.core.Player;
import com.tw.rich.core.commands.Command;
import com.tw.rich.core.commands.CommandFactory;

/**
 * Created by pzzheng on 11/27/16.
 */
public class Estate extends Place {
    private int emptyPrice;
    private Type type;
    private Player owner;

    public Estate(int emptyPrice) {
        this.emptyPrice = emptyPrice;
        type = Type.EMPTY;
    }

    @Override
    public Command comeHere(Player player) {
        player.moveTo(this);
        return type.comeHere(player, this);
    }

    public void sellTo(Player player) {
        owner = player;
    }

    public int getEmptyPrice() {
        return emptyPrice;
    }

    public enum Type{
        EMPTY {
            @Override
            Command comeHere(Player player, Estate estate) {
                if(player.getAsset().getFunds() < estate.emptyPrice) {
                    player.endTurn();
                    return null;
                }
                player.waitForResponse();
                return CommandFactory.BuyEstate(estate);
            }
        }, OTHER {
            @Override
            Command comeHere(Player player, Estate estate) {
                return null;
            }
        }, OWN {
            @Override
            Command comeHere(Player player, Estate estate) {
                return null;
            }
        };

        abstract Command comeHere(Player player, Estate estate);
    }
}
