package com.mibarim.taximeter.models.snapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by armin on 7/14/17.
 */

public class SnappAuthResponse {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private String expiresIn;

    @SerializedName("refresh_token")
    private String refreshToken;

    private String email;

    private String fullname;

}
