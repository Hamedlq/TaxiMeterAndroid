package com.mibarim.taximeter.RestInterfaces;

import com.mibarim.taximeter.models.alopeyk.AlopeykRequest;
import com.mibarim.taximeter.models.alopeyk.AlopeykResponse;

import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by Arya on 12/11/2017.
 */

public interface AlopeykInterface {

    String URL_PATH_PRICE_ALOPEYK = "/api/v2/orders/price/calc";

    @POST(URL_PATH_PRICE_ALOPEYK)
    AlopeykResponse GetAlopeykService(@Body AlopeykRequest request , @Header("authorization") String authorization);
}
