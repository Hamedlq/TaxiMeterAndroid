package com.mibarim.taximeter.favorite;

import java.io.Serializable;

/**
 * Created by mohammad hossein on 13/12/2017.
 */

public class favoriteModel implements Serializable{
    public String cardText;
    public String cardSecondText;
    public String favPlace;
    public String lat;
    public String lng;
    public int id;

    public favoriteModel() {
    }

    public String getCardSecondText() {
        return cardSecondText;
    }
    public favoriteModel(String favPlace, String lat, String lng, int id) {
        this.favPlace = favPlace;
        this.lat = lat;
        this.lng = lng;
        this.id = id;
    }

    public void setCardSecondText(String cardSecondText) {
        this.cardSecondText = cardSecondText;
    }

    public String getCardText() {
        return cardText;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFavPlace() {
        return favPlace;
    }

    public void setFavPlace(String favPlace) {
        this.favPlace = favPlace;
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
