package com.mibarim.taximeter.RestInterfaces;

import com.mibarim.taximeter.models.tap30.Tap30Request;
import com.mibarim.taximeter.models.tap30.Tap30Response;

import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by armin on 7/14/17.
 */

public interface Tap30Interface {
    String URL_PATH_PRICE_TAP30 = "/api/v1/map/info/destination";

    @POST(Tap30Interface.URL_PATH_PRICE_TAP30)
    Tap30Response GetPathPriceTap30(@Body Tap30Request tap30Request, @Header("X-Authorization") String authorization);
}
