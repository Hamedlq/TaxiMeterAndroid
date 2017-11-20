package com.mibarim.taximeter.RestInterfaces;

import com.mibarim.taximeter.models.ApiResponse;
import com.mibarim.taximeter.models.tmTokensModel;

import retrofit.http.Body;
import retrofit.http.POST;


public interface AuthtenticationsInerface {
    String URL_GET_TOKEN = "/testapp/GetTokens";

    @POST(AuthtenticationsInerface.URL_GET_TOKEN)
    ApiResponse getToken(@Body tmTokensModel model);
}
