package com.thoughtworks.mobileCharge.domain.test;

import com.thoughtworks.mobileCharge.support.DatabaseTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by pzzheng on 12/8/16.
 */
@RunWith(DatabaseTestRunner.class)
public class MyTestCodecRepoTest {

    @Inject
    MyTestCodecRepo testRepo;

    @Test
    public void should_able_to_save_and_get_a_test_object() {
        String name = "petrina";
        MyCodecTest saveObj = testRepo.save(new MyCodecTest(name));

        assertThat(saveObj.getId(), is(notNullValue()));
        assertThat(saveObj.getName(), is(name));


        Optional<MyCodecTest> fetched = testRepo.findById(saveObj.getId());
        assertThat(fetched.isPresent(), is(true));
        assertThat(fetched.get().getId(), is(saveObj.getId()));
        assertThat(fetched.get().getName(), is(name));
    }

}