package com.mibarim.taximeter.models.alopeyk;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Arya on 12/7/2017.
 */

public class alopeykRequest implements Serializable {

    public String city;
    private String transport_type;
    private List<alopeykAddress> addresses;
    private boolean has_return, cashed;

    alopeykRequest() {
        transport_type = "motor_taxi";
        has_return = false;
        cashed = false;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<alopeykAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<alopeykAddress> addresses) {
        this.addresses = addresses;
    }

    private class alopeykAddress {
        private int Lat, Lng;
        private String type, city;

        alopeykAddress(String city) {
            this.city = city;
        }

        public int getLat() {
            return Lat;
        }

        public void setLat(int lat) {
            Lat = lat;
        }

        public int getLng() {
            return Lng;
        }

        public void setLng(int lng) {
            Lng = lng;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    }
}
