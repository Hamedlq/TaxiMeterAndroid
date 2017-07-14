package com.mibarim.taximeter.models.tap30;

import java.util.ArrayList;

/**
 * Created by armin on 7/14/17.
 */

public class Tap30Request {


    private Location origin;

    private Location destination;

//    private ArrayList<Object> middleDestinations;

    public Tap30Request(String srcLatitude, String srcLongitude, String dstLatitude, String dstLongitude, int i, int i1, int i2, boolean b, boolean b1, String s) {
        origin = new Location(srcLatitude, srcLongitude);
        destination = new Location(srcLatitude, srcLongitude);
    }

    public Location getOrigin() {
        return origin;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    private class Location{

        public Location(String latitude, String longitude) {
            this.longitude = Double.parseDouble(longitude);
            this.latitude = Double.parseDouble(latitude);
            this.congestionControl = 0;
        }

        private int congestionControl;

        private double longitude;

        private double latitude;

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public int getCongestionControl() {
            return congestionControl;
        }

        public void setCongestionControl(int congestionControl) {
            this.congestionControl = congestionControl;
        }
    }
}
