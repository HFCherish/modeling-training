package com.thoughtworks.mobileCharge.domain.user;

import com.thoughtworks.mobileCharge.api.services.CallRecordQueryService;
import com.thoughtworks.mobileCharge.domain.PaginatedList;
import com.thoughtworks.mobileCharge.support.DatabaseTestRunner;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.Locale;

import static com.thoughtworks.mobileCharge.support.TestHelper.beijingLocale;
import static com.thoughtworks.mobileCharge.support.TestHelper.getCallRecord;
import static com.thoughtworks.mobileCharge.support.TestHelper.getPhoneCard;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

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
        MessageRecord toSave = new MessageRecord(user,
                beijingLocale(),
                getPhoneCard(beijingLocale()),
                MessageRecord.Type.MMS,
                MessageRecord.SendType.SENDER,
                new DateTime().getMillis());

        MessageRecord saved = user.saveMessage(toSave);
        assertThat(saved.getId(), is(toSave.getId()));
        assertThat(saved.type, is(toSave.type));
        assertThat(saved.createdAt, is(toSave.createdAt));
        assertThat(saved.communicationType, is(toSave.communicationType));
        assertThat(saved.sendType, is(toSave.sendType));
        assertThat(saved.targetCard.phoneNumber, is(toSave.targetCard.phoneNumber));
//        assertThat(saved.ownerId, is(user.getId()));
    }
}