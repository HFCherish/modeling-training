package com.tw.rich.core;

/**
 * Created by pzzheng on 12/6/16.
 */
public class Identity {
    private final String id;
    private final String name;
    private String color;
    private String symbol;

    public Identity(String id, String name, String color, String symbol) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.symbol = symbol;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getSymbol() {
        return symbol;
    }
}
