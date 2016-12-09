package com.tw.rich.io;

import com.tw.rich.core.assistenceItems.Tool;
import com.tw.rich.core.messages.Message;
import com.tw.rich.core.places.Estate;
import com.tw.rich.core.places.Hospital;
import com.tw.rich.core.places.Prison;
import com.tw.rich.core.player.Player;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Created by pzzheng on 11/27/16.
 */
public interface SimpleMessage {
    HashMap<Message, SimpleMessage> messages = new HashMap<Message, SimpleMessage>() {{
        put(Message.COME_TO, player -> "您现在处于" + player.currentPlace().getClass().getSimpleName());
        put(Message.IF_UPGRADE, player -> {
            Estate currentPlace = (Estate)player.currentPlace();
            return "这是您的房产, 当前级别为" + currentPlace.getLevel().name()
                    + ". 是否升级该处地产? 开销为: " + currentPlace.getEmptyPrice()
                    + ". 请输入 Y/N 继续.";
        });
        put(Message.IF_BUY_ESTATE, player -> {
            Estate currentPlace = (Estate)player.currentPlace();
            return "这是一块空地, 开销为: " + currentPlace.getEmptyPrice()
                    + ". 是否购买该处地产? 请输入 Y/N 继续.";
        });
        put(Message.TO_BUY_TOOL, player -> {
            return "欢迎光临道具屋, 请选择您所需要的道具(输入F退出道具屋): \n" +
            toolHouseInfo();
        });
        put(Message.TO_SELECT_GIFT, player -> {
            return "欢迎光临礼品屋, 请选择您所需要的礼品: \n" +
                    giftHouseInfo();
        });
        put(Message.TO_CHARGE_TOLL, player -> "您在别人的地盘, 需要交过路费.");
        put(Message.GOD_POSSESSION, player -> "福神附身，免过路费");
        put(Message.FREE_FOR_OWNER_STUCK, player -> "房主在监狱或医院, 免过路费");
        put(Message.BANKRUPT, player -> "破产");
        put(Message.STUCK_IN_PRISON, player -> "监狱待" + Prison.PRISON_DAYS + "天");
        put(Message.STOP_AT_BLOCK, player -> "遇到路障停止");
        put(Message.ENCOUNTER_BOMB, player -> "遇到炸弹,进医院休养" + Hospital.HOSPITAL_DAYS + "天");
        put(Message.GET_MINERAL_POINT, player -> "获得点数");
        put(Message.SKIP_TURN_IN_PRISON, player -> "还要继续待监狱, 跳过一轮");
        put(Message.SKIP_TURN_IN_HOSPITAL, player -> "还要继续待医院, 跳过一轮");
        put(Message.WINNER, player -> player.getIdentity().getName() + ", 恭喜获胜!");
    }

        private String giftHouseInfo() {
            return null;
        }

        private String toolHouseInfo() {
            return null;
        }
    };

    String toString(Player player);

    static String getMessages(Player player) {
        return  player.getMessages().stream().map(m -> messages.get(m).toString(player)).collect(Collectors.joining("\n"));
    }
}
