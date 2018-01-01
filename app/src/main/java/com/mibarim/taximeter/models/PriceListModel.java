package com.mibarim.taximeter.models;

/**
 * Created by Arya on 12/9/2017.
 */

public class PriceListModel {

    private String serviceName;
    private String price;
    private int icon;
    public serviceId id;

    public PriceListModel(String serviceName, String price, int icon, serviceId id) {
        this.serviceName = serviceName;
        this.price = price;
        this.icon = icon;
        this.id = id;
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

    public enum serviceId {
        SNAPP(1),
        TAP30(2),
        CARPINO(3),
        MIBARIM(4),
        ALOPEYK(5),
        MAXIM(6),
        TOUCHSI(7),
        Telephony(8),
        OTHERS(9);

        private int value;

        serviceId(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
