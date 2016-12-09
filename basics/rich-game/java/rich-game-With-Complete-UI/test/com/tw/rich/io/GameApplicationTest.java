package com.tw.rich.io;

import org.junit.Test;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by pzzheng on 12/6/16.
 */
public class GameApplicationTest {
    @Test
    public void should_start_game() throws IOException {
        Scanner in = new Scanner(getClass().getResourceAsStream("gameTest.txt"));
        new GameApplication().start(in, System.out);
    }
}