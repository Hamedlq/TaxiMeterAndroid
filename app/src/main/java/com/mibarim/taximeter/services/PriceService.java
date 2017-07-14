package com.mibarim.taximeter.services;

import android.content.SharedPreferences;
import android.util.Log;

import com.mibarim.taximeter.models.ApiResponse;
import com.mibarim.taximeter.models.snapp.SnappAuthResponse;
import com.mibarim.taximeter.models.snapp.SnappResponse;
import com.mibarim.taximeter.models.snapp.SnappRequest;

import javax.inject.Named;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by Hamed on 3/10/2016.
 */
public class PriceService {

    private RestAdapter restAdapter;

    @Named("snapp")
    private RestAdapter snappRestAdapter;

    @Named("authSnapp")
    private RestAdapter snappAuthRestAdapter;


    public PriceService(RestAdapter restAdapter,RestAdapter snappRestAdapter,RestAdapter snappAuthRestAdapter) {
        this.restAdapter = restAdapter;
        this.snappRestAdapter = snappRestAdapter;
        this.snappAuthRestAdapter = snappAuthRestAdapter;
    }

    public RestAdapter getRestAdapter() {
        return restAdapter;
    }

    public RestAdapter getSnappRestAdapter(){
        return snappRestAdapter;
    }

    public RestAdapter getSnappAuthRestAdapter(){return snappAuthRestAdapter;}

    private com.mibarim.taximeter.RestInterfaces.GetPriceService getService() {
        return getRestAdapter().create(com.mibarim.taximeter.RestInterfaces.GetPriceService.class);
    }

    private com.mibarim.taximeter.RestInterfaces.SnappInterface getSnappService() {
        return getSnappRestAdapter().create(com.mibarim.taximeter.RestInterfaces.SnappInterface.class);
    }

    private com.mibarim.taximeter.RestInterfaces.SnappInterface getSnappAuthService() {
        return getSnappAuthRestAdapter().create(com.mibarim.taximeter.RestInterfaces.SnappInterface.class);
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

//    private String authorization = "Bearer 89Z5DFMFJB7gYX3Njvr7mMT9MVgwU8UfN5Iv89wrs";

    public SnappResponse getPathPriceSnapp(String srcLatitude, String srcLongitude, String dstLatitude, String dstLongitude,String authorization)
    {

        SnappRequest snappRequest = new SnappRequest(srcLatitude,srcLongitude,dstLatitude,dstLongitude,1,0,0,false,false,"2");
//        SnappResponse snappApiResponse = getSnappService().GetPathPriceSnapp(new SnappRequest(srcLatitude,srcLongitude,dstLatitude,dstLongitude,1,0,0,false,false,"2").toString());


//        try {
            SnappResponse snappApiResponse = getSnappService().GetPathPriceSnapp(snappRequest, authorization);
            return snappApiResponse;
//        }catch (Exception e)
//        {
//            SnappAuthResponse snappAuthResponse = getSnappAuthService().authenticateUser("armin.zirak97@gmail.com","12345678","password",
//                    "android_293ladfa12938176yfgsndf",
//                    "as;dfh98129-9111.*(U)jsflsdf");
//            authorization = snappAuthResponse.getTokenType() + " " + snappAuthResponse.getAccessToken();
//            return getPathPriceSnapp(srcLatitude,srcLongitude,dstLatitude,dstLongitude);
////            Log.d("wait","wait");
////            if (e instanceof RetrofitError)
//
//        }
//        if (snappApiResponse.getStatus().equals("401"))
//        {

//
//        }
//        SnappResponse snappApiResponse = getSnappService().GetPathPriceSnapp(new SnappRequest());
//        SnappResponse snappApiResponse = getSnappService().GetPathPriceSnapp(authorization,
//                srcLatitude,srcLongitude,
//                dstLatitude,dstLongitude,1,0,0,false,false,"2");
//        HashMap hashMap = new HashMap();
//        hashMap.put("origin_lat",srcLatitude);
//        hashMap.put("origin_lng",srcLongitude);
//        hashMap.put("destination_lat",dstLatitude);
//        hashMap.put("destination_lng",dstLongitude);
//        hashMap.put("service_type" , 1);
//        hashMap.put("sub_service_type", 0);
//        hashMap.put("destination_place_id",0);
//        hashMap.put("round_trip", false);
//        hashMap.put("services", false);
//        hashMap.put("tag",2);
//        SnappResponse snappApiResponse = getSnappService().GetPathPriceSnapp(authorization,hashMap);

    }
    public String getAuthorizationKey()
    {
        String authorization;
        SnappAuthResponse snappAuthResponse = getSnappAuthService().authenticateUser("armin.zirak97@gmail.com","12345678","password",
                "android_293ladfa12938176yfgsndf",
                "as;dfh98129-9111.*(U)jsflsdf");
        authorization = snappAuthResponse.getTokenType() + " " + snappAuthResponse.getAccessToken();
        return authorization;

    }
}
