package com.thoughtworks.mobileCharge.api.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.mobileCharge.domain.ChargeType;
import com.thoughtworks.mobileCharge.domain.user.DataAccessRecord;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by pzzheng on 12/2/16.
 */
@XmlRootElement
public class DataAccessRequestBean {
    private LocaleBean fromLocale;
    private String targetConsumer;
    private ChargeType chargeType;
    private Long data;
    private DataAccessRecord.WebType webType;
    private Long createdAt;

    public DataAccessRequestBean(@JsonProperty(value = "from_locale", required = true) LocaleBean fromLocale,
                                 @JsonProperty(value = "target_consumer", required = true) String targetConsumer,
                                 @JsonProperty(value = "charge_type", required = true) ChargeType chargeType,
                                 @JsonProperty(value = "data", required = true) Long data,
                                 @JsonProperty(value = "web_type", required = true) DataAccessRecord.WebType webType,
                                 @JsonProperty(value = "createdAt", required = true) Long createdAt) {
        this.fromLocale = fromLocale;
        this.targetConsumer = targetConsumer;
        this.chargeType = chargeType;
        this.data = data;
        this.webType = webType;
        this.createdAt = createdAt;
    }

    public LocaleBean getFromLocale() {
        return fromLocale;
    }

    public String getTargetConsumer() {
        return targetConsumer;
    }

    public ChargeType getChargeType() {
        return chargeType;
    }

    public Long getData() {
        return data;
    }

    public DataAccessRecord.WebType getWebType() {
        return webType;
    }

    public Long getCreatedAt() {
        return createdAt;
    }
}
