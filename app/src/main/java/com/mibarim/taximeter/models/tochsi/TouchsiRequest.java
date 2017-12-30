package com.mibarim.taximeter.models.tochsi;


import java.io.Serializable;

/**
 * Created by mohammad hossein on 27/12/2017.
 */

public class TouchsiRequest implements Serializable {
    public String Token;
    public Value Value;

    public TouchsiRequest(String srcLatitude, String srcLongitude, String dstLatitude, String dstLongitude) {
        Token = "bc17c0f5be66a8fc98850cff5ffee3d5";
        Value = new Value(srcLatitude,srcLongitude,dstLatitude,dstLongitude);
    }


    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public TouchsiRequest.Value getValue() {
        return Value;
    }

    public void setValue(TouchsiRequest.Value value) {
        Value = value;
    }

    public class Value implements Serializable {
        public endTrip endTrip;
        public setting settings;
        public startTrip startTrip;

        public Value(String srcLatitude, String srcLongitude, String dstLatitude, String dstLongitude) {
            endTrip = new endTrip(Double.parseDouble(dstLatitude), Double.parseDouble(dstLongitude));
            settings = new setting();
            startTrip = new startTrip(Double.parseDouble(srcLatitude),Double.parseDouble(srcLongitude));
        }


        public TouchsiRequest.Value.endTrip getEndTrip() {
            return endTrip;
        }

        public void setEndTrip(TouchsiRequest.Value.endTrip endTrip) {
            this.endTrip = endTrip;
        }

        public setting getSettings() {
            return settings;
        }

        public void setSettings(setting settings) {
            this.settings = settings;
        }

        public TouchsiRequest.Value.startTrip getStartTrip() {
            return startTrip;
        }

        public void setStartTrip(TouchsiRequest.Value.startTrip startTrip) {
            this.startTrip = startTrip;
        }

        public class endTrip implements Serializable {
            double latitude;
            double longitude;

            public endTrip(double latitude, double longitude) {
                this.latitude = latitude;
                this.longitude = longitude;
            }

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }
        }

        public class setting implements Serializable {
            String serviceType;

            public setting() {
                this.serviceType = "00030001";
            }

            public String getServiceType() {
                return serviceType;
            }

            public void setServiceType() {
                this.serviceType = "00030001";
            }
        }

        public class startTrip implements Serializable {
            double latitude;
            double longitude;

            public startTrip(double latitude, double longitude) {
                this.latitude = latitude;
                this.longitude = longitude;
            }

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }
        }
    }
}
