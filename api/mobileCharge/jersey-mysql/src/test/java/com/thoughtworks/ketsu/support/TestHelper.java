package com.thoughtworks.ketsu.support;

import com.thoughtworks.ketsu.domain.user.Balance;
import com.thoughtworks.ketsu.domain.user.User;

import java.util.Locale;

public class TestHelper {
    public static User getUser(Balance balance) {
        return new User("13241667788", new Locale("zh", "CN", "beijing"), "410111222233445566", balance);
    }
}
