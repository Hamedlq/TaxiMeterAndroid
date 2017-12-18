package com.mibarim.taximeter.models.alopeyk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arya on 12/7/2017.
 */

public class AlopeykRequest implements Serializable {

    public String city;
    private int delay;
    private String transport_type;
    private List<AlopeykAddresses> addresses;
    private boolean has_return, pay_at_dest;

    public AlopeykRequest() {
        addresses = new ArrayList<>();
        delay = 0;
        transport_type = "motor_taxi";
        has_return = false;
        pay_at_dest = false;
        city = "tehran";
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<AlopeykAddresses> getAddresses() {
        return addresses;
    }

    public void setAddresses(String lnt, String lng, String DstLng) {
        addresses.add(new AlopeykAddresses(Double.valueOf(lnt), Double.valueOf(lng), DstLng));
    }
}
