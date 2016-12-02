package com.thoughtworks.mobileCharge.domain.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private HashMap<CallRecord.CommunicationType, Double> callerChargeUnitPrice;
    private HashMap<CallRecord.CommunicationType, Double> calleeChargeUnitPrice;
    private HashMap<MessageRecord.MessageChargeType, MessageRecord.ChargeBalance> messageAccounts;

    public Balance() {
        remainedMoney = 0.0;
        Stream.of(CommunicationRecord.CommunicationType.values()).forEach(type -> {
            freeCallMinutes.put(type, 0);
            callerChargeUnitPrice.put(type, 0.1);
        });

        messageAccounts = new HashMap();
        for(CommunicationRecord.CommunicationType communicationType : CommunicationRecord.CommunicationType.values()) {
            for(MessageRecord.Type messageType: MessageRecord.Type.values()) {
                for(MessageRecord.SendType sendType: MessageRecord.SendType.values()) {
                    messageAccounts.put(new MessageRecord.MessageChargeType(communicationType, messageType, sendType), new MessageRecord.ChargeBalance());
                }
            }
        }
        freeMessages = 0;
    }

    public double charge(CommunicationRecord record, BiFunction<CommunicationRecord, Balance, Double> chargeStrategy) {
        return chargeStrategy.apply(record, this);
    }

    public static class ChargeStrategies {
        public static BiFunction<CommunicationRecord, Balance, Double> callCharge() {
            return (record, balance) -> {
                double charge = 0.0;
                CommunicationRecord.CommunicationType communicationType = record.communicationType;
                int costMinutes = (int) ((CallRecord) record).duration.getStandardMinutes();

                if (((CallRecord) record).callType.equals(CallRecord.CallType.CALLEE)) {
                    charge = costMinutes * balance.calleeChargeUnitPrice.get(communicationType);
                }

                else {
                    int freeMinutes = balance.freeCallMinutes.get(communicationType);
                    balance.freeCallMinutes.compute(communicationType, (k, v) -> v - Math.min(freeMinutes, costMinutes));
                    if (freeMinutes >= costMinutes) {
                        charge = FREE_CHARGE;
                    }
                    else {
                        charge = (costMinutes - freeMinutes) * balance.callerChargeUnitPrice.get(communicationType);
                    }
                }

                balance.remainedMoney -= charge;

                return charge;
            };
        }

        public static BiFunction<CommunicationRecord, Balance, Double> messageCharge() {
            return (record, balance) -> {
                double charge = 0.0;

                MessageRecord.ChargeBalance messageChargeBalance = balance.messageAccounts.get(new MessageRecord.MessageChargeType(record.communicationType,
                        ((MessageRecord) record).type,
                        ((MessageRecord) record).sendType));
                if(messageChargeBalance.freeNumbers > 0) {
                    messageChargeBalance.addFreeNumber(-1);
                }
                else {
                    charge = messageChargeBalance.chargePrice;
                    balance.remainedMoney -= charge;
                }

                return charge;
            };

        }
    }
}
