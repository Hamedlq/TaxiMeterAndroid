package com.mibarim.taximeter.services;

import com.mibarim.taximeter.RestInterfaces.AuthtenticationsInerface;
import com.mibarim.taximeter.models.ApiResponse;
import com.mibarim.taximeter.models.tmTokensModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;

import static com.mibarim.taximeter.core.Constants.Http.GENERATE_TOKEN_ENDPOINT;
import static com.mibarim.taximeter.core.Constants.Http.URL_BASE;

/**
 * Created by Arya on 11/13/2017.
 */

public class GenerateTokenService {

    private tmTokensModel model;
    private RestAdapter adapter;
    private List<String> json;

    public GenerateTokenService(String stc, String token, int tokenStatus) {
        json = new ArrayList<>();
        adapter = new RestAdapter.Builder()
                .setEndpoint(URL_BASE)
                .build();
        model = new tmTokensModel();
        switch (stc) {
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
                break;
            case "alopeyk":
                model.setAlopeykToken(token);
                model.setAlopeykTokenStatus(tokenStatus);
                break;
            case "maxim":
                model.setMaximToken(token);
                model.setMaximTokenStatus(tokenStatus);
                break;
            case "qonqa":
                model.setMaximToken(token);
                model.setMaximTokenStatus(tokenStatus);
                break;
            case "all":
                model.setSnappToken(token);
                model.setSnappTokenStatus(tokenStatus);
                model.setTap30Token(token);
                model.setTap30TokenStatus(tokenStatus);
                model.setCarpinoToken(token);
                model.setCarpinoTokenStatus(tokenStatus);
                model.setAlopeykToken(token);
                model.setAlopeykTokenStatus(tokenStatus);
                model.setMaximToken(token);
                model.setMaximTokenStatus(tokenStatus);
        }
    }

    public tmTokensModel token() {
        ApiResponse response = adapter.create(AuthtenticationsInerface.class).getToken(model);
        json = response.Messages;
        try {
            JSONObject mainObject = new JSONObject(json.get(0));
            model.setSnappToken(mainObject.getString("SnappToken"));
            model.setSnappTokenStatus(mainObject.getInt("SnappTokenStatus"));
            model.setTap30Token(mainObject.getString("Tap30Token"));
            model.setTap30TokenStatus(mainObject.getInt("Tap30TokenStatus"));
            model.setCarpinoToken(mainObject.getString("CarpinoToken"));
            model.setCarpinoTokenStatus(mainObject.getInt("CarpinoTokenStatus"));
            model.setAlopeykToken(mainObject.getString("AloPeykToken"));
            model.setAlopeykTokenStatus(mainObject.getInt("AloPeykTokenStatus"));
            model.setMaximToken(mainObject.getString("MaximToken"));
            model.setMaximTokenStatus(mainObject.getInt("MaximTokenStatus"));
            model.setQonqaToken(mainObject.getString("QonqaToken"));
            model.setQonqaTokenStatus(mainObject.getInt("QonqaTokenStatus"));
        } catch (Exception e) {

        }
        return model;

    }
}
