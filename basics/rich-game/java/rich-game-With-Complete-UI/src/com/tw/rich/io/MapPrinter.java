package com.tw.rich.io;

import com.tw.rich.core.map.GameMap;

import static com.tw.rich.io.PlaceSymbol.convertToSymbol;

/**
 * Created by pzzheng on 12/6/16.
 */
public class MapPrinter {
    public static void flush(GameMap map) {
        int width = map.getWidth();
        int height = map.getHeight();
        int count = 0;
        String[][] mapString = new String[height][width];


        for(int i=0; i<width; i++) {
            mapString[0][i] = convertToSymbol(map.getPlaces().get(count++), map);
        }

        for( int i=1; i<height; i++) {
            mapString[i][width-1] = convertToSymbol(map.getPlaces().get(count++), map);
        }

        for(int i=width-2; i>=0; i--) {
            mapString[height-1][i] = convertToSymbol(map.getPlaces().get(count++), map);
        }

        for(int i=height-2; i>0; i--) {
            mapString[i][0] = convertToSymbol(map.getPlaces().get(count++), map);
        }

        for( int i=0; i<height; i++ ){
            for ( int j=0; j<width; j++ ){
                mapString[i][j] = mapString[i][j] == null ? " " : mapString[i][j];
                System.out.print(mapString[i][j]);
            }
            System.out.println();
        }
    }
}
