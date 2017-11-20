package com.mibarim.taximeter.RestInterfaces;

import com.mibarim.taximeter.models.carpino.CarpinoAuthResponse;
import com.mibarim.taximeter.models.carpino.CarpinoResponse;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by armin on 7/15/17.
 */

public interface CarpinoInterface {
    String URL_PATH_PRICE = "/v1/rides/quotation/";
//    String URL_AUTH_TOKEN = "/v1/auth/token";

    @GET(CarpinoInterface.URL_PATH_PRICE)
    CarpinoResponse GetPathPriceCarpino(@Query("origin") String origin,
                                        @Query("destination") String destination,
                                        @Query("secondDestination") String secondDestination,
                                        @Query("category") String category,
                                        @Query("rideType") String rideType,
                                        @Query("waitingTime") String waitingTime,
                                        @Header("Authorization") String authorization);

//    @GET(CarpinoInterface.URL_AUTH_TOKEN)
//    CarpinoAuthResponse authenticateUser(@Field("platform") String platform,
//                                         @Field("role") String role,
//                                         @Field("app_version") String app_version,
//                                         @Field("authorization") String authorization);

}
