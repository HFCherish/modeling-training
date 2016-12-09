package com.tw.rich.io;

import com.tw.rich.core.messages.Message;
import com.tw.rich.core.places.Estate;
import com.tw.rich.core.player.Player;
import org.junit.Test;

import static com.tw.rich.io.SimpleMessage.getMessages;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;

/**
 * Created by pzzheng on 12/6/16.
 */
public class SimpleMessageTest {
    @Test
    public void should_get_messages_of_player() {
        Player player = spy(Player.createPlayerWithFund_Wait_turn_State(1000));
        Estate place = new Estate(100);
        player.moveTo(place);

        assertThat(getMessages(player).contains("您现在处于"), is(true));
        System.out.println(getMessages(player));

        player.addMessage(Message.IF_UPGRADE);
        System.out.println(getMessages(player));
    }
}