package com.tw.rich.core;


import com.tw.rich.core.commands.Command;

/**
 * Created by pzzheng on 11/27/16.
 */
public class Player {

    public Status execute(Command command) {
        return null;
    }

    public enum Status {
        WAIT_FOR_TURN {
            @Override
            Status action(Player player, Command command) {
                return null;
            }
        }, WAIT_FORM_COMMAND {
            @Override
            Status action(Player player, Command command) {
                return null;
            }
        }, BANKRUPT {
            @Override
            Status action(Player player, Command command) {
                return null;
            }
        };

        abstract Status action(Player player, Command command);
    }
}
