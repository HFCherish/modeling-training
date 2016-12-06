package com.tw.rich.io;

import com.tw.rich.core.Game;
import com.tw.rich.core.assistenceItems.Tool;
import com.tw.rich.core.map.GameMap;
import com.tw.rich.core.places.Estate;
import com.tw.rich.core.places.Hospital;
import com.tw.rich.core.places.Place;
import com.tw.rich.core.player.Player;
import com.tw.rich.core.Identity;
import com.tw.rich.util.ColorCodes;
import org.junit.Test;

import static com.tw.rich.core.player.Player.createWithIdentityAndFund_WaitTurn;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by pzzheng on 12/6/16.
 */
public class PlaceSymbolTest {
    GameMap map = DefaultMap.getMap();

    @Test
    public void should_convert_to_symbol() {
        Place estate = new Estate(1000);
//        System.out.println(estate.getClass());
//        System.out.println(((Estate)estate).getLevel().ordinal());

        ((Estate) estate).upgrade();
        assertThat(PlaceSymbol.convertToSymbol(estate, map), is("1"));
        assertThat(PlaceSymbol.convertToSymbol(new Hospital(), map), is("H"));

        estate.setTool(Tool.BLOCK);
        assertThat(PlaceSymbol.convertToSymbol(estate, map), is("#"));
    }

    @Test
    public void should_show_right_color_for_estate() {
        Estate estate = new Estate(1000);
        estate.sellTo(createWithIdentityAndFund_WaitTurn(new Identity("1", "test", ColorCodes.BLUE, "T"), 1000));
        System.out.println(PlaceSymbol.convertToSymbol(estate, map));
    }

    @Test
    public void should_player_symbol_at_players_current_place() {
        Estate estate = new Estate(1000);
        Player player = createWithIdentityAndFund_WaitTurn(new Identity("1", "test", ColorCodes.BLUE, "T"), 1000);
        player.moveTo(estate);
        map.initPlayers(player);
        assertThat(PlaceSymbol.convertToSymbol(estate, map), is("T"));
    }
}