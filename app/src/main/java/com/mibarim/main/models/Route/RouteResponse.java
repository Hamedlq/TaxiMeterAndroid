package com.mibarim.main.models.Route;

import android.text.TextUtils;

import com.mibarim.main.core.GravatarUtils;

import java.io.Serializable;
import java.util.List;

public class RouteResponse implements Serializable {

    public int RouteId;
    public String SrcAddress;
    public String SrcLatitude;
    public String SrcLongitude;
    public String DstAddress;
    public String DstLatitude;
    public String DstLongitude;
    public int AccompanyCount;
    public boolean IsDrive;
    public String TimingString;
    public String PricingString;
    public String CarString;
    public List<RouteGroupModel> GroupRoutes;
    public List<GroupModel> SuggestGroups;
    public List<BriefRouteModel> SuggestRoutes;

}
