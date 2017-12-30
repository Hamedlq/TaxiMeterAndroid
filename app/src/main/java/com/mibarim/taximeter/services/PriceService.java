package com.mibarim.taximeter.services;

import com.mibarim.taximeter.RestInterfaces.TochsiInterface;
import com.mibarim.taximeter.models.ApiResponse;
import com.mibarim.taximeter.models.PathPrice;
import com.mibarim.taximeter.models.UserInfoModel;
import com.mibarim.taximeter.models.alopeyk.AlopeykRequest;
import com.mibarim.taximeter.models.alopeyk.AlopeykResponse;
import com.mibarim.taximeter.models.carpino.CarpinoResponse;
import com.mibarim.taximeter.models.maxim.MaximRequest;
import com.mibarim.taximeter.models.maxim.MaximResponse;
import com.mibarim.taximeter.models.snapp.SnappResponse;
import com.mibarim.taximeter.models.snapp.SnappRequest;
import com.mibarim.taximeter.models.tap30.Tap30Request;
import com.mibarim.taximeter.models.tap30.Tap30Response;
import com.mibarim.taximeter.models.tmTokensModel;
import com.mibarim.taximeter.models.tochsi.TochsiResponse;
import com.mibarim.taximeter.models.tochsi.TouchsiRequest;

