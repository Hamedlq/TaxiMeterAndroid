package com.mibarim.taximeter.models.qonqa;

import java.io.Serializable;

/**
 * Created by Arya on 1/1/2018.
 */

public class QonqaResponse implements Serializable {

    private int cost;

    public String getCost() {
        String toman;
        try {
            toman = String.valueOf(cost / 10);
        }
        catch (Exception e){
            toman = "0";
        }
        return toman;
    }
}
