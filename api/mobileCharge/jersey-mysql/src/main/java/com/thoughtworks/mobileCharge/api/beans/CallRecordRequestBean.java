package com.thoughtworks.mobileCharge.api.beans;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.mobileCharge.domain.user.CallRecord;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by pzzheng on 12/1/16.
 */
@XmlRootElement
public class CallRecordRequestBean {
    @JsonProperty("duration")
    DurationBean duration;

    @JsonProperty("from_locale")
    LocaleBean fromLocale;

    @JsonProperty("target")
    CardBean target;

    @JsonProperty("call_type")
    CallRecord.CallType callType;

    public CallRecord.CallType getCallType() {
        return callType;
    }

    public DurationBean getDuration() {
        return duration;
    }

    public LocaleBean getFromLocale() {
        return fromLocale;
    }

    public CardBean getTarget() {
        return target;
    }
}
