package com.mibarim.taximeter.RestInterfaces;

import com.mibarim.taximeter.models.qonqa.QonqaRequest;
import com.mibarim.taximeter.models.qonqa.QonqaResponse;

import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by Arya on 1/1/2018.
 */

public interface QonqaInterface {

    String URL_PATH_PRICE_QONQA = "/webservice/rest_srv_2.php";

    @POST(URL_PATH_PRICE_QONQA)
    QonqaResponse getPathPriceQonqa(@Header("ACTION") String action,
                                    @Header("SESSION") String authorization,
                                    @Body QonqaRequest request);
}
