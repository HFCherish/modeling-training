package com.thoughtworks.mobileCharge.domain.user;

import java.util.Locale;

/**
 * Created by pzzheng on 11/29/16.
 */
public abstract class CommunicationRecord {
    protected CommunicationType communicationType;

    public enum CommunicationType {
        LOCAL, INTERNAL, INTERNATIONAL, communicationType;

        public static CommunicationType typeOf(Locale from_locale, Locale locale) {
            if (from_locale.equals(locale)) return LOCAL;
            if (from_locale.getCountry().equals(locale.getCountry())) return INTERNAL;
            return INTERNATIONAL;
        }
    }
}
