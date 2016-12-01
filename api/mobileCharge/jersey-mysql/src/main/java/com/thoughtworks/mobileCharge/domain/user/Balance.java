package com.thoughtworks.mobileCharge.domain.user;

import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 * Created by pzzheng on 11/29/16.
 */
public class Balance {
    private static double FREE_CHARGE = 0.0;
    private double remainedMoney;
    private HashMap<CallRecord.CommunicationType, Integer> freeCallMinutes;
    private int freeMessages;
    private HashMap<CallRecord.CommunicationType, Double> callerChargeRates;
    private HashMap<CallRecord.CommunicationType, Double> calleeChargeRates;

    public Balance() {
        remainedMoney = 0.0;
        Stream.of(CallRecord.CommunicationType.values()).forEach(type -> {
            freeCallMinutes.put(type, 0);
            callerChargeRates.put(type, 0.1);
        });
        freeMessages = 0;
    }

    public double charge(CommunicationRecord record, BiFunction<CommunicationRecord, Balance, Double> chargeStrategy) {
        return chargeStrategy.apply(record, this);
    }

    public static class ChargeStrategies {
        public static BiFunction<CommunicationRecord, Balance, Double> callCharge() {
            return (record, balance) -> {
                double charge = 0.0;
                CallRecord.CommunicationType communicationType = ((CallRecord) record).communicationType;
                int costMinutes = (int) ((CallRecord) record).duration.getStandardMinutes();

                if (((CallRecord) record).callType.equals(CallRecord.CallType.CALLEE)) {
                    charge = costMinutes * balance.calleeChargeRates.get(communicationType);
                }

                else {
                    int freeMinutes = balance.freeCallMinutes.get(communicationType);
                    balance.freeCallMinutes.compute(communicationType, (k, v) -> v - Math.min(freeMinutes, costMinutes));
                    if (freeMinutes >= costMinutes) {
                        charge = FREE_CHARGE;
                    }
                    else {
                        charge = (costMinutes - freeMinutes) * balance.callerChargeRates.get(communicationType);
                    }
                }

                balance.remainedMoney -= charge;

                return charge;
            };
        }
    }
}
