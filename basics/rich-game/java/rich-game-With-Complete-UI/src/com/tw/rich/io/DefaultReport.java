package com.tw.rich.io;

import com.sun.org.apache.regexp.internal.RE;
import com.tw.rich.core.Reportable;
import com.tw.rich.core.player.Player;

/**
 * Created by pzzheng on 11/28/16.
 */
public class DefaultReport implements Reportable {
    private static DefaultReport report = new DefaultReport();

    public static DefaultReport getReport() {
        return report;
    }

    @Override
    public void showPlayer(Player player) {

    }

    @Override
    public void showHelp() {

    }
}

