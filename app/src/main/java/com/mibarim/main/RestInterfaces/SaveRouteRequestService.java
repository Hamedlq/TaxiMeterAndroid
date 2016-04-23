package com.mibarim.main.RestInterfaces;

import com.mibarim.main.core.Constants;
import com.mibarim.main.models.ApiResponse;
import com.mibarim.main.models.RouteRequest;
import com.mibarim.main.models.enums.PricingOptions;
import com.mibarim.main.models.enums.TimingOptions;
import com.squareup.okhttp.Route;

import java.util.Calendar;
import java.util.Date;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.HEAD;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by Hamed on 3/10/2016.
 */
public interface SaveRouteRequestService {
    @POST(Constants.Http.URL_INSERT_ROUTE)
    @FormUrlEncoded
    ApiResponse SubmitNewRoute(@Header(Constants.Http.PARAM_AUTHORIZATION) String authToken,
                               @Field("SrcGAddress") String SrcGAddress,
                               @Field("SrcDetailAddress") String SrcDetailAddress,
                               @Field("SrcLatitude") String SrcLatitude,
                               @Field("SrcLongitude") String SrcLongitude,
                               @Field("DstGAddress") String DstGAddress,
                               @Field("DstDetailAddress") String DstDetailAddress,
                               @Field("DstLatitude") String DstLatitude,
                               @Field("DstLongitude") String DstLongitude,
                               @Field("AccompanyCount") int AccompanyCount,
                               @Field("TimingOption") TimingOptions TimingOption,
                               @Field("PriceOption") PricingOptions PricingOption,
                               @Field("TheDate") String TheDate,
                               @Field("TheTime") String TheTime,
                               @Field("SatDatetime") String SatDatetime,
                               @Field("SunDatetime") String SunDatetime,
                               @Field("MonDatetime") String MonDatetime,
                               @Field("TueDatetime") String TueDatetime,
                               @Field("WedDatetime") String WedDatetime,
                               @Field("ThuDatetime") String ThuDatetime,
                               @Field("FriDatetime") String FriDatetime,
                               @Field("CostMinMax") float CostMinMax,
                               @Field("IsDrive") boolean IsDrive
    );


    @POST(Constants.Http.URL_CONFIRM_ROUTE)
    @FormUrlEncoded
    ApiResponse ConfirmRoute(@Header(Constants.Http.PARAM_AUTHORIZATION) String authToken,
                             @Field("RouteIdsCommaSeprated") String Ids,
                             @Field("ConfirmedText") String ConfirmedText
    );

    @POST(Constants.Http.URL_NOT_CONFIRM_ROUTE)
    @FormUrlEncoded
    ApiResponse NotConfirmRoute(@Header(Constants.Http.PARAM_AUTHORIZATION) String authToken,
                                @Field("RouteIdsCommaSeprated") String Ids
    );

    @POST(Constants.Http.URL_JOIN_GROUP)
    @FormUrlEncoded
    ApiResponse JoinGroup(@Header(Constants.Http.PARAM_AUTHORIZATION) String authToken,
                          @Field("RouteId") String routeId,
                          @Field("GroupId") String groupId
    );

    @POST(Constants.Http.URL_DELETE_GROUP)
    @FormUrlEncoded
    ApiResponse DeleteGroup(@Header(Constants.Http.PARAM_AUTHORIZATION) String authToken,
                            @Field("RouteId") String routeId,
                            @Field("GroupId") String groupId
    );

    @POST(Constants.Http.URL_LEAVE_GROUP)
    @FormUrlEncoded
    ApiResponse LeaveGroup(@Header(Constants.Http.PARAM_AUTHORIZATION) String authToken,
                           @Field("RouteId") String routeId,
                           @Field("GroupId") String groupId
    );

    @POST(Constants.Http.URL_ACCEPT_ROUTE)
    @FormUrlEncoded
    ApiResponse AcceptSuggestion(@Header(Constants.Http.PARAM_AUTHORIZATION) String authToken,
                                 @Field("RouteId") String routeId,
                                 @Field("SelfRouteId") String selRouteId
    );

    @POST(Constants.Http.URL_DELETE_ROUTE_SUGGESTION)
    @FormUrlEncoded
    ApiResponse deleteRouteSuggestion(@Header(Constants.Http.PARAM_AUTHORIZATION) String authToken,
                                      @Field("RouteId") String routeId,
                                      @Field("SelfRouteId") String selRouteId
    );

}
