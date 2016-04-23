package com.mibarim.main.services;

import com.mibarim.main.RestInterfaces.SaveRouteRequestService;
import com.mibarim.main.models.ApiResponse;
import com.mibarim.main.models.ConfirmationModel;
import com.mibarim.main.models.RouteRequest;

import retrofit.RestAdapter;

/**
 * Created by Hamed on 3/10/2016.
 */
public class RouteRequestService {

    private RestAdapter restAdapter;

    public RouteRequestService(RestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    public RestAdapter getRestAdapter() {
        return restAdapter;
    }

    private SaveRouteRequestService getService() {
        return getRestAdapter().create(SaveRouteRequestService.class);
    }

    public ApiResponse SubmitNewRoute(String authToken, RouteRequest routeRequest) {
        ApiResponse res = getService().SubmitNewRoute("Bearer " + authToken,
                routeRequest.SrcGAddress,
                routeRequest.SrcDetailAddress,
                routeRequest.SrcLatitude,
                routeRequest.SrcLongitude,
                routeRequest.DstGAddress,
                routeRequest.DstDetailAddress,
                routeRequest.DstLatitude,
                routeRequest.DstLongitude,
                routeRequest.AccompanyCount,
                routeRequest.TimingOption,
                routeRequest.PricingOption,
                routeRequest.TheDateString(),
                routeRequest.TheTimeString(),
                routeRequest.SatDatetimeString(),
                routeRequest.SunDatetimeString(),
                routeRequest.MonDatetimeString(),
                routeRequest.TueDatetimeString(),
                routeRequest.WedDatetimeString(),
                routeRequest.ThuDatetimeString(),
                routeRequest.FriDatetimeString(),
                routeRequest.CostMinMax,
                routeRequest.IsDrive
        );
        return res;
    }

    public ApiResponse ConfirmRoute(String authToken, ConfirmationModel confirmModel) {
        ApiResponse res = getService().ConfirmRoute("Bearer " + authToken,
                confirmModel.Ids,
                confirmModel.ConfirmedText
        );
        return res;
    }

    public ApiResponse notConfirmRoute(String authToken, ConfirmationModel confirmModel) {
        ApiResponse res = getService().NotConfirmRoute("Bearer " + authToken,
                confirmModel.Ids
        );
        return res;
    }


    public ApiResponse joinGroup(String authToken, String routeId, String groupId) {
        ApiResponse res = getService().JoinGroup("Bearer " + authToken,
                routeId,
                groupId
        );
        return res;
    }

    public ApiResponse deleteGroup(String authToken, String routeId, String groupId) {
        ApiResponse res = getService().DeleteGroup("Bearer " + authToken,
                routeId,
                groupId
        );
        return res;
    }


    public ApiResponse leaveGroup(String authToken, String routeId, String groupId) {
        ApiResponse res = getService().LeaveGroup("Bearer " + authToken,
                routeId,
                groupId
        );
        return res;
    }

    public ApiResponse acceptSuggestion(String authToken, String routeId, String selRouteId) {
        ApiResponse res = getService().AcceptSuggestion("Bearer " + authToken,
                routeId,
                selRouteId
        );
        return res;
    }

    public ApiResponse deleteRouteSuggestion(String authToken, String routeId, String selRouteId) {
        ApiResponse res = getService().deleteRouteSuggestion("Bearer " + authToken,
                routeId,
                selRouteId
        );
        return res;
    }


}
