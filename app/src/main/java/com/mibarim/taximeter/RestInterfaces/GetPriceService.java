package com.mibarim.taximeter.RestInterfaces;


import com.mibarim.taximeter.core.Constants;
import com.mibarim.taximeter.models.ApiResponse;
import com.mibarim.taximeter.models.PathPrice;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
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
                             @Field("DstLng") String DstLng,
                             @Field("UserId") String userId
    );

    @POST(Constants.Http.URL_TAP30_PATH_PRICE_FROM_SERVER)
    @FormUrlEncoded
    PathPrice GetTap30Price(@Field("SrcLat") String SrcLat,
                            @Field("SrcLng") String SrcLng,
                            @Field("DstLat") String DstLat,
                            @Field("DstLng") String DstLng
    );

    @POST(Constants.Http.URL_SNAPP_PATH_PRICE_FROM_SERVER)
    @FormUrlEncoded
    PathPrice GetSnappPrice(@Field("SrcLat") String SrcLat,
                            @Field("SrcLng") String SrcLng,
                            @Field("DstLat") String DstLat,
                            @Field("DstLng") String DstLng
    );


}
