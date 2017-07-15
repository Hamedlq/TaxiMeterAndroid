package com.mibarim.taximeter.RestInterfaces;

import com.mibarim.taximeter.models.carpino.CarpinoAuthResponse;
import com.mibarim.taximeter.models.carpino.CarpinoResponse;

import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.Header;

/**
 * Created by armin on 7/15/17.
 */

public interface CarpinoInterface {
    String URL_PATH_PRICE = "/v1/rides/quotation/";
    String URL_AUTH_TOKEN = "/v1/auth/token";

    @GET(CarpinoInterface.URL_PATH_PRICE)
    CarpinoResponse GetPathPriceCarpino(@Field("origin") String origin,
                                        @Field("destination") String destination,
                                        @Field("secondDestination") String secondDestination,
                                        @Field("category") String category,
                                        @Field("rideType") String rideType,
                                        @Field("waitingTime") String waitingTime,
                                        @Header("Authorization") String authorization);

    @GET(CarpinoInterface.URL_AUTH_TOKEN)
    CarpinoAuthResponse authenticateUser(@Field("platform") String platform,
                                         @Field("role") String role,
                                         @Field("app_version") String app_version,
                                         @Field("authorization") String authorization);

}
