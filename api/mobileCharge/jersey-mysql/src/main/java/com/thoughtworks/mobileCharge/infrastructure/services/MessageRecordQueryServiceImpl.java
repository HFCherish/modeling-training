package com.thoughtworks.mobileCharge.infrastructure.services;

import com.thoughtworks.mobileCharge.api.services.MessageRecordQueryService;
import com.thoughtworks.mobileCharge.domain.PaginatedList;
import com.thoughtworks.mobileCharge.domain.user.MessageRecord;
import com.thoughtworks.mobileCharge.domain.user.User;
import com.thoughtworks.mobileCharge.infrastructure.mappers.MessageRecordMapper;

import javax.inject.Inject;

/**
 * Created by pzzheng on 12/9/16.
 */
public class MessageRecordQueryServiceImpl implements MessageRecordQueryService {

    @Inject
    MessageRecordMapper messageRecordMapper;

    @Override
    public PaginatedList<MessageRecord> findAllOf(User user, int month) {
        return new PaginatedList<>(messageRecordMapper.countOf(user, month), (page, perPage) -> messageRecordMapper.findAllOf(user, month, page, perPage));
    }
}
