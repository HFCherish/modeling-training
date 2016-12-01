package com.thoughtworks.mobileCharge.api.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by pzzheng on 12/1/16.
 */
@XmlRootElement
public class LocaleBean {
    @JsonProperty("country")
    String country;

    @JsonProperty("city")
    String city;

    @JsonProperty("language")
    String language;

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getLanguage() {
        return language;
    }
}
