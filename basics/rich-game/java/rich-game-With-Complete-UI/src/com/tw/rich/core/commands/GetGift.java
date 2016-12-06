package com.tw.rich.core.commands;

import com.tw.rich.core.player.Player;
import com.tw.rich.core.assistenceItems.Gift;

/**
 * Created by pzzheng on 11/28/16.
 */
public class GetGift extends Command {

    public GetGift() {
    }

    @Override
    public Command execute(Player player) {
        return null;
    }

    @Override
    public Command respond(Player player, Command response) {
        Gift selectGift = Gift.findGiftById(((Selection) response).getSelection());
        if (selectGift != null) {
            selectGift.getThis(player);
        }
        player.endTurn();
        return null;
    }
}
