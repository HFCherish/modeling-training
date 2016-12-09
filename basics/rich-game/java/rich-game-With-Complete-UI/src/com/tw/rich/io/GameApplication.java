package com.tw.rich.io;

import com.tw.rich.core.Game;
import com.tw.rich.core.messages.Message;
import com.tw.rich.core.player.Player;

import java.io.PrintStream;
import java.util.Scanner;

import static com.tw.rich.io.GameFactory.initGame;
import static com.tw.rich.io.MapPrinter.flush;

/**
 * Created by pzzheng on 12/6/16.
 */
public class GameApplication {

    public static void main(String[] args) {
        GameApplication game = new GameApplication();
        game.start(new Scanner(System.in), System.out);
    }

    public void start(Scanner in, PrintStream out) {
        Game game = initGame(in, out);
        CommandIterator commandIterator = new CommandIterator(in, game);

        flush(game.getMap());
        out.println(game.currentPlayer().getIdentity().getName() + ">");
        commandIterator.forEachRemaining(command -> {
            Player player = game.currentPlayer();
            game.execute(command);

            out.println(SimpleMessage.getMessages(player));
            player.resetMessage();

            if(game.status.equals(Game.Status.END)) {
                if(game.getWinner() !=null) {
                    SimpleMessage.messages.get(Message.WINNER).toString(game.getWinner());
                }
                System.exit(0);
            }
            flush(game.getMap());
            out.println(game.currentPlayer().getIdentity().getName() + ">");
        });
    }
}
