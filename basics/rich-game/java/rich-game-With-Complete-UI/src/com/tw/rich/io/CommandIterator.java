package com.tw.rich.io;

import com.tw.rich.core.Game;
import com.tw.rich.core.commands.Command;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Scanner;

import static com.tw.rich.io.CommandSymbol.convertToCommand;

/**
 * Created by pzzheng on 11/27/16.
 */
public class CommandIterator implements Iterator<Command>{
    Scanner in;
    private final Game game;

    public CommandIterator(InputStream in, Game game) {
        this.game = game;
        this.in = new Scanner(in);
    }

    @Override
    public boolean hasNext() {
        return in.hasNextLine();
    }

    @Override
    public Command next() {
        return convertToCommand(in.nextLine(), game);
    }
}
