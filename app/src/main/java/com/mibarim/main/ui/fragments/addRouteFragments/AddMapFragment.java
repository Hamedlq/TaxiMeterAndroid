package com.mibarim.main.ui.fragments.addRouteFragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.mibarim.main.BootstrapApplication;
import com.mibarim.main.R;
import com.mibarim.main.activities.AddMapActivity;
import com.mibarim.main.core.LocationService;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.overlays.MapEventsOverlay;
import org.osmdroid.bonuspack.overlays.MapEventsReceiver;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.PathOverlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hamed on 3/3/2016.
 */
public class AddMapFragment extends Fragment implements MapEventsReceiver {

    //    @Bind(R.id.map)
    protected MapView mapView;

    private MyLocationNewOverlay mLocationOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    private Context context;
    private RelativeLayout mapViewLayout;
    private Marker srcMarker;
    private boolean isSrcMarkerSet = false;
    private Marker dstMarker;
    private Marker pointMarker;
    private List<PathOverlay> taxiRouteOverlayList;

//    private Button zoomIn;
//    private Button zoomOut;

    OnMapClickedListener mCallback;

    public AddMapFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
        context = getActivity();
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        ButterKnife.bind(this, getView());
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mapViewLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_add_map, container, false);
        mapView = (MapView) mapViewLayout.findViewById(R.id.map);
        mapView.setClickable(true);
        this.mLocationOverlay = new MyLocationNewOverlay(context,
                mapView);

        final DisplayMetrics dm = context.getResources().getDisplayMetrics();

        mScaleBarOverlay = new ScaleBarOverlay(mapView);
        mScaleBarOverlay.setCentred(true);
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);


        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(false);
        mapView.setMultiTouchControls(true);
        mapView.getOverlays().add(this.mLocationOverlay);

        mapView.getOverlays().add(this.mScaleBarOverlay);

        IMapController mapController = mapView.getController();
        mapController.setZoom(14);
        // Get the location
        Location location = LocationService.getLocationManager(context).getLocation();
        GeoPoint startPoint;
        if(location!=null){
            startPoint= new GeoPoint(location.getLatitude(), location.getLongitude());
        }else {
            startPoint= new GeoPoint(35.717110, 51.426830);
        }
        mapController.setCenter(startPoint);
        srcMarker = new Marker(mapView);
        dstMarker = new Marker(mapView);

        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(context, this);
        mapView.getOverlays().add(0, mapEventsOverlay);

        mLocationOverlay.enableMyLocation();

//        zoomIn = (Button) mapViewLayout.findViewById(R.id.zoom_in_Btn);
//        zoomOut = (Button) mapViewLayout.findViewById(R.id.zoom_out_Btn);
//        zoomIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mapView.getController().zoomIn();
//            }
//        });
//        zoomOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mapView.getController().zoomOut();
//            }
//        });
        /*pressBtn = (Button) mapViewLayout.findViewById(R.id.press_Btn);
        pressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MapActivity) getActivity()).getTrafficAddress();
            }
        });*/
        //initScreen();

        taxiRouteOverlayList=new ArrayList<PathOverlay>();

        return mapViewLayout;
    }



