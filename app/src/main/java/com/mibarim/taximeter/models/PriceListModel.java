package com.mibarim.taximeter.models;

/**
 * Created by Arya on 12/9/2017.
 */

public class PriceListModel {

    private String serviceName;
    private String price;

    public int getId() {
        return id;
    }

    private int id;
    private int icon;

    public PriceListModel(String serviceName, String price, int icon) {
        this.serviceName = serviceName;
        this.price = price;
        this.icon = icon;
        switch (serviceName) {
            case "اسنپ اکو":
                id = 1;
                break;
            case "تپسی":
                id = 2;
                break;
            case "کارپینو":
                id = 3;
                break;
            case "تاکسی اشتراکی می\u200Cبریم":
                id = 4;
                break;
            default:
                id = 5;
        }
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
