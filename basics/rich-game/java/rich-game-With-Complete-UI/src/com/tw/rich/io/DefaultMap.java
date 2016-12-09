package com.tw.rich.io;

import com.tw.rich.core.assistenceItems.Gift;
import com.tw.rich.core.map.GameMap;
import com.tw.rich.core.places.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pzzheng on 12/6/16.
 */
public class DefaultMap {
    static List<Place> places;

    static {
        places = new ArrayList<>();
        places.add(new Starting());
        addEstates(places, 13, 200);
        places.add(new Hospital());
        addEstates(places, 13, 200);
        places.add(new ToolHouse());
        addEstates(places, 6, 500);
        places.add(new GiftHouse(Gift.FUND_CARD, Gift.LUCKY_GOD, Gift.POINT_CARD));
        addEstates(places, 13, 300);
        places.add(new Prison());
        addEstates(places, 13, 300);
        places.add(new MagicHouse());
        places.add(new Mineral(20));
        places.add(new Mineral(80));
        places.add(new Mineral(100));
        places.add(new Mineral(40));
        places.add(new Mineral(80));
        places.add(new Mineral(60));
    }

    public static GameMap getMap() {
        return new GameMap(29, 8, places.toArray(new Place[places.size()]));
    }

    /**
     * @param id the absolute id in map, start from 1
     * @return the estate in that place
     */
    public static Estate getEstateById(String id) {
        return (Estate) places.get(Integer.valueOf(id) - 1);
    }

    private static void addEstates(List<Place> places, int count, int emptyPrice) {
        for (int i = 0; i < count; i++)
            places.add(new Estate(emptyPrice));
    }

}
