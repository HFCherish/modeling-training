package com.thoughtworks.ketsu.domain.user;

import com.thoughtworks.ketsu.domain.EntityId;
import com.thoughtworks.ketsu.util.IdGenerator;

/**
 * Created by pzzheng on 11/29/16.
 */
public class User {
    private final String phoneNumber;
    private final String locale;
    private final String idCard;
    private final EntityId id;
    private Balance initialBalance;

    public User(String phoneNumber, String locale, String idCard, Balance initialBalance) {
        this.initialBalance = initialBalance;
        this.id = new EntityId(IdGenerator.next());
        this.phoneNumber = phoneNumber;
        this.locale = locale;
        this.idCard = idCard;
    }

    public EntityId getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id != null ? id.equals(user.id) : user.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
