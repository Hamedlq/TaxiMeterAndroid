package com.mibarim.taximeter.RestInterfaces;

import com.mibarim.taximeter.favorite.favoriteModel;
import com.mibarim.taximeter.models.ApiResponse;
import com.mibarim.taximeter.models.UserInfoModel;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;


/**
 * Created by Arya on 12/27/2017.
 */

public interface UserInfoInterface {

    String URL_USER_SIGN_UP = "/newUser";
    String URL_USER_LOGIN = "/getToken";
    String URL_USER_IDENTITY_CODE = "/getTokenForNewUser";
    String URL_USER_FAVORITE_PLACES = "/getAllFavoritePlaces";
    String URL_USER_ADD_FAVORITE_PLACE = "/addFavoritePlaceEntry";
    String URL_USER_DELETE_FAVORITE_PLACE = "/deleteFavoritePlaceEntry";

    @POST(URL_USER_SIGN_UP)
    ApiResponse sendUserInfoSignUp(@Body UserInfoModel userInfo);

    @POST(URL_USER_LOGIN)
    ApiResponse sendUserInfoLogin(@Body UserInfoModel userInfo);

    @POST(URL_USER_IDENTITY_CODE)
    ApiResponse sendUserInfoIdentity(@Body UserInfoModel userInfo);

    @GET(URL_USER_FAVORITE_PLACES)
    ApiResponse getAllFavoritePlace(@Header("Authorization") String token);

    @POST(URL_USER_ADD_FAVORITE_PLACE)
    ApiResponse addFavoritePlace(@Header("Authorization") String token, @Body favoriteModel userInfo);

    @POST(URL_USER_DELETE_FAVORITE_PLACE)
    ApiResponse deleteFavoritePlace(@Header("Authorization") String token, @Body favoriteModel userInfo);
}
