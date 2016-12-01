package com.thoughtworks.mobileCharge.api.beans;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.mobileCharge.domain.user.CallRecord;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by pzzheng on 12/1/16.
 */
@XmlRootElement
public class CallRecordRequestBean {
//    @JsonProperty("duration")
    private DurationBean duration;

//    @JsonProperty("target")
    private CardBean target;

//    @JsonProperty(value = "call_type")
    private CallRecord.CallType callType;

//    @JsonProperty(value = "from_locale")
    LocaleBean fromLocale;

    public CallRecordRequestBean(@JsonProperty(value = "duration", required = true) DurationBean duration,
                                  @JsonProperty(value = "from_locale", required = true) LocaleBean fromLocale,
                                 @JsonProperty(value = "target", required = true) CardBean target,
                                 @JsonProperty(value = "call_type", required = true) CallRecord.CallType callType) {
        this.duration = duration;
        this.fromLocale = fromLocale;
        this.target = target;
        this.callType = callType;
    }

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
