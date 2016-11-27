package com.tw.rich.core;

import com.tw.rich.core.commands.Command;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/27/16.
 */
public class PlayerTest {
    @Test
    public void should_wait_for_response_after_executing_command_needed_response() {
        Game game = mock(Game.class);
        Player player = Player.createPlayerWithFund_Game_Command_State(10000, game);
        Command command = mock(Command.class);
        Command toResponseCommand = mock(Command.class);
        when(command.execute(eq(player))).thenAnswer(p -> {
            ((Player) p.getArgument(0)).waitForResponse();
            return toResponseCommand;
        });

        assertThat(player.getStatus(), is(Player.Status.WAIT_FORM_COMMAND));

        player.execute(command);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_RESPONSE));
        assertThat(player.lastCommand, is(toResponseCommand));
    }


    @Test
    public void should_end_turn_after_executing_terminal_response() {
//        Game game = mock(Game.class);
//        Player player = Player.createPlayerWithFund_Game_Command_State(10000, game);
//
//        Command command = mock(Command.class);
//        when(command.execute(any())).thenAnswer(player -> {
//            return
//        });
//        assertThat(player.getStatus(), is(Player.Status.WAIT_FORM_COMMAND));
//        player.execute(command);
//        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_RESPONSE));
//
//        when(command.respond(any(), any())).thenReturn(Player.Status.WAIT_FOR_TURN);
//        player.execute(command);
//        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
    }


}