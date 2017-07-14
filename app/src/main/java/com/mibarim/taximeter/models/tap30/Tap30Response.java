package com.mibarim.taximeter.models.tap30;

/**
 * Created by armin on 7/14/17.
 */

public class Tap30Response {

    private String result;

    private Data data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{

        private String text;

        private String address;

        private String price;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

    public Tap30Response(){
        data = new Data();
        data.setPrice("نامعلوم");
    }
}
