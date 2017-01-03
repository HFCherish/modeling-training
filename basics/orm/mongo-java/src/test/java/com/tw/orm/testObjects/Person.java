package com.tw.orm.testObjects;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by pzzheng on 12/30/16.
 */
public class Person {
    Location personLocation;
    String sex;
    List<Person> sons;

    public Person setPersonLocation(Location personLocation) {
        this.personLocation = personLocation;
        return this;
    }

    public Person setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public Person setSons(Person... sons) {
        this.sons = asList(sons);
        return this;
    }

    public List<Person> getSons() {
        return sons;
    }

    public String getSex() {
        return sex;
    }

    public Location getPersonLocation() {
        return personLocation;
    }
}
