package com.mibarim.taximeter.RestInterfaces;

import com.mibarim.taximeter.models.maxim.MaximResponse;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Arya on 12/18/2017.
 */

public interface MaximInterface {

    String URL_GET_MAXIM_PRICE = "/Services/Public.svc/TariffsCalculate";

    @GET(MaximInterface.URL_GET_MAXIM_PRICE)
    List<MaximResponse> getMaximPrice(@Query("latitude") String lat,
                       @Query("longitude") String lng,
                       @Query("startLatitude") String startLat,
                       @Query("startLongitude") String srcLng,
                       @Query("endLatitude") String dstLat,
                       @Query("endLongitude") String dstLng,
                       @Query("startQuick") String quickStart,
                       @Query("endQuick") String endQuick,
                       @Query("udid") String udid,
                       @Query("locale") String local,
                       @Query("tariffClass") String tariffClass,
                       @Query("platform") String platform,
                       @Query("version") String version);
}
