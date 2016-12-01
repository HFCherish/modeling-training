package com.thoughtworks.mobileCharge.domain.user;

import com.thoughtworks.mobileCharge.api.jersey.Routes;
import com.thoughtworks.mobileCharge.infrastructure.records.Record;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.thoughtworks.mobileCharge.util.LocaleFormatter.getCityAndCountry;

/**
 * Created by pzzheng on 11/30/16.
 */
public class PhoneCard implements Record{
    protected final String phoneNumber;
    protected final Locale locale;

    public PhoneCard(String phoneNumber, Locale locale) {
        this.phoneNumber = phoneNumber;
        this.locale = locale;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap() {{
            put("phone_number", phoneNumber);
            put("card_locale", getCityAndCountry(locale));
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }
}
