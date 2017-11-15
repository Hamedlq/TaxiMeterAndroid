package com.mibarim.taximeter.services;

import com.mibarim.taximeter.models.ApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Arya on 11/12/2017.
 */

public class tmTokensModel {


    public enum tokenStatus {
        NOT_SET(1),
        EXPIRED(2),
        VALID(3),
        INVALID(4);

        private int value;

        tokenStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private String snappToken;
    private String tap30Token;
    private String carpinoToken;

    private int snappTokenStatus;
    private int tap30TokenStatus;
    private int carpinoTokenStatus;

    public tmTokensModel() {
        snappToken = "";
        tap30Token = "";
        carpinoToken = "";

    }

    public int getSnappTokenStatus() {
        return snappTokenStatus;
    }

    void setSnappTokenStatus(int snappTokenStatus) {
        this.snappTokenStatus = snappTokenStatus;
    }

    public int getTap30TokenStatus() {
        return tap30TokenStatus;
    }

    void setTap30TokenStatus(int tap30TokenStatus) {
        this.tap30TokenStatus = tap30TokenStatus;
    }

    public int getCarpinoTokenStatus() {
        return carpinoTokenStatus;
    }

    void setCarpinoTokenStatus(int carpinoTokenStatus) {
        this.carpinoTokenStatus = carpinoTokenStatus;
    }


    void setSnappToken(String token) {
        this.snappToken = token;
    }

    void setTap30Token(String token) {
        this.tap30Token = token;
    }

    void setCarpinoToken(String token) {
        this.carpinoToken = token;
    }

    public String getSnappToken() {
        return snappToken;
    }

    public String getTap30Token() {
        return tap30Token;
    }

    public String getCarpinoToken() {
        return carpinoToken;
    }


    public String getToken(String stc, tokenStatus status, String authorization) {
        tmTokensModel model;
        model = new GenerateToken(stc, authorization, status.getValue()).token();

        switch (stc) {
            case "snapp":
                if (model.snappTokenStatus == 3) {
                    setSnappToken(model.snappToken);
                    setSnappTokenStatus(model.snappTokenStatus);
                } else return stc;
                break;
            case "tap30":
                if (model.tap30TokenStatus == 3) {
                    setTap30Token(model.tap30Token);
                    setTap30TokenStatus(model.tap30TokenStatus);
                } else return stc;
                break;
            case "carpino":
                if (model.carpinoTokenStatus == 3) {
                    setCarpinoToken(model.carpinoToken);
                    setCarpinoTokenStatus(model.carpinoTokenStatus);
                } else return stc;
                break;
            case "all":
                if (model.snappTokenStatus == 3) {
                    setSnappToken(model.snappToken);
                    setSnappTokenStatus(model.snappTokenStatus);
                }
                if (model.tap30TokenStatus == 3) {
                    setTap30Token(model.tap30Token);
                    setTap30TokenStatus(model.tap30TokenStatus);
                }
                if (model.carpinoTokenStatus == 3) {
                    setCarpinoToken(model.carpinoToken);
                    setCarpinoTokenStatus(model.carpinoTokenStatus);
                }
                return null;
        }
        return "";
    }
}
