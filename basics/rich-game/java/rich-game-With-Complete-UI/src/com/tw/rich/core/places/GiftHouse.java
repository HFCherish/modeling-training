package com.tw.rich.core.places;

import com.tw.rich.core.messages.Message;
import com.tw.rich.core.player.Player;
import com.tw.rich.core.assistenceItems.Gift;
import com.tw.rich.core.commands.Command;
import com.tw.rich.core.commands.CommandFactory;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by pzzheng on 11/27/16.
 */
public class GiftHouse extends Place {

    private final List<Gift> gifts;

    public GiftHouse(Gift... gifts) {
        this.gifts = asList(gifts);
    }

    @Override
    public Command comeHere(Player player) {
        player.moveTo(this);
        player.addMessage(Message.TO_SELECT_GIFT);
        player.waitForResponse();
        return CommandFactory.GetGift;
    }
}
