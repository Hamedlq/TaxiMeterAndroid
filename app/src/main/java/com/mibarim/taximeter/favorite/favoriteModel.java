package com.mibarim.taximeter.favorite;

import java.io.Serializable;

/**
 * Created by mohammad hossein on 13/12/2017.
 */

public class favoriteModel implements Serializable{
    public String cardText;
    public String lat;
    public String lng;



    public String getCardText() {
        return cardText;
    }

    public void setCardText(String cardText) {
        this.cardText = cardText;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
