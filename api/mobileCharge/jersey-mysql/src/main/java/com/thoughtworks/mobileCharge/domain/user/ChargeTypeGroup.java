package com.thoughtworks.mobileCharge.domain.user;

/**
 * Created by pzzheng on 12/9/16.
 */
public class ChargeTypeGroup {
    CommunicationRecord.CommunicationType communicationType;
    CallRecord.CallType callType;
    MessageRecord.Type type;
    MessageRecord.SendType sendType;

    public ChargeTypeGroup(CommunicationRecord.CommunicationType communicationType) {
        this.communicationType = communicationType;
    }

    public ChargeTypeGroup(CommunicationRecord.CommunicationType communicationType, CallRecord.CallType callType) {
        this.communicationType = communicationType;
        this.callType = callType;
    }

    public ChargeTypeGroup(CommunicationRecord.CommunicationType communicationType, MessageRecord.Type type, MessageRecord.SendType sendType) {
        this.communicationType = communicationType;
        this.type = type;
        this.sendType = sendType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChargeTypeGroup that = (ChargeTypeGroup) o;

        if (communicationType != that.communicationType) return false;
        if (callType != that.callType) return false;
        if (type != that.type) return false;
        return sendType == that.sendType;

    }

    @Override
    public int hashCode() {
        int result = communicationType.hashCode();
        result = 31 * result + (callType != null ? callType.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (sendType != null ? sendType.hashCode() : 0);
        return result;
    }
}
