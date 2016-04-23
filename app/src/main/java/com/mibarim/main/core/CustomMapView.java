
package com.mibarim.main.core;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.mibarim.main.util.Strings;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.tileprovider.MapTileProviderBase;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.List;

/**
 * Created by Hamed on 3/3/2016.
 */

public class CustomMapView extends MapView {

    private Overlay tapOverlay;
    private OnTapListener onTapListener;

    public CustomMapView(Context context) {
        super(context);
    }


    private void prepareTagOverlay(){

       this.tapOverlay = new Overlay(this.getContext()) {

            @Override
            protected void draw(Canvas c, MapView osmv, boolean shadow) {

            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e, MapView mapView) {

                Projection proj = mapView.getProjection();
                GeoPoint p = (GeoPoint) proj.fromPixels((int) e.getX(), (int) e.getY());
                proj = mapView.getProjection();

                final GeoPoint geoPoint = (GeoPoint) proj.fromPixels((int) e.getX(), (int) e.getY());

                if(CustomMapView.this.onTapListener != null){

                    CustomMapView.this.onTapListener.onMapTapped(geoPoint);

                    Location location = new Location();
                    location.setLatitude((double) geoPoint.getLatitudeE6() / 1000000);
                    location.setLongitude((double) geoPoint.getLongitudeE6() / 1000000);

                    CustomMapView.this.onTapListener.onMapTapped(location);
                }

                return true;
            }
        };
    }

    public void addTapListener(OnTapListener onTapListener){

        this.prepareTagOverlay();

        this.getOverlays().add(0, this.tapOverlay);

        this.onTapListener = onTapListener;
    }

    public void removeTapListener(){

        if(this.tapOverlay != null && this.getOverlays().size() > 0){

            this.getOverlays().remove(0);
        }

        this.tapOverlay = null;
        this.onTapListener = null;
    }

    public interface OnTapListener{

        void onMapTapped(GeoPoint geoPoint);

        void onMapTapped(Location location);

    }

}