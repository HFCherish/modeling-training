package com.thoughtworks.mobileCharge.domain.test;

import java.util.Optional;

/**
 * Created by pzzheng on 12/7/16.
 */
public interface MyTestCodecRepo {
    MyCodecTest save(MyCodecTest myTestObj);

    Optional<MyCodecTest> findById(String id);
}
