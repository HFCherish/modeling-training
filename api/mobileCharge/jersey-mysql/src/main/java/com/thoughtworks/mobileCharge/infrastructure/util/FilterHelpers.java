package com.thoughtworks.mobileCharge.infrastructure.util;

import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.joda.time.DateTime;

import static com.mongodb.client.model.Filters.and;

/**
 * Created by pzzheng on 12/9/16.
 */
public class FilterHelpers {
    public static Bson getMonthFilter(int month) {
        DateTime current = new DateTime();
        DateTime targetStart = new DateTime(current.getYear(), month, 1, 0, 0);
        DateTime targetEnd = targetStart.plusMonths(1);
        return and(Filters.gte("start", targetStart.getMillis()),
                Filters.lt("start", targetEnd.getMillis()));
    }
}
