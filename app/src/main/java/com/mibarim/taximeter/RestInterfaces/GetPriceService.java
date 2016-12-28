package com.mibarim.taximeter.RestInterfaces;


import com.mibarim.taximeter.core.Constants;
import com.mibarim.taximeter.models.ApiResponse;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by Hamed on 3/10/2016.
 */
public interface GetPriceService {
    @POST(Constants.Http.URL_PATH_PRICE)
    @FormUrlEncoded
    ApiResponse GetPathPrice(@Field("SrcLat") String SrcLat,
                             @Field("SrcLng") String SrcLng,
                             @Field("DstLat") String DstLat,
                             @Field("DstLng") String DstLng
    );

}
