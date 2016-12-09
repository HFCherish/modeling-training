package com.thoughtworks.mobileCharge.infrastructure.repositories;

import com.thoughtworks.mobileCharge.domain.test.MyCodecTest;
import com.thoughtworks.mobileCharge.domain.test.MyTest;
import com.thoughtworks.mobileCharge.domain.test.MyTestCodecRepo;
import com.thoughtworks.mobileCharge.infrastructure.mappers.MyTestCodecMapper;
import com.thoughtworks.mobileCharge.infrastructure.mappers.MyTestMapper;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by pzzheng on 12/7/16.
 */
public class MyTestCodecCodecRepoImpl implements MyTestCodecRepo {

    @Inject
    MyTestCodecMapper myTestMapper;

    @Override
    public MyCodecTest save(MyCodecTest myTestObj) {
        return myTestMapper.save(myTestObj);
    }

    @Override
    public Optional<MyCodecTest> findById(String id) {
        return myTestMapper.findById(id);
    }
}
