package com.tw.rich.core;

import com.tw.rich.core.commands.Command;
import com.tw.rich.core.commands.CommandFactory;
import com.tw.rich.core.map.GameMap;
import com.tw.rich.core.messages.Message;
import com.tw.rich.core.places.Prison;
import com.tw.rich.core.player.Player;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by pzzheng on 11/27/16.
 */
public class Game {
    private final List<Player> players;
    private GameMap map;
    public Status status;
    protected Player currentPlayer;
    private Player winner;

    public Game(GameMap map, Player... players) {
        this.map = map;
        this.players = new ArrayList<>(asList(players));
        this.map.initPlayers(players);

        initAndStart();
    }

    private void initAndStart() {
        currentPlayer = players.get(0);
        players.get(0).inTurn();
        players.stream().forEach(player -> {
            player.moveTo(map.getStarting());
            player.joinGame(this);
        });
        status = Status.START;
    }

    public GameMap getMap() {
        return map;
    }

    public Player.Status execute(Command command) {
        if (command.equals(CommandFactory.Quit)) {
            endGame();
        }
        return currentPlayer.execute(command);
    }


    protected void nextPlayer() {
        currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
        if (currentPlayer.isStucked()) {
            if (currentPlayer.currentPlace() instanceof Prison) {
                currentPlayer.addMessage(Message.SKIP_TURN_IN_PRISON);
            } else {
                currentPlayer.addMessage(Message.SKIP_TURN_IN_HOSPITAL);
            }
            currentPlayer.endTurn();
        } else currentPlayer.inTurn();
    }

    void endGame() {
        status = Status.END;
    }

    public Player currentPlayer() {
        return currentPlayer;
    }

    public void inform(Player player) {
        if (currentPlayer.equals(player)) {
            if (player.getStatus().equals(Player.Status.BANKRUPT)) {
                nextPlayer();
                players.remove(player);
                map.removePlayer(player);
                if (players.size() == 1) {
                    endGame();
                    winner = players.get(0);
                }
            } else if (player.getStatus().equals(Player.Status.WAIT_FOR_TURN)) {
                nextPlayer();
            }
        }
    }

    public Player getWinner() {
        return winner;
    }

    public enum Status {END, START}
}
