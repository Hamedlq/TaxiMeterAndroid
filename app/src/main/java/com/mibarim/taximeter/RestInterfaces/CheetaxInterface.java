package com.mibarim.taximeter.RestInterfaces;

import com.mibarim.taximeter.models.cheetax.CheetaxRequest;
import com.mibarim.taximeter.models.cheetax.CheetaxResponse;

import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by Arya on 1/3/2018.
 */

public interface CheetaxInterface {

    String URL_PATH_PRICE_CHEETAX = "/cheetApi/api/Trip/calcTripInvoice";

    @POST(URL_PATH_PRICE_CHEETAX)
    CheetaxResponse getCheetaxPrice(@Header("Authorization") String authorization, @Body CheetaxRequest cheetaxRequest);
}
