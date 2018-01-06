package com.mibarim.taximeter.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Arya on 12/25/2017.
 */

public class ServiceOrderResponse implements Serializable {

    private List<Integer> servicId;

    public List getServicId() {
        return servicId;
    }
}
