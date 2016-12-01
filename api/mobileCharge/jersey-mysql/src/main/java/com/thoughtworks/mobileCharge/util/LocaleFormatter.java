package com.thoughtworks.mobileCharge.util;

import com.thoughtworks.mobileCharge.api.beans.LocaleBean;

import java.util.Locale;
import java.util.Map;

/**
 * Created by pzzheng on 11/29/16.
 */
public class LocaleFormatter {
    public static String getCityAndCountry(Locale locale) {
        return String.join("-", locale.getDisplayVariant(), locale.getDisplayCountry());
    }

    public static Locale getLocaleFrom(Map<String, Object> localeInfo) {
        return new Locale(localeInfo.get("language").toString(),
                localeInfo.get("country").toString(),
                localeInfo.get("city").toString().toLowerCase());
    }

    public static Locale getLocaleFrom(LocaleBean localeBean) {
        return new Locale(localeBean.getLanguage(),
                localeBean.getCountry(),
                localeBean.getCity().toLowerCase());
    }
}
