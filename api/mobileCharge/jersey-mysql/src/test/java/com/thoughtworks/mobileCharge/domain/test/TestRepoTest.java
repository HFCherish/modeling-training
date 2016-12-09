package com.thoughtworks.mobileCharge.domain.test;

import com.thoughtworks.mobileCharge.support.DatabaseTestRunner;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by pzzheng on 12/7/16.
 */
@RunWith(DatabaseTestRunner.class)
public class TestRepoTest {

    @Inject
    TestRepo testRepo;

    @Test
    public void should_able_to_save_and_get_a_test_object() {
        String id = new ObjectId().toHexString();
        String name = "petrina";
        MyTest saveObj = testRepo.save(new MyTest(id, name));

        assertThat(saveObj.getId(), is(id));
        assertThat(saveObj.getName(), is(name));


        Optional<MyTest> fetched = testRepo.getById(id);
        assertThat(fetched.isPresent(), is(true));
        assertThat(fetched.get().getId(), is(id));
        assertThat(fetched.get().getName(), is(name));
    }
}