/*
    private void initScreen() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.srcdst_fragment, new SrcDstFragment())
                .commit();

    }
*/

    @Override
    public boolean singleTapConfirmedHelper(GeoPoint geoPoint) {
        intelligentStateSelector(geoPoint);
        switch (((AddMapActivity) getActivity()).getSrcDstStateSelector()) {
            case "SOURCE_SELECTED":
                mapView.getOverlays().remove(srcMarker);
                srcMarker.setPosition(new GeoPoint(geoPoint.getLatitude(), geoPoint.getLongitude()));
                srcMarker.setIcon(ContextCompat.getDrawable(context, R.mipmap.ic_from));
                srcMarker.setAnchor(Marker.ANCHOR_CENTER, 1.0f);
                mapView.getOverlays().add(0, srcMarker);
                mapView.invalidate();
                isSrcMarkerSet = true;
                ((AddMapActivity) getActivity()).routeRequest.SrcLatitude= String.valueOf(geoPoint.getLatitude());
                ((AddMapActivity) getActivity()).routeRequest.SrcLongitude= String.valueOf(geoPoint.getLongitude());
//                Toaster.showLong(getActivity(), "Longitude: " + geoPoint.getLongitude() + " Latitude: " + geoPoint.getLatitude());
                mCallback.onMapClicked("SOURCE_SELECTED");
                mCallback.onSrcClicked(String.valueOf(geoPoint.getLatitude()), String.valueOf(geoPoint.getLongitude()));
                break;
            case "DESTINATION_SELECTED":
                mapView.getOverlays().remove(dstMarker);
                dstMarker.setPosition(new GeoPoint(geoPoint.getLatitude(), geoPoint.getLongitude()));
                dstMarker.setIcon(ContextCompat.getDrawable(context, R.mipmap.ic_to));
                dstMarker.setAnchor(Marker.ANCHOR_CENTER, 1.0f);
                mapView.getOverlays().add(0, dstMarker);
                mapView.invalidate();
                ((AddMapActivity) getActivity()).routeRequest.DstLatitude= String.valueOf(geoPoint.getLatitude());
                ((AddMapActivity) getActivity()).routeRequest.DstLongitude= String.valueOf(geoPoint.getLongitude());
//                Toaster.showLong(getActivity(), "Longitude: " + geoPoint.getLongitude() + " Latitude: " + geoPoint.getLatitude());
                mCallback.onMapClicked("DESTINATION_SELECTED");
                mCallback.onDstClicked(String.valueOf(geoPoint.getLatitude()), String.valueOf(geoPoint.getLongitude()));
                break;
        }

        return true;
    }

    private void intelligentStateSelector(GeoPoint geoPoint) {
        if (isSrcMarkerSet) {
            Location loc1 = new Location("");
            loc1.setLatitude(geoPoint.getLatitude());
            loc1.setLongitude(geoPoint.getLongitude());

            Location loc2 = new Location("");
            loc2.setLatitude(srcMarker.getPosition().getLatitude());
            loc2.setLongitude(srcMarker.getPosition().getLongitude());

            float distanceInMeters = loc1.distanceTo(loc2);
            if (distanceInMeters > 1000) {
                ((AddMapActivity) getActivity()).setSrcDstStateSelector("DESTINATION_SELECTED");
                mCallback.onMapClicked("DESTINATION_SELECTED");
                isSrcMarkerSet = false;
            }
        }

    }

    @Override
    public boolean longPressHelper(GeoPoint geoPoint) {
        return false;
    }

    // Container Activity must implement this interface
    public interface OnMapClickedListener {
        public void onMapClicked(String SrcDstStateSelect);

        public void onSrcClicked(String latitude, String longitude);

        public void onDstClicked(String latitude, String longitude);

        public void onPointClicked(String latitude, String longitude);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnMapClickedListener) activity;
        } catch (ClassCastException e) {
        }

    }
/*

    public void showTaxiRouteOnMap(List<RoutePriceModel> routePriceModelLsit) {
        List<String> TaxiLineName = new ArrayList<String>();
        mapView.getOverlays().removeAll(taxiRouteOverlayList);
        taxiRouteOverlayList=new ArrayList<PathOverlay>();
        PathOverlay taxiRouteOverlay;// = new PathOverlay(Color.BLACK, getActivity());
        //taxiRouteOverlay = new PathOverlay(Color.RED, getActivity());
        for (RoutePriceModel model : routePriceModelLsit) {
            PathPoint pathPoint = model.Path;
            if (pathPoint != null && pathPoint.metadata != null) {
                TaxiLineName.add(pathPoint.metadata.name);
                String color = pathPoint.metadata.strokeColor;
                if (color != null && color != "") {
                    taxiRouteOverlay = new PathOverlay(Color.parseColor(color), getActivity());
                } else {
                    taxiRouteOverlay = new PathOverlay(Color.BLACK, getActivity());
                }
                for (Point point : pathPoint.path) {
                    taxiRouteOverlay.addPoint(new GeoPoint(Double.parseDouble(point.lat), Double.parseDouble(point.lng)));
                }
                Paint pPaint = taxiRouteOverlay.getPaint();
                pPaint.setStrokeWidth(5);
                taxiRouteOverlay.setPaint(pPaint);
                taxiRouteOverlayList.add(taxiRouteOverlay);
            }
        }
        //((MapActivity) getActivity()).getRoutePrice(TaxiLineName);
        mapView.getOverlays().addAll(0, taxiRouteOverlayList);
        mapView.invalidate();
        //}
    }

    public void ClearRoute() {
        mapView.getOverlays().removeAll(taxiRouteOverlayList);
        mapView.invalidate();
    }
*/

}
