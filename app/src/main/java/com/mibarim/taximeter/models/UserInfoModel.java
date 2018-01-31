package com.mibarim.taximeter.models;

/**
 * Created by Arya on 12/27/2017.
 */

public class UserInfoModel {

    private String identityCode;
    private String phoneNumber;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getidentity() {
        return identityCode;
    }

    public void setidentity(String identity) {
        this.identityCode = identity;
    }
}
