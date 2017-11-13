package com.mibarim.taximeter.models.carpino;

/**
 * Created by armin on 7/15/17.
 */

public class CarpinoAuthResponse {
    private String authToken;
    private String tokenType;

    public String getAuthToken() {
        return authToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
