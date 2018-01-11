package com.mibarim.taximeter.models.cheetax;

import java.io.Serializable;

/**
 * Created by Arya on 1/3/2018.
 */

public class CheetaxResponse implements Serializable {

    private double payableVal;

    public String getPayableVal() {
        return String.valueOf((int) payableVal);
    }
}
