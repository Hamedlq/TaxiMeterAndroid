package com.mibarim.taximeter.RestInterfaces;

import com.mibarim.taximeter.models.ApiResponse;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Arya on 12/2/2017.
 */

public interface GoogleKey {

    String GOOGLE_TOKEN_URL = "/GetGoogleApi";

    String GOOGLE_ADDRESS = "/GetGoogle";

    @POST(GoogleKey.GOOGLE_TOKEN_URL)
    @FormUrlEncoded
    ApiResponse GetKey(@Field("token") String key);
}
