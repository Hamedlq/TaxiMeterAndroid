package com.mibarim.taximeter.RestInterfaces;

import com.mibarim.taximeter.models.ApiResponse;
import com.mibarim.taximeter.models.ServiceOrderResponse;

import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Arya on 12/25/2017.
 */

public interface PriceOrder {
    String URL_GET_PRICE_ORDER = "/GetOrder";

    @POST(PriceOrder.URL_GET_PRICE_ORDER)
    ApiResponse getOrder(@Body ServiceOrderResponse model);
}
