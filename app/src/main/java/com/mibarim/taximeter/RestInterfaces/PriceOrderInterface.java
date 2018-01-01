package com.mibarim.taximeter.RestInterfaces;

import com.mibarim.taximeter.models.ApiResponse;
import com.mibarim.taximeter.models.ServiceOrderResponse;

import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Arya on 12/25/2017.
 */

public interface PriceOrderInterface {
    String URL_GET_PRICE_ORDER = "/GetOrder";

    @POST(PriceOrderInterface.URL_GET_PRICE_ORDER)
    ApiResponse getOrder(@Body ServiceOrderResponse model);
}
