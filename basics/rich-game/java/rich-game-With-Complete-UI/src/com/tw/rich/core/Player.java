package com.tw.rich.core;


import com.tw.rich.core.commands.Command;

/**
 * Created by pzzheng on 11/27/16.
 */
public class Player {
    Game game;
    Status status;
    Command lastCommand;

    public Status execute(Command command) {
        if (status.equals(Status.WAIT_FORM_COMMAND) || status.equals(Status.WAIT_FOR_RESPONSE)) {
            lastCommand = status.action(this, command);
        }
        return status;
    }

    private Player() {
    }

    public static Player createPlayerWithFund_Game_Command_State(int initialFund, Game game) {
        Player player = new Player();
        player.status = Status.WAIT_FORM_COMMAND;
        player.game = game;
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
