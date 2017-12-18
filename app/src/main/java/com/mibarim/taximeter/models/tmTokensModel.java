package com.mibarim.taximeter.models;

import com.mibarim.taximeter.services.GenerateToken;

import java.io.Serializable;

/**
 * Created by Arya on 11/12/2017.
 */

public class tmTokensModel implements Serializable {


    private String snappToken;
    private String tap30Token;
    private String carpinoToken;
    private String alopeykToken;

    private int snappTokenStatus;
    private int tap30TokenStatus;
    private int carpinoTokenStatus;
    private int alopeykTokenStatus;

    public tmTokensModel() {
        snappToken = "";
        tap30Token = "";
        carpinoToken = "";
        alopeykToken = "";

    }

    public String getAlopeykToken() {
        return alopeykToken;
    }

    public void setAlopeykToken(String alopeykToken) {
        this.alopeykToken = alopeykToken;
    }

    public int getAlopeykTokenStatus() {
        return alopeykTokenStatus;
    }

    public void setAlopeykTokenStatus(int alopeykTokenStatus) {
        this.alopeykTokenStatus = alopeykTokenStatus;
    }

    public int getSnappTokenStatus() {
        return snappTokenStatus;
    }

    public void setSnappTokenStatus(int snappTokenStatus) {
        this.snappTokenStatus = snappTokenStatus;
    }

    public int getTap30TokenStatus() {
        return tap30TokenStatus;
    }

    public void setTap30TokenStatus(int tap30TokenStatus) {
        this.tap30TokenStatus = tap30TokenStatus;
    }

    public int getCarpinoTokenStatus() {
        return carpinoTokenStatus;
    }

    public void setCarpinoTokenStatus(int carpinoTokenStatus) {
        this.carpinoTokenStatus = carpinoTokenStatus;
    }

    public String getSnappToken() {
        return snappToken;
    }

    public void setSnappToken(String token) {
        this.snappToken = token;
    }

    public String getTap30Token() {
        return tap30Token;
    }

    public void setTap30Token(String token) {
        this.tap30Token = token;
    }

    public String getCarpinoToken() {
        return carpinoToken;
    }

    public void setCarpinoToken(String token) {
        this.carpinoToken = token;
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
            case "alopeyk":
                if (model.alopeykTokenStatus == 3) {
                    setAlopeykToken(model.alopeykToken);
                    setAlopeykTokenStatus(model.alopeykTokenStatus);
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
                if (model.alopeykTokenStatus == 3) {
                    setAlopeykToken(model.alopeykToken);
                    setAlopeykTokenStatus(model.alopeykTokenStatus);
                }
                return null;
        }
        return "";
    }


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
}
