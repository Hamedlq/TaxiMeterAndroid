package com.mibarim.taximeter.models.carpino;

import java.io.Serializable;

/**
 * Created by armin on 7/15/17.
 */

public class CarpinoResponse implements Serializable {

    private String total;
    private String payable;

    public CarpinoResponse() {
        this.total = "نامعلوم";
        this.payable = "نامعلوم";
    }

    public String getTotal() {
        double d = Double.parseDouble(total);
        int i = (int) d;
        total = Integer.toString(i);
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPayable() {
        double d = Double.parseDouble(payable);
        int i = (int) d;
        payable = Integer.toString(i);
        return payable;
    }
}
