package com.nonpool.pojo;

import java.util.Locale;

public class GetterClass {
    private String firstName;
    private String lastName;

    public GetterClass(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getName() {
        return getLastName() + "·" + getFirstName();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName.toUpperCase(Locale.ROOT);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
