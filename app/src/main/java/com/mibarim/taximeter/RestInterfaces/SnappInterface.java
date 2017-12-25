package com.mibarim.taximeter.RestInterfaces;

import com.mibarim.taximeter.models.snapp.SnappAuthResponse;
import com.mibarim.taximeter.models.snapp.SnappRequest;
import com.mibarim.taximeter.models.snapp.SnappResponse;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by armin on 7/14/17.
 */

public interface SnappInterface {
    String URL_PATH_PRICE_SNAPP = "/v2/passenger/price/s/1/99";
    String URL_AUTH = "/v1/auth";

    @POST(SnappInterface.URL_PATH_PRICE_SNAPP)
    SnappResponse GetPathPriceSnapp(@Body SnappRequest snappRequest, @Header("authorization") String authorization);

    @POST(SnappInterface.URL_AUTH)
    @FormUrlEncoded
    SnappAuthResponse authenticateUser(@Field("username") String username,
                                       @Field("password") String password,
                                       @Field("grant_type") String grantType,
                                       @Field("client_id") String clientId,
                                       @Field("client_secret") String clientSecret);
}
