package com.mibarim.taximeter.services;

import android.util.Base64;

import com.mibarim.taximeter.models.ApiResponse;
import com.mibarim.taximeter.models.carpino.CarpinoAuthResponse;
import com.mibarim.taximeter.models.carpino.CarpinoResponse;
import com.mibarim.taximeter.models.snapp.SnappAuthResponse;
import com.mibarim.taximeter.models.snapp.SnappResponse;
import com.mibarim.taximeter.models.snapp.SnappRequest;
import com.mibarim.taximeter.models.tap30.Tap30Request;
import com.mibarim.taximeter.models.tap30.Tap30Response;

import javax.inject.Named;

import retrofit.RestAdapter;

/**
 * Created by Hamed on 3/10/2016.
 */
public class PriceService {

    private RestAdapter restAdapter;

    @Named("snapp")
    private RestAdapter snappRestAdapter;

    @Named("authSnapp")
    private RestAdapter snappAuthRestAdapter;

    @Named("authTap30")
    private RestAdapter tap30RestAdapter;

    @Named("carpino")
    private RestAdapter carpinoRestAdapter;


    public PriceService(RestAdapter restAdapter,RestAdapter snappRestAdapter,RestAdapter snappAuthRestAdapter,RestAdapter tap30RestAdapter,RestAdapter carpinoRestAdapter) {
        this.restAdapter = restAdapter;
        this.snappRestAdapter = snappRestAdapter;
        this.snappAuthRestAdapter = snappAuthRestAdapter;
        this.tap30RestAdapter = tap30RestAdapter;
        this.carpinoRestAdapter = carpinoRestAdapter;
    }

    public RestAdapter getRestAdapter() {
        return restAdapter;
    }

    public RestAdapter getSnappRestAdapter(){
        return snappRestAdapter;
    }

    public RestAdapter getSnappAuthRestAdapter(){return snappAuthRestAdapter;}

    public RestAdapter getTap30RestAdapter(){return tap30RestAdapter;}

    public RestAdapter getCarpinoRestAapter(){return carpinoRestAdapter;}

    private com.mibarim.taximeter.RestInterfaces.GetPriceService getService() {
        return getRestAdapter().create(com.mibarim.taximeter.RestInterfaces.GetPriceService.class);
    }

    private com.mibarim.taximeter.RestInterfaces.SnappInterface getSnappService() {
        return getSnappRestAdapter().create(com.mibarim.taximeter.RestInterfaces.SnappInterface.class);
    }

    private com.mibarim.taximeter.RestInterfaces.SnappInterface getSnappAuthService() {
        return getSnappAuthRestAdapter().create(com.mibarim.taximeter.RestInterfaces.SnappInterface.class);
    }

    private com.mibarim.taximeter.RestInterfaces.Tap30Interface getTap30Service() {
        return getTap30RestAdapter().create(com.mibarim.taximeter.RestInterfaces.Tap30Interface.class);
    }

    private com.mibarim.taximeter.RestInterfaces.CarpinoInterface getCarpinoService() {
        return getCarpinoRestAapter().create(com.mibarim.taximeter.RestInterfaces.CarpinoInterface.class);
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
    public Tap30Response getPathPriceTap30(String srcLatitude, String srcLongitude, String dstLatitude, String dstLongitude,String authorization)
    {

        Tap30Request tap30Request = new Tap30Request(srcLatitude,srcLongitude,dstLatitude,dstLongitude,1,0,0,false,false,"2");
        Tap30Response tap30Response = getTap30Service().GetPathPriceTap30(tap30Request, authorization);
        return tap30Response;

    }
    public CarpinoResponse getPathPriceCarpino(String srcLatitude, String srcLongitude, String dstLatitude, String dstLongitude,String authorization)
    {
        String origin = srcLatitude + "," + srcLongitude;
        String destination = dstLatitude +"," + dstLongitude;
        CarpinoResponse carpinoResponse = getCarpinoService().GetPathPriceCarpino(origin, destination ,"0,0", "NORMAL", "SINGLE", "0",authorization);
        return carpinoResponse;
    }
    public String getSnappAuthorizationKey()
    {
        String authorization;
        SnappAuthResponse snappAuthResponse = getSnappAuthService().authenticateUser("armin.zirak97@gmail.com","12345678","password",
                "android_293ladfa12938176yfgsndf",
                "as;dfh98129-9111.*(U)jsflsdf");
        authorization = snappAuthResponse.getTokenType() + " " + snappAuthResponse.getAccessToken();
        return authorization;

    }
    public String getTap30AuthorizationKey()
    {
        String authorization = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ1c2VyIjp7ImlkIjo4NjQ2OCwicHJvZmlsZUlkIjo4NjQ2OCwidXNlclBhc3NDcmVkZW50aWFsSWQiOjgyMjk1LCJpc0Nob3NlbkZvckluY2VudGl2ZSI6bnVsbCwiZGV2aWNlVG9rZW4iOiJOb0RldmljZVRva2VuWWV0IiwiZGV2aWNlVHlwZSI6IkFORFJPSUQiLCJyZWZlcnJhbENvZGUiOiIyT0FJQjciLCJyZWZlcnJlcklkIjpudWxsLCJyb2xlIjoiUEFTU0VOR0VSIiwiY3JlYXRlZEF0IjoiMjAxNi0wOC0xNFQxMzozNjoxMS44NTJaIiwidXBkYXRlZEF0IjoiMjAxNi0xMi0xNlQyMDo1MToxMy44MzdaIiwicHVzaHlEZXZpY2VUb2tlbiI6bnVsbCwidGVsZWdyYW1JZCI6bnVsbH0sImlhdCI6MTQ4MTkyMTQ5MCwiYXVkIjoiZG9yb3Noa2U6YXBwIiwiaXNzIjoiZG9yb3Noa2U6c2VydmVyIiwic3ViIjoiZG9yb3Noa2U6dG9rZW4ifQ.raEUrMSwJoRHUuCvy0oBHCapd8EebpzRNBqgFVSZXwiUueV5QfvQE-drhqIyFykwazZKKd5-KIfj9dmjeS3zAw";
        return authorization;
    }
    public String getCarpinoAuthorizationKey()
    {
//        String basicAuthorization = "Basic " + Base64.encode(("armin.zirak97@gmail.com:az4484"));
        String basicAuthorization = "Basic " + "YXJtaW4uemlyYWs5N0BnbWFpbC5jb206YXo0NDg0";
        CarpinoAuthResponse carpinoAuthResponse = getCarpinoService().authenticateUser("ANDROID","PASSENGER","app_version",basicAuthorization);
        return carpinoAuthResponse.getAuthToken();
    }
}
