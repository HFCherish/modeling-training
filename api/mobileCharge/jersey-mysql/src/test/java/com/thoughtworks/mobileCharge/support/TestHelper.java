package com.thoughtworks.mobileCharge.support;

import com.thoughtworks.mobileCharge.domain.ChargeType;
import com.thoughtworks.mobileCharge.domain.user.*;
import com.thoughtworks.mobileCharge.infrastructure.util.SafetyInjector;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.mockito.Mockito.*;

public class TestHelper {
    public static User getUser(Balance balance) {
        return SafetyInjector.injectMembers(new User("410111222233445566", balance, new PhoneCard("13241667788", new Locale("zh", "CN", "beijing"))));
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
            put("type", MessageRecord.Type.MMS.name());
            put("from_locale", beijingLocaleMap());
            put("target", phoneCardMap());
            put("send_type", MessageRecord.SendType.SENDER.name());
            put("createdAt", new DateTime().getMillis());
        }};
    }


    public static Map<String, Object> dataAccessRecordMap() {
        return new HashMap() {{
            put("charge_type", ChargeType.CHARGE.name());
            put("from_locale", beijingLocaleMap());
            put("target_consumer", "163.music.com");
            put("data", 12);
            put("web_type", DataAccessRecord.WebType.G4);
            put("createdAt", new DateTime().getMillis());
        }};

    }
}
