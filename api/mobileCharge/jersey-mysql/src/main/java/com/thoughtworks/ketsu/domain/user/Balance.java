package com.thoughtworks.ketsu.domain.user;

import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 * Created by pzzheng on 11/29/16.
 */
public class Balance {
    private double remainedMoney;
    private HashMap<CallRecord.CommunicationType, Integer> freeCallMinutes;
    private int freeMessages;

    public Balance() {
        remainedMoney = 0.0;
        Stream.of(CallRecord.CommunicationType.values()).map(type -> freeCallMinutes.put(type, 0));
        freeMessages = 0;
    }

    public double charge(CommunicationRecord record, BiFunction<CommunicationRecord, Balance, Double> chargeStrategy) {
        return chargeStrategy.apply(record, this);
    }

    public static class ChargeStrategies {
        public static BiFunction<CommunicationRecord, Balance, Double> callCharge() {
            return (record, balance) -> {
                return 0.0;
            };
        }
    }
}
