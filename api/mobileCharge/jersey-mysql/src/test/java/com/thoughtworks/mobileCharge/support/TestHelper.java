package com.thoughtworks.mobileCharge.support;

import com.thoughtworks.mobileCharge.domain.user.Balance;
import com.thoughtworks.mobileCharge.domain.user.MessageRecord;
import com.thoughtworks.mobileCharge.domain.user.PhoneCard;
import com.thoughtworks.mobileCharge.domain.user.User;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Locale;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class TestHelper {
    public static User getUser(Balance balance) {
        return new User("410111222233445566", balance, new PhoneCard("13241667788", new Locale("zh", "CN", "beijing")));
    }

    public static User getSpyUser(Balance balance) {
        User user = new User("410111222233445566", balance, new PhoneCard("13241667788", new Locale("zh", "CN", "beijing")));
        return spy(user);
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

    public static HashMap phoneCardMap() {
        return new HashMap() {{
            put("phone_number", "12332323212");
            put("card_locale", beijingLocaleMap());
        }};
    }

    public static HashMap messageRecordMap() {
        return new HashMap() {{
            put("type", MessageRecord.Type.MMS);
            put("from_locale", beijingLocaleMap());
            put("target", phoneCardMap());
            put("send_type", MessageRecord.SendType.SENDER);
            put("createdAt", new DateTime().getMillis());
        }};
    }
}
