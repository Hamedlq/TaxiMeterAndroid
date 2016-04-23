package com.mibarim.main.RestInterfaces;

import com.mibarim.main.core.Constants;
import com.mibarim.main.models.ApiResponse;
import com.mibarim.main.models.PersonalInfoModel;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by Hamed on 3/10/2016.
 */
public interface GetRouteResponseService {
    @POST(Constants.Http.URL_GET_ROUTE)
    @FormUrlEncoded
    ApiResponse GetUserRoutes(@Header(Constants.Http.PARAM_AUTHORIZATION) String authToken,
                                      @Field("UserId") String Id);

    @POST(Constants.Http.ROUTE_IMAGE_URL)
    @FormUrlEncoded
    PersonalInfoModel GetRouteUserImage(@Header(Constants.Http.PARAM_AUTHORIZATION) String authToken,
                              @Field("RouteRequestId") long Id);

    @POST(Constants.Http.SUGGEST_ROUTE_URL)
    @FormUrlEncoded
    ApiResponse GetRouteSuggest(@Header(Constants.Http.PARAM_AUTHORIZATION) String authToken,
                                        @Field("RouteRequestId") long Id);

}
