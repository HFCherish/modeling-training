package com.tw.rich.core;

import com.tw.rich.core.commands.Command;
import com.tw.rich.core.map.GameMap;
import com.tw.rich.core.places.Hospital;
import com.tw.rich.core.places.Place;
import com.tw.rich.core.places.Starting;
import com.tw.rich.core.player.Player;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 12/5/16.
 */
public class GameTest {

    private GameMap map;
    private Starting starting;

    @Before
    public void setUp() {
        starting = new Starting();
        map = new GameMap(2, 2, starting, mock(Place.class), mock(Place.class), new Hospital());
    }

    @Test
    public void should_init_game_players_and_map() {
        Player player1 = Player.createPlayerWithFund_Wait_turn_State(1000);
        Player player2 = Player.createPlayerWithFund_Wait_turn_State(1000);
        Player player3 = Player.createPlayerWithFund_Wait_turn_State(1000);

        Game game = new Game(map, player1, player2, player3);

        assertThat(player1.getStatus(), is(Player.Status.WAIT_FORM_COMMAND));
        assertThat(player1.currentPlace(), is(starting));
        assertThat(player2.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player2.currentPlace(), is(starting));
        assertThat(player3.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player3.currentPlace(), is(starting));

        assertThat(game.currentPlayer, is(player1));
        assertThat(game.status, is(Game.Status.START));
    }

    @Test
    public void should_can_designate_current_player_execute_command() {
        Player player = mock(Player.class);
        Game game = new Game(map, player);
        Command command = mock(Command.class);
        when(player.execute(command)).thenReturn(Player.Status.WAIT_FOR_RESPONSE);

        assertThat(game.execute(command), is(Player.Status.WAIT_FOR_RESPONSE));
    }

    @Test
    public void should_able_to_shift_player() {
        Player player1 = Player.createPlayerWithFund_Wait_turn_State(1000);
        Player player2 = Player.createPlayerWithFund_Wait_turn_State(1000);
        Player player3 = Player.createPlayerWithFund_Wait_turn_State(1000);

        Game game = new Game(map, player1, player2, player3);

        game.nextPlayer();

        assertThat(game.currentPlayer, is(player2));
    }

    @Test
    public void should_shift_player_if_current_player_end_turn() {
        Player player1 = Player.createPlayerWithFund_Wait_turn_State(1000);
        Player player2 = Player.createPlayerWithFund_Wait_turn_State(1000);
        Player player3 = Player.createPlayerWithFund_Wait_turn_State(1000);

        Game game = new Game(map, player1, player2, player3);

        player1.endTurn();

        assertThat(game.currentPlayer, is(player2));
    }
}