package com.tw.rich.io;

import com.tw.rich.core.commands.Command;

import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by pzzheng on 11/27/16.
 */
public class CommandIterator implements Iterator<Command>{
    InputStream in;
    Command command;

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Command next() {
        return null;
    }
}
