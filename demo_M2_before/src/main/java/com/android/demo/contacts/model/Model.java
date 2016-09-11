package com.android.demo.contacts.model;

/**
 * Created by omrierez on 8/24/16.
 */
public class Model {

    private boolean flag;
    private String name;
    private int age;
    private String family;

    public Model(boolean flag, String name, int age, String family) {
        this.flag = flag;
        this.name = name;
        this.age = age;
        this.family = family;
    }

    public boolean isFlag() {
        return flag;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getFamily() {
        return family;
    }
}
