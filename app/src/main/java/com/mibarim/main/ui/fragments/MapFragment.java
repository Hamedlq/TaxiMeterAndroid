package com.mibarim.main.ui.fragments;

import android.content.Context;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mibarim.main.BootstrapApplication;
import com.mibarim.main.R;
import com.mibarim.main.activities.GroupActivity;
import com.mibarim.main.activities.NewRouteDelActivity;
import com.mibarim.main.activities.SuggestGroupActivity;
import com.mibarim.main.activities.SuggestRouteActivity;
import com.mibarim.main.models.Address.Location;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.overlays.MapEventsOverlay;
import org.osmdroid.bonuspack.overlays.MapEventsReceiver;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

/**
 * Created by Hamed on 3/3/2016.
 */
public class MapFragment extends Fragment {
    private MyLocationNewOverlay mLocationOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    private Context context;
    private MapView mapView;
    private Marker srcMarker;
    private Marker blueSrcMarker;
    private Marker greenSrcMarker;
    private Marker yellowSrcMarker;
    private Marker redSrcMarker;

    private Marker dstMarker;
    private Marker blueDstMarker;
    private Marker greenDstMarker;
    private Marker yellowDstMarker;
    private Marker redDstMarker;

    private boolean isBlueOn = false;
    private boolean isGreenOn = false;
    private boolean isYellowOn = false;
    private boolean isRedOn = false;

    private double minLat = 1000;
    private double minLng = 1000;
    private double maxLat = 0;
    private double maxLng = 0;


    public MapFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mapView = (MapView) inflater.inflate(R.layout.fragment_map, container, false);
        mapView.setClickable(true);
        this.mLocationOverlay = new MyLocationNewOverlay(context,
                mapView);

        final DisplayMetrics dm = context.getResources().getDisplayMetrics();

