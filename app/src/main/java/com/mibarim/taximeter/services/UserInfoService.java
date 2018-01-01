package com.mibarim.taximeter.services;

import com.mibarim.taximeter.RestInterfaces.UserInfoInterface;
import com.mibarim.taximeter.models.UserInfoModel;

import javax.inject.Named;

import retrofit.RestAdapter;

/**
 * Created by Arya on 12/27/2017.
 */

public class UserInfoService {

    @Named("userInfo")
    private RestAdapter userAdapter;

    public UserInfoService(RestAdapter userAdapter) {
        this.userAdapter = userAdapter;
    }

    private RestAdapter getUserAdapter() {
        return userAdapter;
    }

    private UserInfoInterface getService() {
        return getUserAdapter().create(UserInfoInterface.class);
    }

    public void sendUserInfo(UserInfoModel userInfo) {
        getService().sendUserInfo(userInfo);
    }
}
