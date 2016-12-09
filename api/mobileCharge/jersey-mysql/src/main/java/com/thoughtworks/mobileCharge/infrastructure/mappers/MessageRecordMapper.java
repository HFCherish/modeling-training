package com.thoughtworks.mobileCharge.infrastructure.mappers;

import com.thoughtworks.mobileCharge.domain.user.MessageRecord;

/**
 * Created by pzzheng on 12/9/16.
 */
public interface MessageRecordMapper {
    MessageRecord saveMessage(MessageRecord newMessage);

}
