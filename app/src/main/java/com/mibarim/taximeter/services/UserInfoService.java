package com.mibarim.taximeter.services;

import com.mibarim.taximeter.RestInterfaces.UserInfoInterface;
import com.mibarim.taximeter.favorite.favoriteModel;
import com.mibarim.taximeter.models.ApiResponse;
import com.mibarim.taximeter.models.UserInfoModel;

import javax.inject.Named;

import retrofit.RestAdapter;

/**
 * Created by Arya on 12/27/2017.
 */

public class UserInfoService {

    @Named("userInfo")
    private RestAdapter userAdapter;

    public UserInfoService(RestAdapter userAdapter) {
        this.userAdapter = userAdapter;
    }

    private RestAdapter getUserAdapter() {
        return userAdapter;
    }

    private UserInfoInterface getService() {
        return getUserAdapter().create(UserInfoInterface.class);
    }

    public ApiResponse sendUserInfoSignUp(UserInfoModel userInfo) {
        return getService().sendUserInfoSignUp(userInfo);
    }

    public ApiResponse sendUserInfoLogin(UserInfoModel userInfo) {
        return getService().sendUserInfoLogin(userInfo);
    }

    public ApiResponse sendUserIdentity(UserInfoModel userInfo) {
        return getService().sendUserInfoIdentity(userInfo);
    }

    public ApiResponse getFavoritePlaces(String token){
        return getService().getAllFavoritePlace(token);
    }

    public ApiResponse addFavoritePlace(String token, favoriteModel favoriteModel){
        return getService().addFavoritePlace(token, favoriteModel);
    }

    public ApiResponse deleteFavoritePlace(String token, favoriteModel favoriteModel){
        return getService().deleteFavoritePlace(token, favoriteModel);
    }
}
