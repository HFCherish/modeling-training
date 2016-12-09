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
        return null;
    }
}
