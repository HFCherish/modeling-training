package com.tw.rich.core.player;


import com.tw.rich.core.Game;
import com.tw.rich.core.Identity;
import com.tw.rich.core.assistenceItems.Gift;
import com.tw.rich.core.commands.Command;
import com.tw.rich.core.messages.Message;
import com.tw.rich.core.places.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pzzheng on 11/27/16.
 */
public class Player {
    Game game;
    Status status;
    Command lastCommand;
    private Place currentPlace;
    private Asset asset;
    private int luckyDays;
    private int stuckDays;
    private Identity identity;
    private List<Message> messages;

    public Status execute(Command command) {
        if (status.equals(Status.WAIT_FORM_COMMAND) || status.equals(Status.WAIT_FOR_RESPONSE)) {
            lastCommand = status.action(this, command);
        }
        return status;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void resetMessage() {
        messages = new ArrayList<>();
    }

    private Player() {
        messages = new ArrayList();
    }

    public Asset getAsset() {
        return asset;
    }

    public static Player createPlayerWithFund_Game_Command_State(int initialFund, Game game) {
        Player player = new Player();
        player.status = Status.WAIT_FORM_COMMAND;
        player.joinGame(game);
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
        if(luckyDays > 0)   luckyDays--;
        if(stuckDays > 0)   stuckDays--;
        status = Status.WAIT_FOR_TURN;
        game.inform(this);
    }

    public Game getGame() {
        return game;
    }

    public Place currentPlace() {
        return currentPlace;
    }

    public void moveTo(Place place) {
        messages.add(Message.COME_TO);
        currentPlace = place;
    }

    public void bankrupt() {
        status = Status.BANKRUPT;
        game.inform(this);
    }

    public void getLuckyGod() {
        luckyDays = Gift.LUCKY_GOD.getValue() + 1;
    }

    public boolean isLucky() {
        return luckyDays > 0;
    }

    public void stuckFor(int stuckDays) {
        this.stuckDays = stuckDays + 1;
    }

    public boolean isStucked() {
        return stuckDays > 0;
    }

    public void inTurn() {
        status = Status.WAIT_FORM_COMMAND;
    }

    public static Player createPlayerWithFund_Wait_turn_State(int initialFund) {
        Player player = new Player();
        player.asset = new Asset(initialFund);
        player.status = Status.WAIT_FOR_TURN;
        return player;
    }

    public void joinGame(Game game) {
        this.game = game;
    }

    public static Player createWithIdentityAndFund_WaitTurn(Identity identity, int initialFund) {
        Player player = createPlayerWithFund_Wait_turn_State(initialFund);
        player.identity = identity;
        return player;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void addMessage(Message message) {
        messages.add(message);
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
                return player.lastCommand.respond(player, command);
            }
        };

        abstract Command action(Player player, Command command);
    }
}
