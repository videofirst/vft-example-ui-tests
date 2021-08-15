package io.videofirst.uitests.bddexp.gen3.person;

import io.videofirst.vfa.annotation.person.Builder;

/**
 * @author Bob Marks
 */
//@Builder
public class Person {

    private int age;

    private String name;
    private String address;

    //@BuilderProperty
    public void setAge(int age) {
        this.age = age;
    }

    //@BuilderProperty
    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String name) {
        this.name = name;
    }

    // getters

}

