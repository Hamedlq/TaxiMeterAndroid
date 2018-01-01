package com.mibarim.taximeter.services;

import com.mibarim.taximeter.RestInterfaces.GoogleKeyInterface;
import com.mibarim.taximeter.core.Constants;
import com.mibarim.taximeter.models.ApiResponse;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;

/**
 * Created by Arya on 12/2/2017.
 */

public class GoogleAutocompleteService {

    private RestAdapter restAdapter;
    private List<String> json;

    public GoogleAutocompleteService() {
        json = new ArrayList<>();
        this.restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.Http.URL_BASE)
                .build();
    }

    private RestAdapter getRestAdapter() {
        return restAdapter;
    }

    private GoogleKeyInterface getTokenService() {

        return getRestAdapter().create(GoogleKeyInterface.class);
    }

    public String getKey(String key){
        ApiResponse response = getTokenService().GetKey(key);
        json = response.Messages;
        try {
            JSONObject mainObject = new JSONObject(json.get(0));
            return mainObject.getString("Token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
