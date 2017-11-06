package com.mibarim.taximeter.models.carpino;

/**
 * Created by armin on 7/15/17.
 */

public class CarpinoResponse {
    private String total;


    public CarpinoResponse() {
        this.total = "مامعلوم";
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
}
