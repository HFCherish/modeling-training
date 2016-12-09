package com.thoughtworks.mobileCharge.infrastructure.mappers;

import com.thoughtworks.mobileCharge.domain.user.MessageRecord;
import com.thoughtworks.mobileCharge.domain.user.User;

import java.util.List;

/**
 * Created by pzzheng on 12/9/16.
 */
public interface MessageRecordMapper {
    MessageRecord saveMessage(MessageRecord newMessage);

    long countOf(User user, int month);

    List<MessageRecord> findAllOf(User user, int month, int page, int perPage);
}
