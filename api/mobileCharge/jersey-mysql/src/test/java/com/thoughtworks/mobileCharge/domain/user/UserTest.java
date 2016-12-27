package com.thoughtworks.mobileCharge.domain.user;

import com.thoughtworks.mobileCharge.domain.PaginatedList;
import com.thoughtworks.mobileCharge.support.DatabaseTestRunner;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static com.thoughtworks.mobileCharge.support.TestHelper.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by pzzheng on 12/9/16.
 */
@RunWith(DatabaseTestRunner.class)
public class UserTest {

    @Inject
    UserRepo userRepo;
    private User user;

    @Before
    public void setUp() {
        user = userRepo.findAll().get(0);
    }

    @Test
    public void should_save_and_get_that_call_record() {
        DateTime start = new DateTime();
        CallRecord toSave = getCallRecord(user, start);

        CallRecord saved = user.saveCallRecord(toSave);
        assertThat(saved.getId(), is(toSave.getId()));
        assertThat(saved.callType, is(toSave.callType));
        assertThat(saved.duration, is(toSave.duration));
        assertThat(saved.communicationType, is(toSave.communicationType));
        assertThat(saved.start, is(start));
        assertThat(saved.targetCard.phoneNumber, is(toSave.targetCard.phoneNumber));
        assertThat(saved.ownerId, is(user.getId()));
    }

    @Test
    public void should_save_and_get_that_message_record() {
        MessageRecord toSave = getMessageRecord(user, new DateTime());

        MessageRecord saved = user.saveMessage(toSave);
        assertThat(saved.getId(), is(toSave.getId()));
        assertThat(saved.type, is(toSave.type));
        assertThat(saved.createdAt, is(toSave.createdAt));
        assertThat(saved.communicationType, is(toSave.communicationType));
        assertThat(saved.sendType, is(toSave.sendType));
        assertThat(saved.targetCard.phoneNumber, is(toSave.targetCard.phoneNumber));
        assertThat(saved.ownerId, is(user.getId()));
    }

    @Test
    public void should_save_and_get_that_data_access_record() {
        DataAccessRecord toSave = getDataAccessRecord(user, new DateTime());

        DataAccessRecord saved = user.saveDataAccess(toSave);
        assertThat(saved.getId(), is(toSave.getId()));
        assertThat(saved.chargeType, is(toSave.chargeType));
        assertThat(saved.webType, is(toSave.webType));
        assertThat(saved.createdAt, is(toSave.createdAt));
        assertThat(saved.communicationType, is(toSave.communicationType));
        assertThat(saved.data, is(toSave.data));
        assertThat(saved.targetConsumer, is(toSave.targetConsumer));
        assertThat(saved.fromLocale, is(toSave.fromLocale));
        assertThat(saved.ownerId, is(user.getId()));
    }

    @Test
    public void should_get_user_balance() {
        Balance balance = user.getBalance();
        assertThat(balance.remainMoney > 0, is(true));
        assertThat(balance.accounts.isEmpty(), is(false));
    }

    @Test
    public void should_get_all_call_records() {
        CallRecord toSave = getCallRecord(user, new DateTime());
        user.saveCallRecord(toSave);
        PaginatedList<CallRecord> allCallRecords = user.findAllCallRecords(0);
        assertThat(allCallRecords.size(), is(1l));
    }

    @Test
    public void should_get_all_call_records_by_month() {
        user.saveCallRecord(getCallRecord(user, new DateTime(2016, 1,1,1,1)));
        user.saveCallRecord(getCallRecord(user, new DateTime(2016, 2,1,1,1)));

        assertThat(user.findAllCallRecords(0).size(), is(2l));
        assertThat(user.findAllCallRecords(1).size(), is(1l));
        assertThat(user.findAllCallRecords(2).size(), is(1l));
    }

    @Test
    public void should_get_all_data_access_records() {
        DataAccessRecord toSave = getDataAccessRecord(user, new DateTime());
        user.saveDataAccess(toSave);
        PaginatedList<DataAccessRecord> dataAccessRecords = user.findAllDataAccess(0);
        assertThat(dataAccessRecords.size(), is(1l));
    }

    @Test
    public void should_get_all_data_access_records_by_month() {
        user.saveDataAccess(getDataAccessRecord(user, new DateTime(2016, 1, 1, 1, 1)));
        user.saveDataAccess(getDataAccessRecord(user, new DateTime(2016, 2, 1, 1, 1)));

        assertThat(user.findAllDataAccess(0).size(), is(2l));
        assertThat(user.findAllDataAccess(1).size(), is(1l));
        assertThat(user.findAllDataAccess(2).size(), is(1l));
    }

    @Test
    public void should_get_all_message_records() {
        MessageRecord toSave = getMessageRecord(user, new DateTime());
        user.saveMessage(toSave);
        PaginatedList<MessageRecord> messageRecords = user.findAllMessages(0);
        assertThat(messageRecords.size(), is(1l));
    }

    @Test
    public void should_get_all_message_records_by_month() {
        user.saveMessage(getMessageRecord(user, new DateTime(2016, 1,1,1,1)));
        user.saveMessage(getMessageRecord(user, new DateTime(2016, 2,1,1,1)));

        assertThat(user.findAllMessages(0).size(), is(2l));
        assertThat(user.findAllMessages(1).size(), is(1l));
        assertThat(user.findAllMessages(2).size(), is(1l));
    }
}