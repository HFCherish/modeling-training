package com.tw.rich.core.player;

/**
 * Created by pzzheng on 12/6/16.
 */
public class PlayerIdentity {
    private final String id;
    private final String name;
    private final String color;

    public PlayerIdentity(String id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
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
}
