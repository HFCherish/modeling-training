package com.thoughtworks.mobileCharge.api.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by pzzheng on 12/1/16.
 */
@XmlRootElement
public class CardBean {
    @JsonProperty("phone_number")
    String phoneNumber;

    @JsonProperty("card_locale")
    LocaleBean cardLocale;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocaleBean getCardLocale() {
        return cardLocale;
    }
}
