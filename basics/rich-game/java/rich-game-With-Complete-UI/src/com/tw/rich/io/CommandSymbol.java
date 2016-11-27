package com.tw.rich.io;

import com.tw.rich.core.commands.Command;

/**
 * Created by pzzheng on 11/27/16.
 */
public enum  CommandSymbol {
    ROLL {
        @Override
        Command getCommand() {
            return null;
        }
    };

    public static Command convertToCommand(String command){
        //use reflection
        return null;
    }

    abstract Command getCommand();
}
