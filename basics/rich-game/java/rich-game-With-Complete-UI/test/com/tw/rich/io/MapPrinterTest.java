package com.tw.rich.io;

import org.junit.Test;

import static com.tw.rich.io.DefaultMap.getMap;

/**
 * Created by pzzheng on 12/6/16.
 */
public class MapPrinterTest {
    @Test
    public void should_print() {
        MapPrinter.flush(getMap());
    }
}