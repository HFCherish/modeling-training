package com.tw.orm.testObjects;

/**
 * Created by pzzheng on 12/28/16.
 */
public class User extends Person{
    String id;
    String username;
    String nickname;

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public User setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }
}
