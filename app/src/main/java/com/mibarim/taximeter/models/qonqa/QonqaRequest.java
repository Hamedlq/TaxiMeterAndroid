package com.mibarim.taximeter.models.qonqa;

import java.io.Serializable;

/**
 * Created by Arya on 1/1/2018.
 */

public class QonqaRequest implements Serializable {

    private int id, x3, y3, type;
    private double x1, y1, x2, y2;
    private boolean roundtrip;

    public QonqaRequest(String srcLat, String srcLng, String dstLat, String dstLng) {
        x1 = Double.valueOf(srcLat);
        y1 = Double.valueOf(srcLng);
        x2 = Double.valueOf(dstLat);
        y2 = Double.valueOf(dstLng);
        id = 134581;
        x3 = 0;
        y3 = 0;
        roundtrip = false;
        type = 1;

    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }
}