        mScaleBarOverlay = new ScaleBarOverlay(mapView);
        mScaleBarOverlay.setCentred(true);
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);


        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        mapView.getOverlays().add(this.mLocationOverlay);
        mapView.getOverlays().add(this.mScaleBarOverlay);

        srcMarker = new Marker(mapView);
        dstMarker = new Marker(mapView);
        blueSrcMarker = new Marker(mapView);
        greenSrcMarker = new Marker(mapView);
        yellowSrcMarker = new Marker(mapView);
        redSrcMarker = new Marker(mapView);

        blueDstMarker = new Marker(mapView);
        greenDstMarker = new Marker(mapView);
        yellowDstMarker = new Marker(mapView);
        redDstMarker = new Marker(mapView);

        if (getActivity() instanceof GroupActivity
                || getActivity() instanceof SuggestRouteActivity
                || getActivity() instanceof SuggestGroupActivity) {
            setSourceFlag();
            setDestinationFlag();
        }

        IMapController mapController = mapView.getController();
        mapController.setZoom(14);


        //MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(context, this);
        //mapView.getOverlays().add(0, mapEventsOverlay);

        mLocationOverlay.enableMyLocation();
        //initScreen();
        return mapView;
    }


    private void setSourceFlag() {
        mapView.getOverlays().remove(srcMarker);
        Location srcPoint = new Location();
        if (getActivity() instanceof GroupActivity) {
            srcPoint = ((GroupActivity) getActivity()).getRouteSource();
        }else if (getActivity() instanceof SuggestRouteActivity) {
            srcPoint = ((SuggestRouteActivity) getActivity()).getRouteSource();
        }else if (getActivity() instanceof SuggestGroupActivity) {
            srcPoint = ((SuggestGroupActivity) getActivity()).getRouteSource();
        }
        srcMarker.setPosition(new GeoPoint(Double.parseDouble(srcPoint.lat), Double.parseDouble(srcPoint.lng)));
        setMinMaxValues(Double.parseDouble(srcPoint.lat), Double.parseDouble(srcPoint.lng));
        srcMarker.setIcon(ContextCompat.getDrawable(context, R.mipmap.ic_from));
        srcMarker.setAnchor(Marker.ANCHOR_CENTER, 1.0f);
        mapView.getOverlays().add(0, srcMarker);
        mapView.invalidate();
    }

    private void setDestinationFlag() {
        mapView.getOverlays().remove(dstMarker);
        Location dstPoint = new Location();
        if (getActivity() instanceof GroupActivity) {
            dstPoint = ((GroupActivity) getActivity()).getRouteDestination();
        }else if (getActivity() instanceof SuggestRouteActivity) {
            dstPoint = ((SuggestRouteActivity) getActivity()).getRouteDestination();
        }else if (getActivity() instanceof SuggestGroupActivity) {
            dstPoint = ((SuggestGroupActivity) getActivity()).getRouteDestination();
        }
        dstMarker.setPosition(new GeoPoint(Double.parseDouble(dstPoint.lat), Double.parseDouble(dstPoint.lng)));
        setMinMaxValues(Double.parseDouble(dstPoint.lat), Double.parseDouble(dstPoint.lng));
        dstMarker.setIcon(ContextCompat.getDrawable(context, R.mipmap.ic_to));
        dstMarker.setAnchor(Marker.ANCHOR_CENTER, 1.0f);
        mapView.getOverlays().add(0, dstMarker);
        mapView.invalidate();
        zoomToBoundry();
    }

    private void setMinMaxValues(double lat, double lng) {
        if (minLat > lat) {
            minLat = lat;
        }
        if (minLng > lng) {
            minLng = lng;
        }
        if (maxLat < lat) {
            maxLat = lat;
        }
        if (maxLng < lng) {
            maxLng = lng;
        }
    }


    public void setRoute(String srcLat, String srcLng, String dstLat, String dstLng, int color) {
        setMinMaxValues(Double.parseDouble(srcLat), Double.parseDouble(srcLng));
        setMinMaxValues(Double.parseDouble(dstLat), Double.parseDouble(dstLng));
        switch (color) {
            case 0:
                if (isBlueOn) {
                    mapView.getOverlays().remove(blueSrcMarker);
                    mapView.getOverlays().remove(blueDstMarker);
                    isBlueOn = false;
                } else {
                    mapView.getOverlays().remove(blueSrcMarker);
                    blueSrcMarker.setPosition(new GeoPoint(Double.parseDouble(srcLat), Double.parseDouble(srcLng)));
                    blueSrcMarker.setIcon(ContextCompat.getDrawable(context, R.mipmap.ic_from));
                    blueSrcMarker.setAnchor(Marker.ANCHOR_CENTER, 1.0f);
                    mapView.getOverlays().add(0, blueSrcMarker);

                    mapView.getOverlays().remove(blueDstMarker);
                    blueDstMarker.setPosition(new GeoPoint(Double.parseDouble(dstLat), Double.parseDouble(dstLng)));
                    blueDstMarker.setIcon(ContextCompat.getDrawable(context, R.mipmap.ic_to));
                    blueDstMarker.setAnchor(Marker.ANCHOR_CENTER, 1.0f);
                    mapView.getOverlays().add(0, blueDstMarker);
                    isBlueOn = true;
                }

                break;
            case 1:
                if (isGreenOn) {
                    mapView.getOverlays().remove(greenSrcMarker);
                    mapView.getOverlays().remove(greenDstMarker);
                    isGreenOn = false;
                } else {
                    mapView.getOverlays().remove(greenSrcMarker);
                    greenSrcMarker.setPosition(new GeoPoint(Double.parseDouble(srcLat), Double.parseDouble(srcLng)));
                    greenSrcMarker.setIcon(ContextCompat.getDrawable(context, R.mipmap.ic_from));
                    greenSrcMarker.setAnchor(Marker.ANCHOR_CENTER, 1.0f);
                    mapView.getOverlays().add(0, greenSrcMarker);

                    mapView.getOverlays().remove(greenDstMarker);
                    greenDstMarker.setPosition(new GeoPoint(Double.parseDouble(dstLat), Double.parseDouble(dstLng)));
                    greenDstMarker.setIcon(ContextCompat.getDrawable(context, R.mipmap.ic_to));
                    greenDstMarker.setAnchor(Marker.ANCHOR_CENTER, 1.0f);
                    mapView.getOverlays().add(0, greenDstMarker);
                    isGreenOn = true;
                }
                break;
            case 2:
                if (isYellowOn) {
                    mapView.getOverlays().remove(yellowSrcMarker);
                    mapView.getOverlays().remove(yellowDstMarker);
                    isYellowOn = false;
                } else {
                    mapView.getOverlays().remove(yellowSrcMarker);
                    yellowSrcMarker.setPosition(new GeoPoint(Double.parseDouble(srcLat), Double.parseDouble(srcLng)));
                    yellowSrcMarker.setIcon(ContextCompat.getDrawable(context, R.mipmap.ic_from));
                    yellowSrcMarker.setAnchor(Marker.ANCHOR_CENTER, 1.0f);
                    mapView.getOverlays().add(0, yellowSrcMarker);

                    mapView.getOverlays().remove(yellowDstMarker);
                    yellowDstMarker.setPosition(new GeoPoint(Double.parseDouble(dstLat), Double.parseDouble(dstLng)));
                    yellowDstMarker.setIcon(ContextCompat.getDrawable(context, R.mipmap.ic_to));
                    yellowDstMarker.setAnchor(Marker.ANCHOR_CENTER, 1.0f);
                    mapView.getOverlays().add(0, yellowDstMarker);
                    isYellowOn = true;
                }
                break;
            case 3:
                if (isRedOn) {
                    mapView.getOverlays().remove(redSrcMarker);
                    mapView.getOverlays().remove(redDstMarker);
                    isRedOn = false;
                } else {
                    mapView.getOverlays().remove(redSrcMarker);
                    redSrcMarker.setPosition(new GeoPoint(Double.parseDouble(srcLat), Double.parseDouble(srcLng)));
                    redSrcMarker.setIcon(ContextCompat.getDrawable(context, R.mipmap.ic_from));
                    redSrcMarker.setAnchor(Marker.ANCHOR_CENTER, 1.0f);
                    mapView.getOverlays().add(0, redSrcMarker);

                    mapView.getOverlays().remove(redDstMarker);
                    redDstMarker.setPosition(new GeoPoint(Double.parseDouble(dstLat), Double.parseDouble(dstLng)));
                    redDstMarker.setIcon(ContextCompat.getDrawable(context, R.mipmap.ic_to));
                    redDstMarker.setAnchor(Marker.ANCHOR_CENTER, 1.0f);
                    mapView.getOverlays().add(0, redDstMarker);
                    isRedOn = true;
                }

                break;
        }
        zoomToBoundry();
        mapView.invalidate();
    }

    private void zoomToBoundry() {
        BoundingBoxE6 boundingBox = new BoundingBoxE6(maxLat, maxLng, minLat, minLng);
        mapView.zoomToBoundingBox(boundingBox);
    }

