package com.thoughtworks.mobileCharge.support;

import com.thoughtworks.mobileCharge.domain.user.Balance;
import com.thoughtworks.mobileCharge.domain.user.PhoneCard;
import com.thoughtworks.mobileCharge.domain.user.User;

import java.util.HashMap;
import java.util.Locale;

public class TestHelper {
    public static User getUser(Balance balance) {
        return new User("410111222233445566", balance, new PhoneCard("13241667788", new Locale("zh", "CN", "beijing")));
    }

    public static HashMap<String, Object> beijingLocaleMap() {
        return new HashMap() {{
            put("city", "beijing");
            put("country", "CN");
            put("language", "zh");
        }};
    }

    public static Locale beijingLocale() {
        return new Locale("zh", "CN", "beijing");
    }

}
