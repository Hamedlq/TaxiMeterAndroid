package com.mibarim.taximeter.RestInterfaces;

import com.mibarim.taximeter.models.UserInfoModel;

import retrofit.http.Body;
import retrofit.http.POST;


/**
 * Created by Arya on 12/27/2017.
 */

public interface UserInfoInterface {

    String URL_USER_INFO = "/UserInfo";

    @POST(URL_USER_INFO)
    void sendUserInfo(@Body UserInfoModel userInfo);
}
