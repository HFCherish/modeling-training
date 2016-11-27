package com.tw.rich.io;

import com.tw.rich.core.places.Place;

/**
 * Created by pzzheng on 11/27/16.
 */
public enum PlaceSymbol {
    Estate {
        @Override
        String getSymbol() {
            return null;
        }
    };

    public static String convertToSymbol(Place place){
        //use reflection
        return null;
    }
    abstract String getSymbol();
}
