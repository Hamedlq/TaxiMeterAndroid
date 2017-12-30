package com.mibarim.taximeter.RestInterfaces;

import com.mibarim.taximeter.models.ApiResponse;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Arya on 12/2/2017.
 */

public interface GoogleKeyInterface {

    String GOOGLE_TOKEN_URL = "/GetGoogleApi";

    @POST(GoogleKeyInterface.GOOGLE_TOKEN_URL)
    @FormUrlEncoded
    ApiResponse GetKey(@Field("token") String key);
}
