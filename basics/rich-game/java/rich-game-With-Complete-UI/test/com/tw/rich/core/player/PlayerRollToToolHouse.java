package com.tw.rich.core.player;

import com.tw.rich.core.Dice;
import com.tw.rich.core.Game;
import com.tw.rich.core.map.GameMap;
import com.tw.rich.core.assistenceItems.Tool;
import com.tw.rich.core.commands.BuyTool;
import com.tw.rich.core.commands.CommandFactory;
import com.tw.rich.core.places.ToolHouse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/27/16.
 */
public class PlayerRollToToolHouse {

    private Game game;
    private ToolHouse toolHouse;
    private GameMap map;
    private Dice dice;

    @Before
    public void setUp() {
        dice = () -> 1;
        toolHouse = new ToolHouse();
        game = mock(Game.class);
        map = mock(GameMap.class);
        when(map.move(any(), anyInt())).thenReturn(toolHouse);
        when(game.getMap()).thenReturn(map);
    }

    @Test
    public void should_end_turn_if_has_no_enough_point() {
        Player player = Player.createPlayerWithFund_Game_Command_State(0, game);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FORM_COMMAND));

        player.execute(CommandFactory.Roll(dice));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
    }

    @Test
    public void should_buy_tool_if_has_enough_points_and_select_one() {
        Player player = Player.createPlayerWithFund_Game_Command_State(0, game);
        player.getAsset().addPoints(Tool.ROBOT.getValue() + Tool.BLOCK.getValue());

        assertThat(player.getStatus(), is(Player.Status.WAIT_FORM_COMMAND));
        assertThat(player.getAsset().getPoints(), is(Tool.ROBOT.getValue() + Tool.BLOCK.getValue()));

        player.execute(CommandFactory.Roll(dice));
        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_RESPONSE));
        assertThat(player.lastCommand instanceof BuyTool, is(true));

        player.execute(CommandFactory.BuyTool(Tool.BLOCK));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_RESPONSE));
        assertThat(player.getAsset().getPoints(), is(Tool.ROBOT.getValue()));
        assertThat(player.getAsset().hasTool(Tool.BLOCK), is(true));
    }

    @Test
    public void should_end_turn_if_has_enough_points_but_quit_manually() {
        Player player = Player.createPlayerWithFund_Game_Command_State(0, game);
        player.getAsset().addPoints(Tool.ROBOT.getValue() + Tool.BLOCK.getValue());

        assertThat(player.getStatus(), is(Player.Status.WAIT_FORM_COMMAND));
        assertThat(player.getAsset().getPoints(), is(Tool.ROBOT.getValue() + Tool.BLOCK.getValue()));

        player.execute(CommandFactory.Roll(dice));
        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_RESPONSE));
        assertThat(player.lastCommand instanceof BuyTool, is(true));

        player.execute(CommandFactory.BuyTool(null));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player.getAsset().hasTool(Tool.BLOCK), is(false));
    }

}
