package com.mibarim.taximeter.services;

import com.mibarim.taximeter.RestInterfaces.AuthtenticationsInerface;
import com.mibarim.taximeter.ui.activities.AddMapActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;

import static com.mibarim.taximeter.core.Constants.Http.GENERATE_TOKEN_ENDPOINT;

/**
 * Created by Arya on 11/13/2017.
 */

public class GenerateToken {

    private tmTokensModel model;
    private RestAdapter adapter;
    private List<String> json;

    public GenerateToken(String stc, String token, int tokenStatus) {
        json = new ArrayList<>();
        adapter = new RestAdapter.Builder()
                .setEndpoint(GENERATE_TOKEN_ENDPOINT)
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
            case "all":
                model.setSnappToken(token);
                model.setSnappTokenStatus(tokenStatus);
                model.setTap30Token(token);
                model.setTap30TokenStatus(tokenStatus);
                model.setCarpinoToken(token);
                model.setCarpinoTokenStatus(tokenStatus);
        }
    }

    public tmTokensModel token() {
        json = adapter.create(AuthtenticationsInerface.class).getToken(model).Messages;
        try {
            JSONObject mainObject = new JSONObject(json.get(0));
            model.setSnappToken(mainObject.getString("SnappToken"));
            model.setSnappTokenStatus(mainObject.getInt("SnappTokenStatus"));
            model.setTap30Token(mainObject.getString("Tap30Token"));
            model.setTap30TokenStatus(mainObject.getInt("Tap30TokenStatus"));
            model.setCarpinoToken(mainObject.getString("CarpinoToken"));
            model.setCarpinoTokenStatus(mainObject.getInt("CarpinoTokenStatus"));
        } catch (Exception e) {

        }
        return model;

    }
}
