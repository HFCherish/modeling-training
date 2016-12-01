package com.thoughtworks.mobileCharge.api.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by pzzheng on 12/1/16.
 */
@XmlRootElement
public class DurationBean {
    @JsonProperty("start")
    long start;

    @JsonProperty("end")
    long end;

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }
}
