package com.mibarim.taximeter.models.snapp;

import java.io.Serializable;
import java.util.List;

/**
 * Created by armin on 7/13/17.
 */

public class SnappResponse implements Serializable {
    private String status;
    public Data data;
    public class Data implements Serializable {

        List<SnappPrices> prices;

        public List<SnappPrices> getPrices() {
            return prices;
        }

        public void setPrices(List<SnappPrices> prices) {
            this.prices = prices;
        }

        //        @SerializedName("final")
//        private String amount;
//
//        private String distance;
//
//        private String message;
//
//        public String getAmount() {
//            return amount;
//        }
//
//        public void setAmount(String amount) {
//            this.amount = amount;
//        }
//
//        public String getDistance() {
//            return distance;
//        }
//
//        public void setDistance(String distance) {
//            this.distance = distance;
//        }
//
//        public String getMessage() {
//            return message;
//        }
//
//        public void setMessage(String message) {
//            this.message = message;
//        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public SnappResponse(){
        data = new Data();
//        data.setAmount("نامعلوم");
    }

}
