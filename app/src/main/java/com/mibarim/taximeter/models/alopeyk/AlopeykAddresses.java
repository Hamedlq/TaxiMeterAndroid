package com.mibarim.taximeter.models.alopeyk;

import java.io.Serializable;

/**
 * Created by Arya on 12/11/2017.
 */

public class AlopeykAddresses implements Serializable {

    private double lat, lng;
    private String type, city;

    AlopeykAddresses(double lat, double lng, String type) {
        this.lat = lat;
        this.lng = lng;
        this.type = type;
        city = "tehran";
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getlat() {
        return lat;
    }

    public void setlat(double lat) {
        lat = lat;
    }

    public double getlng() {
        return lng;
    }

    public void setlng(double lng) {
        lng = lng;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
