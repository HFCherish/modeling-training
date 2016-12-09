package com.thoughtworks.mobileCharge.domain.user;

import org.bson.Document;

/**
 * Created by pzzheng on 12/9/16.
 */
public class ChargeTypeGroup {
    CommunicationRecord.CommunicationType communicationType;
    CallRecord.CallType callType;
    MessageRecord.Type messageType;
    MessageRecord.SendType sendType;

    private ChargeTypeGroup() {
    }

    public ChargeTypeGroup(CommunicationRecord.CommunicationType communicationType) {
        this.communicationType = communicationType;
    }

    public ChargeTypeGroup(CommunicationRecord.CommunicationType communicationType, CallRecord.CallType callType) {
        this.communicationType = communicationType;
        this.callType = callType;
    }

    public ChargeTypeGroup(CommunicationRecord.CommunicationType communicationType, MessageRecord.Type messageType, MessageRecord.SendType sendType) {
        this.communicationType = communicationType;
        this.messageType = messageType;
        this.sendType = sendType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChargeTypeGroup that = (ChargeTypeGroup) o;

        if (communicationType != that.communicationType) return false;
        if (callType != that.callType) return false;
        if (messageType != that.messageType) return false;
        return sendType == that.sendType;

    }

    @Override
    public int hashCode() {
        int result = communicationType.hashCode();
        result = 31 * result + (callType != null ? callType.hashCode() : 0);
        result = 31 * result + (messageType != null ? messageType.hashCode() : 0);
        result = 31 * result + (sendType != null ? sendType.hashCode() : 0);
        return result;
    }

    public static ChargeTypeGroup buildFromDocument(Document document) {
        if(document == null || document.isEmpty())  return null;
        ChargeTypeGroup chargeTypeGroup = new ChargeTypeGroup();
        chargeTypeGroup.communicationType = CommunicationRecord.CommunicationType.valueOf(document.getString("communication_type"));
        chargeTypeGroup.callType = document.containsKey("call_type") ? CallRecord.CallType.valueOf(document.getString("call_type")) : null;
        chargeTypeGroup.sendType = document.containsKey("send_type") ? MessageRecord.SendType.valueOf(document.getString("send_type")) : null;
        chargeTypeGroup.messageType = document.containsKey("message_type") ? MessageRecord.Type.valueOf(document.getString("message_type")) : null;

        return chargeTypeGroup;
    }
}
