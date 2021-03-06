package com.thoughtworks.mobileCharge.util;

import org.bson.Document;

import java.util.Locale;

/**
 * Created by pzzheng on 11/29/16.
 */
public class LocaleFormatter {
    public static String getCityAndCountry(Locale locale) {
        return String.join("-", locale.getDisplayVariant(), locale.getDisplayCountry());
    }

    public static Locale buildFromDocument(Document document) {
        if (document.isEmpty()) return null;
        return new Locale(document.getString("language"), document.getString("country"), document.getString("city"));
    }

    public static Document toDocument(Locale from_locale) {
        return new Document("language", from_locale.getLanguage())
                .append("country", from_locale.getCountry())
                .append("city", from_locale.getVariant());
    }
}
