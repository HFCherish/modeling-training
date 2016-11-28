package com.tw.rich.core.commands;

import com.tw.rich.core.player.Player;
import com.tw.rich.core.assistenceItems.Gift;

/**
 * Created by pzzheng on 11/28/16.
 */
public class GetGift extends Command {
    private Gift gift;

    public GetGift(Gift gift) {
        this.gift = gift;
    }

    public GetGift() {
    }

    @Override
    public Command execute(Player player) {
        return null;
    }

    @Override
    public Command respond(Player player, Command response) {
        if(((GetGift)response).gift != null) {
            ((GetGift)response).gift.getThis(player);
        }
        player.endTurn();
        return null;
    }
}
