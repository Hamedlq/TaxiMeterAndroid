package com.mibarim.taximeter.RestInterfaces;

import android.support.annotation.Nullable;

import com.mibarim.taximeter.core.Constants.Http;
import com.mibarim.taximeter.models.ApiResponse;
import com.mibarim.taximeter.services.tmTokensModel;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.POST;
import retrofit.http.Query;


public interface AuthtenticationsInerface {
    String URL_GET_TOKEN = "/testapp/GetTokens";

    @POST(AuthtenticationsInerface.URL_GET_TOKEN)
    ApiResponse getToken(@Body tmTokensModel model);
}
