package com.tw.rich.io;

import com.tw.rich.core.Game;
import com.tw.rich.core.commands.*;
import org.junit.Test;

import static com.tw.rich.io.CommandSymbol.convertToCommand;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by pzzheng on 12/6/16.
 */
public class CommandSymbolTest {
    @Test
    public void should_convert_command() {
        Game game = GameFactory.createDefaultGame(1000, "12");
        assertThat(convertToCommand("roll", game) instanceof Roll, is(true));
        assertThat(convertToCommand("robot", game) instanceof UseTool, is(true));
        assertThat(convertToCommand("sell 2", game) instanceof SellEstate, is(true));
        assertThat(convertToCommand("sellTool 2", game) instanceof SellTool, is(true));
        assertThat(convertToCommand("block 2", game) instanceof UseTool, is(true));

        assertThat(convertToCommand("2", game) instanceof Selection, is(true));
    }
}