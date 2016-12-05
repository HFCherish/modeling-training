package com.tw.rich.core;

import com.tw.rich.core.commands.Command;
import com.tw.rich.core.map.GameMap;
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

    public Game(GameMap map, Player... players) {
        this.map = map;
        this.players = new ArrayList<>(asList(players));
        this.map.initPlayers(players);

        initAndStart();
    }

    private void initAndStart() {
        currentPlayer = players.get(0);
        players.get(0).inTurn();
        players.stream().forEach(player -> {player.moveTo(map.getStarting());
            player.joinGame(this);
        });
        status = Status.START;
    }

    public GameMap getMap() {
        return map;
    }

    public Player.Status execute(Command command) {
        return currentPlayer.execute(command);
    }


    public void nextPlayer() {
        currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
    }

    public void inform(Player player) {
        if(currentPlayer.equals(player)) {
            if (player.getStatus().equals(Player.Status.BANKRUPT)) {
                nextPlayer();
                players.remove(player);
                map.removePlayer(player);
            } else if (player.getStatus().equals(Player.Status.WAIT_FOR_TURN)) {
                nextPlayer();
            }
        }

    }

    public enum Status {START}
}
