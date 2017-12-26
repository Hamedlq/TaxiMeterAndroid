package com.mibarim.taximeter.models.maxim;

import java.io.Serializable;

/**
 * Created by Arya on 12/20/2017.
 */

public class MaximRequest implements Serializable {

    public String latitude;
    public String longitude;
    public String startLatitude;
    public String startLongitude;
    public String endLatitude;
    public String endLongitude;
    public String locale;
    public String startQuick;
    public String endQuick;
    public String udid;
    public String tariffClass;
    public String platform;
    public String version;

    public MaximRequest(String srcLat, String srcLng, String dstLat, String dstLng){
        latitude = srcLat;
        longitude = srcLng;
        startLatitude = srcLat;
        startLongitude = srcLng;
        endLatitude = dstLat;
        endLongitude = dstLng;
        locale = "fa";
        startQuick = "0";
        endQuick = "0";
        udid = "52006e49fec87437";
        tariffClass = "1";
        platform = "CLAPP_ANDROID";
        version = "3.3.1";
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(String startLatitude) {
        this.startLatitude = startLatitude;
    }

    public String getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(String startLongitude) {
        this.startLongitude = startLongitude;
    }

    public String getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(String endLatitude) {
        this.endLatitude = endLatitude;
    }

    public String getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(String endLongitude) {
        this.endLongitude = endLongitude;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
