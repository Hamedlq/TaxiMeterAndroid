package com.mibarim.taximeter.services;

import com.mibarim.taximeter.RestInterfaces.PriceOrder;
import com.mibarim.taximeter.models.ServiceOrderResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import retrofit.RestAdapter;

/**
 * Created by Arya on 12/25/2017.
 */

public class ServiceOrderService {

    @Named("serviceOrder")
    private RestAdapter restAdapter;

    public ServiceOrderService(RestAdapter restAdapter){
        this.restAdapter = restAdapter;
    }

    private PriceOrder getPriceOrderInterface(){
        return restAdapter.create(PriceOrder.class);
    }

    private List<String> getMessage(ServiceOrderResponse response){
        return getPriceOrderInterface().getOrder(response).Messages;
    }

    public List<String> getPriceOrder(ServiceOrderResponse response){
        List<String> Orders = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(getMessage(response).get(0));
            Orders.add(0,json.getString("SnappOrder"));
            Orders.add(1, json.getString("Tap30Order"));
            Orders.add(2, json.getString("CarpinoOrder"));
            Orders.add(3, json.getString("MibarimOrder"));
            Orders.add(4, json.getString("AlopeykOrder"));
            Orders.add(5, json.getString("MaximOrder"));
            Orders.add(6, json.getString("TelephonyOrder"));
            Orders.add(7, json.getString("OthersOrder"));
            return Orders;
        } catch (JSONException e) {
            return null;
        }
    }
}
