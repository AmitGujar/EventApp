package com.amitgujar.eventapp;

public class UserModel {
    private String fullname, username, phonenumber;

    public UserModel() {
    }

    public UserModel(String fullname, String username, String phonenumber) {
        this.fullname = fullname;
        this.username = username;
        this.phonenumber = phonenumber;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
