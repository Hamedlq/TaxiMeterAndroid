package com.mibarim.taximeter.models.snapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by armin on 7/13/17.
 */

public class SnappResponse {
    private String status;
    public Data data;
    public class Data{

        @SerializedName("final")
        private String amount;

        private String distance;

        private String message;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
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


}
