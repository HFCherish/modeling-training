package com.tw.rich.io;

import com.tw.rich.core.player.Player;
import com.tw.rich.core.messages.Message;

/**
 * Created by pzzheng on 11/27/16.
 */
public enum SimpleMessages{
    COME_TO {
        @Override
        String toString(Player player) {
            return null;
        }
    };

    abstract String toString(Player player);

    public static String getMessages(Player player){
        return null;
    }

    private static String convertToSimpleMessage(Message message){
        //use reflection
        return null;
    }
}
