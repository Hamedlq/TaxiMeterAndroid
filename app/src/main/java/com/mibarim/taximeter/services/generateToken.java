package com.mibarim.taximeter.services;

import com.mibarim.taximeter.RestInterfaces.AuthtenticationsInerface;
import com.mibarim.taximeter.core.Constants;

import retrofit.RestAdapter;

/**
 * Created by Arya on 11/13/2017.
 */

public class generateToken {

    private tmTokensModel model;
    private RestAdapter adapter;

    public generateToken(String stc, String token, int tokenStatus) {
        adapter = new RestAdapter.Builder()
                .setEndpoint("http://mibarimapp.com")
                .build();
        model = new tmTokensModel();
        switch (stc){
            case "snapp":
                model.setSnappToken(token);
                model.setSnappTokenStatus(tokenStatus);
                break;
            case "tap30":
                model.setTap30Token(token);
                model.setTap30TokenStatus(tokenStatus);
                break;
            case "carpino":
                model.setCarpinoToken(token);
                model.setCarpinoTokenStatus(tokenStatus);
        }
    }

    public tmTokensModel token() {

        return adapter.create(AuthtenticationsInerface.class).getToken(model);

    }
}
