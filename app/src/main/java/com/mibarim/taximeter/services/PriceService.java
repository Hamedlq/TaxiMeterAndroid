package com.mibarim.taximeter.services;

import com.mibarim.taximeter.models.ApiResponse;

import retrofit.RestAdapter;

/**
 * Created by Hamed on 3/10/2016.
 */
public class PriceService {

    private RestAdapter restAdapter;

    public PriceService(RestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    public RestAdapter getRestAdapter() {
        return restAdapter;
    }

    private com.mibarim.taximeter.RestInterfaces.GetPriceService getService() {
        return getRestAdapter().create(com.mibarim.taximeter.RestInterfaces.GetPriceService.class);
    }

    public ApiResponse GetPathPrice(String srcLatitude, String srcLongitude, String dstLatitude, String dstLongitude) {
        ApiResponse res = getService().GetPathPrice(
                srcLatitude,
                srcLongitude,
                dstLatitude,
                dstLongitude
        );
        return res;
    }
}
