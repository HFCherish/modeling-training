package com.tw.rich.io;

import com.tw.rich.core.assistenceItems.Tool;
import com.tw.rich.core.map.GameMap;
import com.tw.rich.core.places.Estate;
import com.tw.rich.core.places.Place;

/**
 * Created by pzzheng on 11/27/16.
 */
public interface PlaceSymbol {
    PlaceSymbol EstateSymbol = (place) -> {
        Estate estate = (Estate) place;
        String color = "";
        if(estate.getOwner() != null) {
            color = estate.getOwner().getIdentity().getColor();
        }
        return color + String.valueOf(estate.getLevel().ordinal());
    };
    PlaceSymbol HospitalSymbol = (place) -> "H";
    PlaceSymbol StartingSymbol = (place) -> "S";
    PlaceSymbol ToolHouseSymbol = (place) -> "T";
    PlaceSymbol GiftHouseSymbol = (place) -> "G";
    PlaceSymbol PrisonSymbol = (place) -> "P";
    PlaceSymbol MineralSymbol = (place) -> "$";
    PlaceSymbol MagicHouseSymbol = (place) -> "M";



    static String convertToSymbol(Place place, GameMap map){
        if(map.playerOn(place).isPresent()) {
            return map.playerOn(place).get().getIdentity().getSymbol();
        }

        if(place.getTool() != null) {
            return place.getTool() == Tool.BLOCK ? "#" : "@";
        }

        String placeSymbolName = place.getClass().getSimpleName() + "Symbol";

        try {
            Object o = PlaceSymbol.class.getDeclaredField(placeSymbolName).get(null);
            return ((PlaceSymbol)o).getSymbol(place);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    String getSymbol(Place place);
}
