package com.tw.rich.core.map;

import com.tw.rich.core.assistenceItems.Tool;
import com.tw.rich.core.places.Hospital;
import com.tw.rich.core.places.Place;
import com.tw.rich.core.player.Player;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by pzzheng on 11/27/16.
 */
public class GameMap {

    private final int width;
    private final int height;
    private final List<Place> places;
    private List<Player> players;

    public GameMap(int width, int height, Place... places) {
        this.width = width;
        this.height = height;
        this.places = asList(places);
        players = new ArrayList<>();
    }

    public Place move(Place start, int steps) {
        int startIndex = places.indexOf(start);
        int direction = steps < 0 ? -1 : 1;
        for (int i = 0; Math.abs(i) <= Math.min(Math.abs(steps), places.size()); i += direction) {
            int nextIndex = nextIndex(startIndex, i);
            Place targetPlace = places.get(nextIndex);
            if(targetPlace.getTool() != null) {
                return targetPlace;
            }
        }
        return places.get(nextIndex(startIndex, steps));
    }

    private int nextIndex(int startIndex, int steps) {
        return (places.size() + (startIndex + steps) % places.size()) % places.size();
    }

    public Hospital getHospital() {
        return null;
    }

    public boolean setTool(Tool tool, Place start, int steps) {
        if((!tool.equals(Tool.BLOCK) && !tool.equals(Tool.BOMB)) || Math.abs(steps) > 10) return false;

        Place targetPlace = places.get(nextIndex(places.indexOf(start), steps));
        if(targetPlace.getTool() != null || players.stream().filter(p -> p.currentPlace().equals(targetPlace)).count() > 0) {
            return false;
        }
        targetPlace.setTool(tool);
        return true;
    }

    public boolean useRobot(Place start) {
        return false;
    }


    public void initPlayers(Player... players) {
        this.players = asList(players);
    }
}
