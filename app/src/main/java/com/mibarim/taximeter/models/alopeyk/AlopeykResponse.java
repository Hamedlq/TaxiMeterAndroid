package com.mibarim.taximeter.models.alopeyk;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Arya on 12/7/2017.
 */

public class AlopeykResponse implements Serializable {

    private String status, message;
    public AlopeykObject object;

    public class AlopeykObject{
        List<AlopeykAddresses> addresses;
        private int price, distance, duration, delay, price_with_return;
        boolean credit, has_return, cashed;
        String status, user_credit, city, transeport_type;

        public String getPrice() {
            return String.valueOf(price);
        }
    }
}
