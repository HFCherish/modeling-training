package com.thoughtworks.mobileCharge.api.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by pzzheng on 12/1/16.
 */
@XmlRootElement
public class DurationBean {
//    @JsonProperty("start")
    Long start;

//    @JsonProperty("end")
    Long end;

//    @JsonCreator
    public DurationBean(@JsonProperty(value = "start", required = true) Long start,
                        @JsonProperty(value = "end", required = true) Long end) {
        this.start = start;
        this.end = end;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }
}
