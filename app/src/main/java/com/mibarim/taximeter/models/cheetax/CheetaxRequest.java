package com.mibarim.taximeter.models.cheetax;

import java.io.Serializable;

/**
 * Created by Arya on 1/3/2018.
 */

public class CheetaxRequest implements Serializable {

    private double srcLat, srcLon, dest1Lat, dest1Lon;
    private String srcAddress, dest1Address, dest2Address;

    public CheetaxRequest(String srcLat, String srcLon, String dest1Lat,String dest1Lon){

        this.srcLat = Double.valueOf(srcLat);
        this.srcLon = Double.valueOf(srcLon);
        this.dest1Lat = Double.valueOf(dest1Lat);
        this.dest1Lon = Double.valueOf(dest1Lon);
        srcAddress = " - ";
        dest1Address = " - ";
        dest2Address = " - ";
    }
}
