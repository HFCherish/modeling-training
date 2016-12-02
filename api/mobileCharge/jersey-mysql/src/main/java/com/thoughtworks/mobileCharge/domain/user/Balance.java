package com.thoughtworks.mobileCharge.domain.user;

import com.thoughtworks.mobileCharge.api.jersey.Routes;
import com.thoughtworks.mobileCharge.domain.ChargeType;
import com.thoughtworks.mobileCharge.infrastructure.records.Record;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 * Created by pzzheng on 11/29/16.
 */
public class Balance implements Record {
    private static double FREE_CHARGE = 0.0;
    private double remainedMoney;
    private HashMap<CallRecord.CallChargeType, MessageRecord.ChargeBalance> callAccounts;
    private HashMap<MessageRecord.MessageChargeType, MessageRecord.ChargeBalance> messageAccounts;
    private HashMap<DataAccessRecord.DataAccessChargeType, MessageRecord.ChargeBalance> dataAccessAccounts;

    public Balance() {
        remainedMoney = 0.0;

        callAccounts = new HashMap();
        for (CommunicationRecord.CommunicationType communicationType : CommunicationRecord.CommunicationType.values()) {
            for (CallRecord.CallType callType : CallRecord.CallType.values()) {
                callAccounts.put(new CallRecord.CallChargeType(communicationType, callType), new MessageRecord.ChargeBalance());
            }
        }

        messageAccounts = new HashMap();
        for (CommunicationRecord.CommunicationType communicationType : CommunicationRecord.CommunicationType.values()) {
            for (MessageRecord.Type messageType : MessageRecord.Type.values()) {
                for (MessageRecord.SendType sendType : MessageRecord.SendType.values()) {
                    messageAccounts.put(new MessageRecord.MessageChargeType(communicationType, messageType, sendType), new MessageRecord.ChargeBalance());
                }
            }
        }

        dataAccessAccounts = new HashMap<>();
        Stream.of(CommunicationRecord.CommunicationType.values()).forEach(type -> {
            dataAccessAccounts.put(new DataAccessRecord.DataAccessChargeType(type), new MessageRecord.ChargeBalance());
        });

    }

    public double charge(CommunicationRecord record, BiFunction<CommunicationRecord, Balance, Double> chargeStrategy) {
        return chargeStrategy.apply(record, this);
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap() {{
            put("remainedMoney", remainedMoney);
            put("remainedData", new HashMap() {{
                put("local", dataAccessAccounts.get(new DataAccessRecord.DataAccessChargeType(CommunicationRecord.CommunicationType.LOCAL)).freeNumbers);
                put("internal", dataAccessAccounts.get(new DataAccessRecord.DataAccessChargeType(CommunicationRecord.CommunicationType.INTERNAL)).freeNumbers);
                put("international", dataAccessAccounts.get(new DataAccessRecord.DataAccessChargeType(CommunicationRecord.CommunicationType.INTERNATIONAL)).freeNumbers);
            }});
            put("remainedCallMinutes", new HashMap() {{
                put("local", callAccounts.get(new CallRecord.CallChargeType(CommunicationRecord.CommunicationType.LOCAL, CallRecord.CallType.CALLER)).freeNumbers +
                        callAccounts.get(new CallRecord.CallChargeType(CommunicationRecord.CommunicationType.LOCAL, CallRecord.CallType.CALLEE)).freeNumbers);
                put("internal", callAccounts.get(new CallRecord.CallChargeType(CommunicationRecord.CommunicationType.INTERNAL, CallRecord.CallType.CALLER)).freeNumbers +
                        callAccounts.get(new CallRecord.CallChargeType(CommunicationRecord.CommunicationType.INTERNAL, CallRecord.CallType.CALLEE)).freeNumbers);
                put("internal", callAccounts.get(new CallRecord.CallChargeType(CommunicationRecord.CommunicationType.INTERNATIONAL, CallRecord.CallType.CALLER)).freeNumbers +
                        callAccounts.get(new CallRecord.CallChargeType(CommunicationRecord.CommunicationType.INTERNATIONAL, CallRecord.CallType.CALLEE)).freeNumbers);
            }});

        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }

    public static class ChargeStrategies {
        public static BiFunction<CommunicationRecord, Balance, Double> callCharge() {
            return (record, balance) -> {
                double charge = 0.0;

                MessageRecord.ChargeBalance callChargeBalance = balance.callAccounts.get(new CallRecord.CallChargeType(record.communicationType, ((CallRecord) record).callType));
                Long costMinutes = ((CallRecord) record).duration.getStandardMinutes();
                long freeMinutes = callChargeBalance.freeNumbers;
                if (freeMinutes > costMinutes) {
                    callChargeBalance.addFreeNumber(-costMinutes);
                } else {
                    callChargeBalance.addFreeNumber(-freeMinutes);
                    charge = callChargeBalance.unitPrice * (costMinutes - freeMinutes);
                    balance.remainedMoney -= charge;
                }

                return charge;
            };
        }

        public static BiFunction<CommunicationRecord, Balance, Double> dataAccessCharge() {
            return (record, balance) -> {
                double charge = 0.0;

                if (((DataAccessRecord) record).chargeType.equals(ChargeType.CHARGE)) {
                    MessageRecord.ChargeBalance dataAccessChargeBalance = balance.dataAccessAccounts.get(new DataAccessRecord.DataAccessChargeType(record.communicationType));
                    Long costData = ((DataAccessRecord) record).data;
                    long freeData = dataAccessChargeBalance.freeNumbers;
                    if (freeData > costData) {
                        dataAccessChargeBalance.addFreeNumber(-costData);
                    } else {
                        dataAccessChargeBalance.addFreeNumber(-freeData);
                        charge = dataAccessChargeBalance.unitPrice * (costData - freeData);
                        balance.remainedMoney -= charge;
                    }
                }

                return charge;
            };
        }

        public static BiFunction<CommunicationRecord, Balance, Double> messageCharge() {
            return (record, balance) -> {
                double charge = 0.0;

                MessageRecord.ChargeBalance messageChargeBalance = balance.messageAccounts.get(new MessageRecord.MessageChargeType(record.communicationType,
                        ((MessageRecord) record).type,
                        ((MessageRecord) record).sendType));
                if (messageChargeBalance.freeNumbers > 0) {
                    messageChargeBalance.addFreeNumber(-1);
                } else {
                    charge = messageChargeBalance.unitPrice;
                    balance.remainedMoney -= charge;
                }

                return charge;
            };
        }
    }
}
