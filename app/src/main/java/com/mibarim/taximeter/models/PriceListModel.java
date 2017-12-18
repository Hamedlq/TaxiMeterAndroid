package com.mibarim.taximeter.models;

import android.widget.ImageView;

/**
 * Created by Arya on 12/9/2017.
 */

public class PriceListModel {

    private String serviceName;
    private String price;
    private String id;
    private int icon;

    public PriceListModel(String serviceName, String price, int icon){
        this.serviceName = serviceName;
        this.price = price;
        this.icon = icon;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