import java.util.List;

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
    private RestAdapter tap30AuthRestAdapter;

    @Named("authTap30")
    private RestAdapter tap30RestAdapter;

    @Named("carpino")
    private RestAdapter carpinoRestAdapter;

    @Named("alopeyk")
    private RestAdapter alopeykRestAdapter;

    @Named("maxim")
    private RestAdapter maximRestAdapter;

    @Named("tochsi")
    private RestAdapter tochsiRestAdapter;


    public PriceService(RestAdapter restAdapter, RestAdapter snappRestAdapter, RestAdapter snappAuthRestAdapter, RestAdapter tap30AuthRestAdapter, RestAdapter tap30RestAdapter, RestAdapter carpinoRestAdapter, RestAdapter alopeykRestAdapter, RestAdapter maximRestAdapter,RestAdapter tochsiRestAdapter) {
        this.restAdapter = restAdapter;
        this.snappRestAdapter = snappRestAdapter;
        this.snappAuthRestAdapter = snappAuthRestAdapter;
        this.tap30AuthRestAdapter = tap30AuthRestAdapter;
        this.tap30RestAdapter = tap30RestAdapter;
        this.carpinoRestAdapter = carpinoRestAdapter;
        this.alopeykRestAdapter = alopeykRestAdapter;
        this.maximRestAdapter = maximRestAdapter;
        this.tochsiRestAdapter = tochsiRestAdapter;
    }

    public RestAdapter getRestAdapter() {
        return restAdapter;
    }

    public RestAdapter getSnappRestAdapter() {
        return snappRestAdapter;
    }

    public RestAdapter getSnappAuthRestAdapter() {
        return snappAuthRestAdapter;
    }

    public RestAdapter getTap30AuthRestAdapter() {
        return tap30AuthRestAdapter;
    }

    public RestAdapter getTap30RestAdapter() {
        return tap30RestAdapter;
    }

    public RestAdapter getCarpinoRestAapter() {
        return carpinoRestAdapter;
    }

    public RestAdapter getAlopeykRestAdapter(){
        return alopeykRestAdapter;
    }

    public RestAdapter getMaximRestAdapter(){
        return maximRestAdapter;
    }

    public RestAdapter getTochsiRestAdapter() {
        return tochsiRestAdapter;
    }

    private com.mibarim.taximeter.RestInterfaces.GetPriceService getService() {
        return getRestAdapter().create(com.mibarim.taximeter.RestInterfaces.GetPriceService.class);
    }

    private com.mibarim.taximeter.RestInterfaces.SnappInterface getSnappService() {
        return getSnappRestAdapter().create(com.mibarim.taximeter.RestInterfaces.SnappInterface.class);
    }

    private com.mibarim.taximeter.RestInterfaces.SnappInterface getSnappAuthService() {
        return getSnappAuthRestAdapter().create(com.mibarim.taximeter.RestInterfaces.SnappInterface.class);
    }

    private com.mibarim.taximeter.RestInterfaces.Tap30Interface getTap30AuthService() {
        return getTap30AuthRestAdapter().create(com.mibarim.taximeter.RestInterfaces.Tap30Interface.class);
    }

    private com.mibarim.taximeter.RestInterfaces.Tap30Interface getTap30Service() {
        return getTap30RestAdapter().create(com.mibarim.taximeter.RestInterfaces.Tap30Interface.class);
    }

    private com.mibarim.taximeter.RestInterfaces.CarpinoInterface getCarpinoService() {
        return getCarpinoRestAapter().create(com.mibarim.taximeter.RestInterfaces.CarpinoInterface.class);
    }

    private com.mibarim.taximeter.RestInterfaces.AlopeykInterface getAlopeykService() {
        return getAlopeykRestAdapter().create(com.mibarim.taximeter.RestInterfaces.AlopeykInterface.class);
    }

    private com.mibarim.taximeter.RestInterfaces.MaximInterface getMaximkService() {
        return getMaximRestAdapter().create(com.mibarim.taximeter.RestInterfaces.MaximInterface.class);
    }
    private com.mibarim.taximeter.RestInterfaces.TochsiInterface getTochsiService(){
        return getTochsiRestAdapter().create(com.mibarim.taximeter.RestInterfaces.TochsiInterface.class);
    }

    public ApiResponse GetPathPrice(String srcLatitude, String srcLongitude, String dstLatitude, String dstLongitude, String userId) {
        ApiResponse res = getService().GetPathPrice(
                srcLatitude,
                srcLongitude,
                dstLatitude,
                dstLongitude,
                userId
        );
        return res;
    }


    public PathPrice GetTap30PriceFromServer(String srcLatitude, String srcLongitude, String dstLatitude, String dstLongitude) {
        PathPrice res = getService().GetTap30Price(
                srcLatitude,
                srcLongitude,
                dstLatitude,
                dstLongitude
        );
        return res;
    }

    public PathPrice GetSnappPriceFromServer(String srcLatitude, String srcLongitude, String dstLatitude, String dstLongitude) {
        PathPrice res = getService().GetSnappPrice(
                srcLatitude,
                srcLongitude,
                dstLatitude,
                dstLongitude
        );
        return res;
    }


    public SnappResponse getPathPriceSnapp(String srcLatitude, String srcLongitude, String dstLatitude, String dstLongitude, String authorization) {

        SnappRequest snappRequest = new SnappRequest(srcLatitude, srcLongitude, dstLatitude, dstLongitude, 1, 0, 0, false, false, "2");
        SnappResponse snappApiResponse = getSnappService().GetPathPriceSnapp(snappRequest, authorization);
        return snappApiResponse;

    }

    public Tap30Response getPathPriceTap30(String srcLatitude, String srcLongitude, String dstLatitude, String dstLongitude, String authorization) {

        Tap30Request tap30Request = new Tap30Request(srcLatitude, srcLongitude, dstLatitude, dstLongitude, 1, 0, 0, false, false, "2");
        Tap30Response tap30Response = getTap30Service().GetPathPriceTap30(tap30Request, authorization);
        return tap30Response;

    }

    public CarpinoResponse getPathPriceCarpino(String srcLatitude, String srcLongitude, String dstLatitude, String dstLongitude, String authorization) {
        //Temporary
//        String auth = "Bearer eiJjdHkiOiJKV1QiLCJlbmMiOiJBMjU2R0NNIiwiYWxnIjoiZGlyIn0..RdBoecrRFVTkHtcY.fuDxt5CjN-1821TaQsa0MJLnDjgWNuJjlS7uUdrnfMx_zHgjwJ51wcanmtdJ1R15H-_OS46RZ3TsFTrGRHJmFa8wLTBrDLMV7tCBRFkrqxfzv41rKbKj5RPMthfb8ei4POAl9U3bx9BtQRsDaZMhbhMyG_xtjNwHZTeq44coPyP96z6YDZGlGe3Q_RQNamDZG6XPXXpeiX0EynDn08dFNWhTqmpgW39ghyGPnYNxu6cS42CWUILoyyWsC3PxxR3-pf2vkf81t7flZis0Q1Adw7nTKAUSZganzbBJgCBj-3MMhj2zRUXYDVPf-QiClFuTywJed-CIYaGgyNTVAtlyNsVRRKbfnlXomTG4dTGblHzefI6WtK7uSa49YtAL0OFEgdfECZ79HBmop5YmZAMTnT4kjc1FvyVIdrtMtDeXNZcF_8ZtAkE6usb5-ya59TObTLr8JKjKkbBBPGQMwh5-vbQCFB8CF1N2D3VhwfSvEkmgCAqGR54ffnCpWgIrw3qs9gKpJIT7hMm7XjPsqxRyFWnAWey9tPOtd19Up8gjl3-gVxod6K21utpENjhytjMTqceElFxkdPnHhbkBx6ie.UD2tOle36RCdxzXoyhO8Lg";
        String origin = srcLatitude + "," + srcLongitude;
        String destination = dstLatitude + "," + dstLongitude;
        CarpinoResponse carpinoResponse = getCarpinoService().GetPathPriceCarpino(origin, destination, "0,0", "NORMAL", "SINGLE", "0", authorization);
        return carpinoResponse;
    }

    public AlopeykResponse getPathPriceAlopeyk(String srcLatitude, String srcLongitude, String dstLatitude, String dstLongitude, String authorization){
        AlopeykRequest alopeykRequest = new AlopeykRequest();
        alopeykRequest.setAddresses(srcLatitude, srcLongitude, "origin");
        alopeykRequest.setAddresses(dstLatitude, dstLongitude, "destination");
        AlopeykResponse alopeykResponse = getAlopeykService().GetAlopeykService(alopeykRequest, authorization);

        return alopeykResponse;
    }

    public List<MaximResponse> getPathPriceMaxim(String srcLatitude, String srcLongitude, String dstLatitude, String dstLongitude, String authorization){
        MaximRequest maximRequest = new MaximRequest(srcLatitude, srcLongitude, dstLatitude, dstLongitude);
        List<MaximResponse> maximResponse = getMaximkService().getMaximPrice(maximRequest.latitude, maximRequest.longitude,
                maximRequest.startLatitude, maximRequest.startLongitude, maximRequest.endLatitude, maximRequest.endLongitude,
                maximRequest.startQuick, maximRequest.endQuick, authorization, maximRequest.locale, maximRequest.tariffClass,
                maximRequest.platform, maximRequest.version);

        return maximResponse;
    }
    public TochsiResponse getPathPriceTochsi(String srcLatitude, String srcLongitude, String dstLatitude, String dstLongitude){
        TouchsiRequest touchsiRequest = new TouchsiRequest(srcLatitude,srcLongitude,dstLatitude,dstLongitude);
        TochsiResponse tochsiResponse = getTochsiService().tochsiPrice(touchsiRequest);

        return tochsiResponse;

    }

    public String tap30Unauthorizationint(String authorization) {
        tmTokensModel tokenGenerator = new tmTokensModel();
        if (!authorization.matches(""))
            tokenGenerator.getToken("tap30", tmTokensModel.tokenStatus.EXPIRED, authorization);
        else
            tokenGenerator.getToken("tap30", tmTokensModel.tokenStatus.NOT_SET, authorization);
        return tokenGenerator.getTap30Token();
    }

    public String carpinoUnauthorizationint(String authorization) {
        tmTokensModel tokenGenerator = new tmTokensModel();
        if (!authorization.matches(""))
            tokenGenerator.getToken("carpino", tmTokensModel.tokenStatus.EXPIRED, authorization);
        else
            tokenGenerator.getToken("carpino", tmTokensModel.tokenStatus.NOT_SET, authorization);
        return tokenGenerator.getCarpinoToken();
    }

    public String alopeykUnauthorizationint(String authorization) {
        tmTokensModel tokenGenerator = new tmTokensModel();
        if (!authorization.matches(""))
            tokenGenerator.getToken("alopeyk", tmTokensModel.tokenStatus.EXPIRED, authorization);
        else
            tokenGenerator.getToken("alopeyk", tmTokensModel.tokenStatus.NOT_SET, authorization);
        return tokenGenerator.getAlopeykToken();
    }

    public String maximUnauthorizationint(String authorization) {
        tmTokensModel tokenGenerator = new tmTokensModel();
        if (!authorization.matches(""))
            tokenGenerator.getToken("maxim", tmTokensModel.tokenStatus.EXPIRED, authorization);
        else
            tokenGenerator.getToken("maxim", tmTokensModel.tokenStatus.NOT_SET, authorization);
        return tokenGenerator.getMaximToken();
    }

    public String snappUnauthorizationint(String authorization) {
        tmTokensModel tokenGenerator = new tmTokensModel();
        if (!authorization.matches(""))
            tokenGenerator.getToken("snapp", tmTokensModel.tokenStatus.EXPIRED, authorization);
        else
            tokenGenerator.getToken("snapp", tmTokensModel.tokenStatus.NOT_SET, authorization);

//        String basicAuthorization = "Basic " + Base64.encode(("armin.zirak97@gmail.com:az4484"));
//        String basicAuthorization = "Basic " + "Kzk4OTE5OTI0MjcxMDoxMzc1MTI";
//        CarpinoAuthResponse carpinoAuthResponse = getCarpinoService().authenticateUser("ANDROID", "PASSENGER", "app_version", basicAuthorization);

        return tokenGenerator.getSnappToken();
    }
}
