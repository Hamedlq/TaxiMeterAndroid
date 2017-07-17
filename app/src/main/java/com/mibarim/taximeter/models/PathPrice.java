package com.mibarim.taximeter.models;

import com.mibarim.taximeter.models.Address.PathPoint;

import java.io.Serializable;

/**
 * Created by Hamed on 2/28/2016.
 */
public class PathPrice implements Serializable {
    public PathPoint PathRoute;
    public String SharedServicePrice;
    public String PrivateServicePrice;
    public String SnappServicePrice;

}
