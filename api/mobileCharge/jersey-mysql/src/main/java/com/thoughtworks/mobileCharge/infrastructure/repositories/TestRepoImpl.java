package com.thoughtworks.mobileCharge.infrastructure.repositories;

import com.thoughtworks.mobileCharge.domain.test.MyTest;
import com.thoughtworks.mobileCharge.domain.test.TestRepo;
import com.thoughtworks.mobileCharge.infrastructure.mappers.MyTestMapper;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by pzzheng on 12/7/16.
 */
public class TestRepoImpl implements TestRepo{

    @Inject
    MyTestMapper myTestMapper;

    @Override
    public MyTest save(MyTest myTestObj) {
        return myTestMapper.save(myTestObj);
    }

    @Override
    public Optional<MyTest> getById(String id) {
        return myTestMapper.findById(id);
    }
}
