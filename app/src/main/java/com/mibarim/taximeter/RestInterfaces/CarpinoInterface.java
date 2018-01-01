package com.mibarim.taximeter.RestInterfaces;

import com.mibarim.taximeter.models.carpino.CarpinoResponse;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by armin on 7/15/17.
 */

public interface CarpinoInterface {
    String URL_PATH_PRICE = "/v1/rides/quotation";

    @GET(CarpinoInterface.URL_PATH_PRICE)
    CarpinoResponse GetPathPriceCarpino(@Query("origin") String origin,
                                        @Query("destination") String destination,
                                        @Query("secondDestination") String secondDestination,
                                        @Query("category") String category,
                                        @Query("rideType") String rideType,
                                        @Query("waitingTime") String waitingTime,
                                        @Header("Authorization") String authorization);
}
