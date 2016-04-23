package com.mibarim.main.RestInterfaces;

import android.graphics.Bitmap;

import com.mibarim.main.core.Constants;
import com.mibarim.main.models.ApiResponse;
import com.mibarim.main.models.CarInfoModel;
import com.mibarim.main.models.LicenseInfoModel;
import com.mibarim.main.models.PersonalInfoModel;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by Hamed on 3/10/2016.
 */
public interface GetUserInfoService {
    @POST(Constants.Http.URL_LICENSE_INFO)
    @FormUrlEncoded
    LicenseInfoModel getLicenseInfo(@Header(Constants.Http.PARAM_AUTHORIZATION) String authToken,
                                    @Field("UserId") String Id);

    @POST(Constants.Http.URL_CAR_INFO)
    @FormUrlEncoded
    CarInfoModel getCarInfo(@Header(Constants.Http.PARAM_AUTHORIZATION) String authToken,
                            @Field("UserId") String Id);

    @POST(Constants.Http.URL_PERSON_INFO)
    @FormUrlEncoded
    PersonalInfoModel getPersonInfo(@Header(Constants.Http.PARAM_AUTHORIZATION) String authToken,
                                    @Field("UserId") String Id);

    @POST(Constants.Http.URL_SET_PERSON_INFO)
    @FormUrlEncoded
    ApiResponse SaveUserPersonalInfo(@Header(Constants.Http.PARAM_AUTHORIZATION) String authToken,
                                     @Field("Name") String name,
                                     @Field("Family") String family,
                                     @Field("NationalCode") String nationalCode,
                                     @Field("Gender") String gender,
                                     @Field("Email") String email);

    @POST(Constants.Http.URL_SET_LICENSE_INFO)
    @FormUrlEncoded
    ApiResponse SaveLicenseInfo(@Header(Constants.Http.PARAM_AUTHORIZATION) String authToken,
                                @Field("LicenseNo") String licenseNo);

    @POST(Constants.Http.URL_SET_CAR_INFO)
    @FormUrlEncoded
    ApiResponse SaveCarInfo(@Header(Constants.Http.PARAM_AUTHORIZATION) String authToken,
                            @Field("CarType") String carType,
                            @Field("CarColor") String carColor,
                            @Field("CarPlateNo") String carPlateNo
    );

}
