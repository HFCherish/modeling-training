package com.thoughtworks.mobileCharge.api.services;

import com.thoughtworks.mobileCharge.domain.PaginatedList;
import com.thoughtworks.mobileCharge.domain.user.CallRecord;
import com.thoughtworks.mobileCharge.domain.user.MessageRecord;
import com.thoughtworks.mobileCharge.domain.user.User;
import com.thoughtworks.mobileCharge.domain.user.UserRepo;
import com.thoughtworks.mobileCharge.support.DatabaseTestRunner;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static com.thoughtworks.mobileCharge.support.TestHelper.getCallRecord;
import static com.thoughtworks.mobileCharge.support.TestHelper.getMessageRecord;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by pzzheng on 12/9/16.
 */
@RunWith(DatabaseTestRunner.class)
public class MessageRecordQueryServiceTest {
    @Inject
    MessageRecordQueryService messageRecordQueryService;
    @Inject
    UserRepo userRepo;
    private User user;

    @Before
    public void setUp() {
        user = userRepo.findAll().get(0);
    }

    @Test
    public void should_get_all_message_records() {
        MessageRecord toSave = getMessageRecord(user, new DateTime());
        user.saveMessage(toSave);
        PaginatedList<MessageRecord> messageRecords = messageRecordQueryService.findAllOf(user, 0);
        assertThat(messageRecords.size(), is(1l));
    }

    @Test
    public void should_get_all_message_records_by_month() {
        user.saveMessage(getMessageRecord(user, new DateTime(2016, 1,1,1,1)));
        user.saveMessage(getMessageRecord(user, new DateTime(2016, 2,1,1,1)));

        assertThat(messageRecordQueryService.findAllOf(user, 0).size(), is(2l));

        assertThat(messageRecordQueryService.findAllOf(user, 1).size(), is(1l));
        assertThat(messageRecordQueryService.findAllOf(user, 2).size(), is(1l));
    }
}