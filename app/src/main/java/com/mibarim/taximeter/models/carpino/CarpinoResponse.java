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
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
