package com.mibarim.taximeter.models.snapp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Arya on 12/9/2017.
 */

public class SnappPrices implements Serializable {

    @SerializedName("final")
    String amount;

    String distance;

    public String getAmount() {
        return amount;
    }
}
