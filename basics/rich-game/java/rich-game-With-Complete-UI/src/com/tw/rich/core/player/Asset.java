package com.tw.rich.core.player;

import com.tw.rich.core.assistenceItems.Tool;
import com.tw.rich.core.places.Estate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pzzheng on 11/28/16.
 */
public class Asset {
    private int funds;
    private int points;
    private Map<Tool, Integer> tools;
    private List<Estate> estates;

    public Asset(int initialFund) {
        this.funds = initialFund;
        tools = new HashMap();
        estates = new ArrayList<>();
    }

    public int getFunds() {
        return funds;
    }

    public boolean hasEstate(Estate estate) {
        return estates.contains(estate);
    }

    public void buyEstate(Estate estate) {
        estates.add(estate);
        funds -= estate.getEmptyPrice();
    }

    public void addFunds(int increment) {
        funds += increment;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int increment) {
        points += increment;
    }

    public void buyTool(Tool tool) {
        tools.compute(tool, (k, v) -> v == null ? 1 : v + 1);
        points -= tool.getValue();
    }

    public boolean hasTool(Tool tool) {
        tools.compute(tool, (k, v) -> v == null ? 0 : v);
        return tools.get(tool) > 0;
    }

    public void sellTool(Tool tool) {
        tools.compute(tool, (k, v) -> v - 1);
        points += tool.getValue();
    }

    public void sellEstate(Estate estate) {
        estates.remove(estate);
        funds += estate.getEmptyPrice() * (estate.getLevel().ordinal() + 1) * 2;
    }

    public void useTool(Tool tool) {
        tools.compute(tool, (k,v) -> v-1);
    }
}
