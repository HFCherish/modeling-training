package com.tw.rich.core.places;

import com.tw.rich.core.player.Player;
import com.tw.rich.core.commands.Command;
import com.tw.rich.core.commands.CommandFactory;

/**
 * Created by pzzheng on 11/27/16.
 */
public class Estate extends Place {
    private int emptyPrice;
    private Type type;
    private Player owner;
    private Level level;

    public Estate(int emptyPrice) {
        this.emptyPrice = emptyPrice;
        level = Level.EMPTY;
    }

    @Override
    public Command comeHere(Player player) {
        player.moveTo(this);
        return typeFor(player).comeHere(player, this);
    }

    public void sellTo(Player player) {
        owner = player;
    }

    public Type typeFor(Player player) {
        if (owner == null) return Type.EMPTY;
        if (owner.equals(player)) return Type.OWN;
        return Type.OTHER;
    }

    public int getEmptyPrice() {
        return emptyPrice;
    }

    public void upgrade() {
        if (!isHighestLevel()) {
            level = Level.values()[level.ordinal() + 1];
        }
    }

    boolean isHighestLevel() {
        Level[] values = Level.values();
        return level.equals(values[values.length - 1]);
    }

    public Level getLevel() {
        return level;
    }

    public Player getOwner() {
        return owner;
    }

    public void empty() {
        owner = null;
    }

    public enum Level {
        EMPTY, THATCH, FOREIGN_STYLE, SKYSCRAPER
    }

    public enum Type {
        EMPTY {
            @Override
            Command comeHere(Player player, Estate estate) {
                if (player.getAsset().getFunds() < estate.emptyPrice) {
                    player.endTurn();
                    return null;
                }
                player.waitForResponse();
                return CommandFactory.BuyEstate(estate);
            }
        }, OTHER {
            @Override
            Command comeHere(Player player, Estate estate) {
                int charge = estate.emptyPrice * (estate.level.ordinal() + 1) / 2;
                if(!player.isLucky() && !estate.owner.isStucked()) {
                    if(player.getAsset().getFunds() < charge) {
                        player.bankrupt();
                        return null;
                    }
                    player.getAsset().addFunds(-charge);
                    estate.owner.getAsset().addFunds(charge);
                }
                player.endTurn();
                return null;
            }
        }, OWN {
            @Override
            Command comeHere(Player player, Estate estate) {
                if (player.getAsset().getFunds() < estate.emptyPrice || estate.isHighestLevel()) {
                    player.endTurn();
                    return null;
                }
                player.waitForResponse();
                return CommandFactory.UpgradeEstate(estate);
            }
        };

        abstract Command comeHere(Player player, Estate estate);
    }
}
