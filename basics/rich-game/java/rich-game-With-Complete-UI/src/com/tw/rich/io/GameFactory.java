package com.tw.rich.io;

import com.tw.rich.core.Game;
import com.tw.rich.core.player.Player;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

/**
 * Created by pzzheng on 12/5/16.
 */
public class GameFactory {
    public static Game initGame(Scanner in, PrintStream out) {
        out.println("设置玩家初始资金，范围1000～50000（默认10000):");
        int initialFund = 10000;
        if(in.hasNextInt()) {
            initialFund = in.nextInt();
        }

        out.println("请选择2~4位不重复玩家，输入编号即可。(1.钱夫人; 2.阿土伯; 3.孙小美; 4.金贝贝) (如输入12): ");
        return createDefaultGame(initialFund, in.nextLine());
    }

    protected static Game createDefaultGame(int initialFund, String playerIds) {
        List<Player> players = PlayersFactory.getPlayers(playerIds, initialFund);
        return new Game(MapFactory.defaultMap(), players.toArray(new Player[players.size()]));
    }
}
