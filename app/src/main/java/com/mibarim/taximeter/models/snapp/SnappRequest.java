package com.mibarim.taximeter.models.snapp;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by armin on 7/13/17.
 */

public class SnappRequest{

    @SerializedName("origin_lat")
    private String SrcLat;

    @SerializedName("origin_lng")
    private String SrcLng;

    @SerializedName("destination_lat")
    private String DstLat;

    @SerializedName("destination_lng")
    private String DstLng;

    @SerializedName("service_type")
    private int serviceType;

    @SerializedName("sub_service_type")
    private int subServiceType;

    @SerializedName("destination_place_id")
    private int destinationPlaceId;

    @SerializedName("round_trip")
    private boolean roundTrip;

    @SerializedName("services")
    private boolean services;

    @SerializedName("tag")
    private String tag;

    public String getSrcLat() {
        return SrcLat;
    }

    public void setSrcLat(String srcLat) {
        SrcLat = srcLat;
    }

    public String getSrcLng() {
        return SrcLng;
    }

    public void setSrcLng(String srcLng) {
        SrcLng = srcLng;
    }

    public String getDstLat() {
        return DstLat;
    }

    public void setDstLat(String dstLat) {
        DstLat = dstLat;
    }

    public String getDstLng() {
        return DstLng;
    }

    public void setDstLng(String dstLng) {
        DstLng = dstLng;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public int getSubServiceType() {
        return subServiceType;
    }

    public void setSubServiceType(int subServiceType) {
        this.subServiceType = subServiceType;
    }

    public int getDestinationPlaceId() {
        return destinationPlaceId;
    }

    public void setDestinationPlaceId(int destinationPlaceId) {
        this.destinationPlaceId = destinationPlaceId;
    }

    public boolean isRoundTrip() {
        return roundTrip;
    }

    public void setRoundTrip(boolean roundTrip) {
        this.roundTrip = roundTrip;
    }

    public boolean isServices() {
        return services;
    }

    public void setServices(boolean services) {
        this.services = services;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public SnappRequest() {}

    public SnappRequest(String srcLat, String srcLng, String dstLat, String dstLng, int serviceType, int subServiceType, int destinationPlaceId, boolean roundTrip, boolean services, String tag) {
        this.SrcLat = srcLat;
        this.SrcLng = srcLng;
        this.DstLat = dstLat;
        this.DstLng = dstLng;
        this.serviceType = serviceType;
        this.subServiceType = subServiceType;
        this.destinationPlaceId = destinationPlaceId;
        this.roundTrip = roundTrip;
        this.services = services;
        this.tag = tag;

    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