/*
    private void initScreen() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.srcdst_fragment, new SrcDstFragment())
                .commit();

    }
*/

//    @Override
//    public boolean singleTapConfirmedHelper(GeoPoint geoPoint) {
//        //intelligentStateSelector(geoPoint);
//        switch (((NewRouteDelActivity) getActivity()).getSrcDstStateSelector()) {
//            case "SOURCE_SELECTED":
//                mapView.getOverlays().remove(srcMarker);
//                srcMarker.setPosition(new GeoPoint(geoPoint.getLatitude(), geoPoint.getLongitude()));
//                srcMarker.setIcon(ContextCompat.getDrawable(context, R.mipmap.ic_from));
//                srcMarker.setAnchor(Marker.ANCHOR_CENTER, 1.0f);
//                mapView.getOverlays().add(0, srcMarker);
//                mapView.invalidate();
//                isSrcMarkerSet = true;
//                ((NewRouteDelActivity) getActivity()).routeRequest.SrcLatitude = String.valueOf(geoPoint.getLatitude());
//                ((NewRouteDelActivity) getActivity()).routeRequest.SrcLongitude = String.valueOf(geoPoint.getLongitude());
////                Toaster.showLong(getActivity(), "Longitude: " + geoPoint.getLongitude() + " Latitude: " + geoPoint.getLatitude());
//                break;
//            case "DESTINATION_SELECTED":
//                mapView.getOverlays().remove(dstMarker);
//                dstMarker.setPosition(new GeoPoint(geoPoint.getLatitude(), geoPoint.getLongitude()));
//                dstMarker.setIcon(ContextCompat.getDrawable(context, R.mipmap.ic_to));
//                dstMarker.setAnchor(Marker.ANCHOR_CENTER, 1.0f);
//                mapView.getOverlays().add(0, dstMarker);
//                mapView.invalidate();
//                ((NewRouteDelActivity) getActivity()).routeRequest.DstLatitude = String.valueOf(geoPoint.getLatitude());
//                ((NewRouteDelActivity) getActivity()).routeRequest.DstLongitude = String.valueOf(geoPoint.getLongitude());
////                Toaster.showLong(getActivity(), "Longitude: " + geoPoint.getLongitude() + " Latitude: " + geoPoint.getLatitude());
//                break;
//        }
//
//        return true;
//    }

//    private void intelligentStateSelector(GeoPoint geoPoint) {
//        if (isSrcMarkerSet) {
//            Location loc1 = new Location("");
//            loc1.setLatitude(geoPoint.getLatitude());
//            loc1.setLongitude(geoPoint.getLongitude());
//
//            Location loc2 = new Location("");
//            loc2.setLatitude(srcMarker.getPosition().getLatitude());
//            loc2.setLongitude(srcMarker.getPosition().getLongitude());
//
//            float distanceInMeters = loc1.distanceTo(loc2);
//            if (distanceInMeters > 1000) {
//                ((NewRouteDelActivity) getActivity()).setSrcDstStateSelector("DESTINATION_SELECTED");
//            }
//        }
//
//    }
//
//    @Override
//    public boolean longPressHelper(GeoPoint geoPoint) {
//        return false;
//    }


}
