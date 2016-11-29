package com.thoughtworks.ketsu.domain.user;

import java.util.function.Function;

/**
 * Created by pzzheng on 11/29/16.
 */
public class Balance {
    public double charge(CommunicationRecord record, Function<CommunicationRecord, Double> chargeStrategy) {
        return chargeStrategy.apply(record);
    }

    public static class ChargeStrategies {
        public static Function<CommunicationRecord, Double> callCharge() {
            return record -> 0.0;
        }
    }
}
