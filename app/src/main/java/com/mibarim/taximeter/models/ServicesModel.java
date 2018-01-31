package com.mibarim.taximeter.models;

/**
 * Created by Arya on 1/17/2018.
 */

public class ServicesModel {
    private String serviceName, price;
    private int icon;
    private PriceListModel.serviceId id;

    public ServicesModel(String serviceName, String price, int icon, PriceListModel.serviceId id) {
        this.serviceName = serviceName;
        this.price = price;
        this.icon = icon;
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public PriceListModel.serviceId getId() {
        return id;
    }

    public void setId(PriceListModel.serviceId id) {
        this.id = id;
    }
}
