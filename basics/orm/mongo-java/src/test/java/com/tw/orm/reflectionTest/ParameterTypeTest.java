package com.tw.orm.reflectionTest;

import com.tw.orm.testObjects.User;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pzzheng on 1/3/17.
 */
public class ParameterTypeTest {

    @Test
    public void should_get_parameterType_of_field() throws NoSuchFieldException {
        //                    if ( propertyValue != null) System.out.println(propertyValue.getClass().getName() + "*********");
//                    System.out.println(pd.getPropertyType().getName() + "#######");
        List<String> test = new ArrayList<String>();
//                    System.out.println(test.getClass().getGenericSuperclass().getTypeName() + "&&&&&&&&&");
        System.out.println(((ParameterizedType)test.getClass().getGenericSuperclass()).getActualTypeArguments()[0] + "&&&&&&&&&^^^^^^^^^^");
//                    System.out.println(test.getClass().getTypeName() + "&&&&&&&&&********");
        System.out.println(test.getClass().getTypeParameters()[0] + "&&&&&&&&&********#####");

        Field users = ParameterTypeClass.class.getDeclaredField("users");
        System.out.println(users.getGenericType());
        System.out.println(((ParameterizedType)users.getGenericType()).getActualTypeArguments()[0]);
    }

    class ParameterTypeClass {
        List<User> users;
    }
}
