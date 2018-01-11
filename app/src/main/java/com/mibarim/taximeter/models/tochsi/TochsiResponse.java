package com.mibarim.taximeter.models.tochsi;

import java.io.Serializable;

/**
 * Created by mohammad hossein on 27/12/2017.
 */

public class TochsiResponse implements Serializable {
    int uiFormat;
    boolean result;
    String message;
    public Value value;
    public class Value implements Serializable{
        String payableAmount;

        public String getPrice() {
            return payableAmount;
        }
    }
}
