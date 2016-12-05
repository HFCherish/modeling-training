package com.thoughtworks.mobileCharge.api.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.mobileCharge.domain.user.MessageRecord;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by pzzheng on 12/2/16.
 */
@XmlRootElement
public class MessageRequestBean {
    private MessageRecord.Type type;
    private LocaleBean fromLocale;
    private CardBean target;
    private MessageRecord.SendType sendType;
    private Long createdAt;

    public MessageRequestBean(@JsonProperty(value = "type", required = true) MessageRecord.Type type,
                              @JsonProperty(value = "from_locale", required = true) LocaleBean fromLocale,
                              @JsonProperty(value = "target", required = true) CardBean target,
                              @JsonProperty(value = "send_type", required = true) MessageRecord.SendType sendType,
                              @JsonProperty(value = "createdAt", required = true) Long createdAt) {
        this.type = type;
        this.fromLocale = fromLocale;
        this.target = target;
        this.sendType = sendType;
        this.createdAt = createdAt;
    }

    public MessageRecord.Type getType() {
        return type;
    }

    public LocaleBean getFromLocale() {
        return fromLocale;
    }

    public CardBean getTarget() {
        return target;
    }

    public MessageRecord.SendType getSendType() {
        return sendType;
    }

    public Long getCreatedAt() {
        return createdAt;
    }
}
