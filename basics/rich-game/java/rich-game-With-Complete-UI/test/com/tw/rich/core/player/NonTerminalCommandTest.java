package com.tw.rich.core.player;

import com.tw.rich.core.Game;
import com.tw.rich.core.map.GameMap;
import com.tw.rich.core.assistenceItems.Tool;
import com.tw.rich.core.commands.CommandFactory;
import com.tw.rich.core.places.Estate;
import com.tw.rich.core.places.Place;
import com.tw.rich.core.places.Starting;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/28/16.
 */
public class NonTerminalCommandTest {
    Game game;
    GameMap map;

    @Before
    public void setUp() {
        game = mock(Game.class);
        map = mock(GameMap.class);
        when(game.getMap()).thenReturn(map);
    }

    @Test
    public void should_sell_tool_if_has() {
        Player player = Player.createPlayerWithFund_Game_Command_State(0, game);
        player.getAsset().addPoints(Tool.BLOCK.getValue());
        player.getAsset().buyTool(Tool.BLOCK);

        player.execute(CommandFactory.SellTool(Tool.BLOCK));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FORM_COMMAND));
        assertThat(player.getAsset().hasTool(Tool.BLOCK), is(false));
    }

    @Test
    public void should_sell_Estate_if_has() {
        Player player = Player.createPlayerWithFund_Game_Command_State(10, game);
        Estate estate = new Estate(10);
        estate.sellTo(player);
        player.getAsset().buyEstate(estate);

        player.execute(CommandFactory.SellEstate(estate));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FORM_COMMAND));
        assertThat(player.getAsset().hasEstate(estate), is(false));
        assertThat(player.getAsset().getFunds(), is(20));
        assertThat(estate.typeFor(player), is(Estate.Type.EMPTY));
    }

    @Test
    public void should_use_tool_if_has_tool() {
        Player player = Player.createPlayerWithFund_Game_Command_State(0, game);
        when(map.setTool(eq(Tool.BLOCK), any(Place.class), anyInt())).thenReturn(true);
        player.moveTo(new Starting());
        player.getAsset().addPoints(Tool.BLOCK.getValue());
        player.getAsset().buyTool(Tool.BLOCK);

        player.execute(CommandFactory.UseTool(Tool.BLOCK, 1));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FORM_COMMAND));
        assertThat(player.getAsset().hasTool(Tool.BLOCK), is(false));
    }

    @Test
    public void should_able_to_query_and_help() {
        Player player = Player.createPlayerWithFund_Game_Command_State(0, game);

        player.execute(CommandFactory.Query);
        assertThat(player.getStatus(), is(Player.Status.WAIT_FORM_COMMAND));

        player.execute(CommandFactory.Help);
        assertThat(player.getStatus(), is(Player.Status.WAIT_FORM_COMMAND));
    }

}
