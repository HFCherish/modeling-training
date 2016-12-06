package com.tw.rich.io;

import com.tw.rich.core.assistenceItems.Tool;
import com.tw.rich.core.places.Estate;
import com.tw.rich.core.places.Hospital;
import com.tw.rich.core.places.Place;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by pzzheng on 12/6/16.
 */
public class PlaceSymbolTest {

    @Test
    public void should_convert_to_symbol() {
        Place estate = new Estate(1000);
//        System.out.println(estate.getClass());
//        System.out.println(((Estate)estate).getLevel().ordinal());

        ((Estate) estate).upgrade();
        assertThat(PlaceSymbol.convertToSymbol(estate), is("1"));
        assertThat(PlaceSymbol.convertToSymbol(new Hospital()), is("H"));

        estate.setTool(Tool.BLOCK);
        assertThat(PlaceSymbol.convertToSymbol(estate), is("@"));
    }
}