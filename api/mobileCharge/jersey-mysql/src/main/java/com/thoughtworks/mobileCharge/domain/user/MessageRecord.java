package com.thoughtworks.mobileCharge.domain.user;

/**
 * Created by pzzheng on 11/30/16.
 */
public class MessageRecord extends CommunicationRecord {
    public enum Type {SMS, MMS}

    public enum SendType {SENDER, RECEIVER}
}
