package com.mibarim.main.services;

import android.graphics.Bitmap;

import com.mibarim.main.RestInterfaces.GetUserInfoService;
import com.mibarim.main.models.ApiResponse;
import com.mibarim.main.models.CarInfoModel;
import com.mibarim.main.models.LicenseInfoModel;
import com.mibarim.main.models.PersonalInfoModel;

import retrofit.RestAdapter;

/**
 * Created by Hamed on 3/10/2016.
 */
public class UserInfoService {

    private RestAdapter restAdapter;

    public UserInfoService(RestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    public RestAdapter getRestAdapter() {
        return restAdapter;
    }

    private GetUserInfoService getService() {
        return getRestAdapter().create(GetUserInfoService.class);
    }

    public LicenseInfoModel getLicenseInfo(String authToken) {
        LicenseInfoModel res = getService().getLicenseInfo("Bearer " + authToken, "7");//7 is for retrofit problem
        return res;
    }

    public CarInfoModel getCarInfo(String authToken) {
        CarInfoModel res = getService().getCarInfo("Bearer " + authToken, "7");
        return res;
    }

    public PersonalInfoModel getUserInfo(String authToken) {
        PersonalInfoModel res = getService().getPersonInfo("Bearer " + authToken, "7");
        return res;
    }

    public ApiResponse SaveUserPersonalInfo(String authToken, PersonalInfoModel personalInfoModel) {
        ApiResponse res = getService().SaveUserPersonalInfo("Bearer " + authToken,
                personalInfoModel.Name,
                personalInfoModel.Family,
                personalInfoModel.NationalCode,
                personalInfoModel.Gender,
                personalInfoModel.Email
        );
        return res;
    }

    public ApiResponse SaveLicenseInfo(String authToken, LicenseInfoModel licenseInfoModel) {
        ApiResponse res = getService().SaveLicenseInfo("Bearer " + authToken,
                licenseInfoModel.LicenseNo);
        return res;
    }

    public ApiResponse SaveCarInfo(String authToken, CarInfoModel carInfoModel) {
        ApiResponse res = getService().SaveCarInfo("Bearer " + authToken,
                carInfoModel.CarType,
                carInfoModel.CarColor,
                carInfoModel.CarPlateNo);
        return res;
    }
}
