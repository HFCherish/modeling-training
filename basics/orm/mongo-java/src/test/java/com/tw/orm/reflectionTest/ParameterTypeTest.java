package com.tw.orm.reflectionTest;

import com.tw.orm.testObjects.User;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by pzzheng on 1/3/17.
 */
public class ParameterTypeTest {

    @Test
    public void should_get_parameterType_of_field() throws NoSuchFieldException, ClassNotFoundException {
        List<String> test = new ArrayList<String>();
        System.out.println(((ParameterizedType)test.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        System.out.println(test.getClass().getTypeParameters()[0]);

        System.out.println("------------------------------");
        Field users = ParameterTypeClass.class.getDeclaredField("users");
        System.out.println(users.getGenericType());
        assertThat(users.getGenericType() instanceof ParameterizedType, is(true));
        System.out.println("users.getGenericType().getClass(): " + users.getGenericType().getClass());
        Type type = ((ParameterizedType) users.getGenericType()).getActualTypeArguments()[0];
        assertThat(((Class)((ParameterizedType) users.getGenericType()).getActualTypeArguments()[0]).equals(User.class), is(true));
        assertThat(users.getGenericType().getTypeName(), containsString("List"));
        assertThat(users.getGenericType().getTypeName(), containsString("User"));
        String parameterTypeName = type.getTypeName();
        System.out.println(parameterTypeName);
        System.out.println(Class.forName(parameterTypeName));
        System.out.println(((Class)type).getName());
        System.out.println("(Class)type: " + (Class)type);
        System.out.println("------------------------------");
        Field generic = ParameterTypeClass.class.getDeclaredField("generic");
        System.out.println(generic.getGenericType());
//        System.out.println(((ParameterizedType)generic.getGenericType()).getActualTypeArguments()[0]);


//        System.out.println(((ParameterizedType)generic.getGenericType()).getActualTypeArguments()[0]);
    }

    @Test
    public void testArray() throws NoSuchFieldException {
        System.out.println("------------------------------");
        Field userArray = ParameterTypeClass.class.getDeclaredField("userArray");
        assertThat(userArray.getType().isArray(), is(true));
        assertThat(userArray.getType().getComponentType().equals(User.class), is(true));
        Object testArray = new User[1];
        ((Object[]) testArray)[0] = new User();
        ArrayList c = new ArrayList();
        Collections.addAll(c, testArray);
        assertThat(c.size(), is(1));
        assertThat(testArray.getClass().isArray(), is(true));
        assertThat(testArray.getClass().getComponentType().equals(User.class), is(true));
        Arrays.stream((Object[])testArray).forEach(o -> System.out.println(o.getClass().getName()));

        Object testList = Arrays.asList(new User());
        Stream.of(testList).forEach(o -> System.out.println(o.getClass().getName()));
    }

    class ParameterTypeClass {
        List<User> users;
        List generic;
        User[] userArray;
    }
}
