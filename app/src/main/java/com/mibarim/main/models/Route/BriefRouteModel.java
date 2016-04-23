package com.mibarim.main.models.Route;

import java.io.Serializable;

/**
 * Created by Hamed on 4/6/2016.
 */
public class BriefRouteModel implements Serializable {
    public int RouteId;
    public String Name;
    public String Family;
    public String  TimingString;
    public String  PricingString;
    public String  CarString;
    public String SrcDistance;
    public String SrcLatitude;
    public String SrcLongitude;
    public String DstDistance;
    public String DstLatitude;
    public String DstLongitude;
    public int AccompanyCount;
    public boolean IsDrive;

}
