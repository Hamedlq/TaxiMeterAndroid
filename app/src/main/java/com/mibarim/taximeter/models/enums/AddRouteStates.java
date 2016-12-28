package com.mibarim.taximeter.models.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamed on 3/8/2016.
 */
public enum AddRouteStates {

    SelectOriginState("SelectOriginState", 3),
    SelectDestinationState("SelectDestinationState", 4),
    SelectPriceState("SelectPriceState", 5);

    private String stringValue;
    private int intValue;

    private AddRouteStates(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}


