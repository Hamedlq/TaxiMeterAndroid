package com.mibarim.taximeter.services;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.mibarim.taximeter.RestInterfaces.AuthtenticationsInerface;
import com.mibarim.taximeter.core.Constants;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Arya on 11/12/2017.
 */

public class tmTokensModel {


    public enum tokenStatus {
        NOT_SET(1),
        EXPIRED(2),
        VALID(3);

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
    public String carpinoToken;

    private int snappTokenStatus;
    private int tap30TokenStatus;
    private int carpinoTokenStatus;

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


    public tmTokensModel() {
        snappToken = "";
        tap30Token = "";
        carpinoToken = "";

    }


    public void setSnappToken(String token) {
        this.snappToken = token;
    }

    public void setTap30Token(String token) {
        this.tap30Token = token;
    }

    public void setCarpinoToken(String token) {
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


    public void getToken(String stc, tokenStatus status, String authorization) {

        if (stc.matches("snapp")) {
            if (status == tokenStatus.EXPIRED)
                setCarpinoToken(new generateToken(stc, authorization, tokenStatus.EXPIRED.getValue()).token().snappToken);
            else
                setCarpinoToken(new generateToken(stc, authorization, tokenStatus.NOT_SET.getValue()).token().snappToken);
        } else if (stc.matches("tap30")) {
            if (status == tokenStatus.EXPIRED)
                setCarpinoToken(new generateToken(stc, authorization, tokenStatus.EXPIRED.getValue()).token().tap30Token);
            else
                setCarpinoToken(new generateToken(stc, authorization, tokenStatus.NOT_SET.getValue()).token().tap30Token);
        } else if (stc.matches("carpino")) {
            if (status == tokenStatus.EXPIRED)
                setCarpinoToken(new generateToken(stc, authorization, tokenStatus.EXPIRED.getValue()).token().carpinoToken);
            else
                setCarpinoToken(new generateToken(stc, authorization, tokenStatus.NOT_SET.getValue()).token().carpinoToken);
        }
    }

}
