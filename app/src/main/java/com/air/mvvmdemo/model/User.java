package com.air.mvvmdemo.model;

/**
 * Created by Air on 15/9/9.
 */
public class User {
    public final String firstName;
    public final String lastName;
    private boolean isFriend;

    public User(String firstName, String lastName, boolean isFriend) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isFriend = isFriend;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setIsFriend(boolean isFriend) {
        this.isFriend = isFriend;
    }
}
