package com.mibarim.taximeter.RestInterfaces;

import com.mibarim.taximeter.models.tochsi.TouchsiRequest;
import com.mibarim.taximeter.models.tochsi.TochsiResponse;

import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by mohammad hossein on 30/12/2017.
 */

public interface TochsiInterface {
    String URL_TOCHSI ="/v2/touchsiapi/api/passenger/getTripInitialEstimates/v2";

    @POST(TochsiInterface.URL_TOCHSI)
    TochsiResponse tochsiPrice(@Body TouchsiRequest tachcRequest);
}
