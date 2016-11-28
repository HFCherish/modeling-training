package com.tw.rich.core;


import com.tw.rich.core.commands.Command;
import com.tw.rich.core.places.Estate;
import com.tw.rich.core.places.Place;

/**
 * Created by pzzheng on 11/27/16.
 */
public class Player {
    Game game;
    Status status;
    Command lastCommand;
    private Place currentPlace;
    private Asset asset;

    public Status execute(Command command) {
        if (status.equals(Status.WAIT_FORM_COMMAND) || status.equals(Status.WAIT_FOR_RESPONSE)) {
            lastCommand = status.action(this, command);
        }
        return status;
    }

    private Player() {
    }

    public Asset getAsset() {
        return asset;
    }

    public static Player createPlayerWithFund_Game_Command_State(int initialFund, Game game) {
        Player player = new Player();
        player.status = Status.WAIT_FORM_COMMAND;
        player.game = game;
        player.asset = new Asset(initialFund);
        return player;
    }

    public Status getStatus() {
        return status;
    }

    public void waitForResponse() {
        status = Status.WAIT_FOR_RESPONSE;
    }

    public void endTurn() {
        status = Status.WAIT_FOR_TURN;
    }

    public Game getGame() {
        return game;
    }

    public Place currentPlace() {
        return currentPlace;
    }

    public void moveTo(Place place) {
        currentPlace = place;
    }

    public enum Status {
        WAIT_FOR_TURN {
            @Override
            Command action(Player player, Command command) {
                return null;
            }
        }, WAIT_FORM_COMMAND {
            @Override
            Command action(Player player, Command command) {
                return command.execute(player);
            }
        }, BANKRUPT {
            @Override
            Command action(Player player, Command command) {
                return null;
            }
        }, WAIT_FOR_RESPONSE {
            @Override
            Command action(Player player, Command command) {
                return command.respond(player, command);
            }
        };

        abstract Command action(Player player, Command command);
    }
}
