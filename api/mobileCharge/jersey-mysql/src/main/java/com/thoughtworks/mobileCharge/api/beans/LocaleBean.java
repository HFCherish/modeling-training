package com.thoughtworks.mobileCharge.api.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Locale;

/**
 * Created by pzzheng on 12/1/16.
 */
@XmlRootElement
public class LocaleBean {
//    @JsonProperty("country")
    String country;

//    @JsonProperty("city")
    String city;

//    @JsonProperty("language")
    String language;

//    @JsonCreator
    public LocaleBean(@JsonProperty(value = "country", required = true) String country,
                      @JsonProperty(value = "city", required = true) String city,
                      @JsonProperty(value = "language", required = true) String language) {
        this.country = country;
        this.city = city;
        this.language = language;
    }

    public Locale getLocale() {
        return new Locale(language, country, city.toLowerCase());
    }
}
