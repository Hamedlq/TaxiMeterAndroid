package com.mibarim.taximeter.favorite;

import android.accounts.OperationCanceledException;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.mibarim.taximeter.BootstrapApplication;
import com.mibarim.taximeter.R;
import com.mibarim.taximeter.core.Constants;
import com.mibarim.taximeter.models.Address.DetailPlaceResult;
import com.mibarim.taximeter.models.ApiResponse;
import com.mibarim.taximeter.services.AddressService;
import com.mibarim.taximeter.services.UserInfoService;
import com.mibarim.taximeter.ui.activities.LocationSearchActivity;
import com.mibarim.taximeter.ui.dialog_fav;
import com.mibarim.taximeter.util.SafeAsyncTask;

import java.util.Locale;

import javax.inject.Inject;


/**
 * Created by mohammad hossein on 14/12/2017.
 */

public class FavoriteMapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int ERROR_DIALOG_REQUEST = 9001;
    AddressService addressService;
    GoogleMap mMap;
    Button fav_choose;
    String place_name;
    LatLng latLng;
    RelativeLayout address_search;
    String srcLatitude, srcLongitude;
    ApiResponse apiResponse;
    @Inject
    UserInfoService userInfoService;
    String token;
    private int Location_SET = 1515;
//    DataBaseFav db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_map);
        BootstrapApplication.component().inject(this);

        SharedPreferences preferences = getSharedPreferences("user_token", MODE_PRIVATE);
        token = preferences.getString("token", null);
        apiResponse = new ApiResponse();
        fav_choose = (Button) findViewById(R.id.fav_choose);
        address_search = (RelativeLayout) findViewById(R.id.address_layout_fav);
        addressService = new AddressService();
//        db = new DataBaseFav(this);

        if (servicesOK()) {
            Locale.setDefault(new Locale("ir"));
            initMap();
        } else
            Toast.makeText(getApplicationContext(), "متاسفانه سرویس " +
                    "وگل در دسترس نمی باشد", Toast.LENGTH_LONG).show();

        address_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLocationActivity();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Location_SET && resultCode == RESULT_OK) {
            String PlaceId = data.getStringExtra("PlaceId");
            getPlaceDetail(PlaceId);
        }
    }

    public void gotoLocationActivity() {
        Intent intent = new Intent(this, LocationSearchActivity.class);
        intent.putExtra("checkActivity", 1);
        this.startActivityForResult(intent, Location_SET);
    }

    private void getPlaceDetail(final String placeId) {
        final SharedPreferences pref = getSharedPreferences(Constants.Geocoding.GOOGLE_KEYS, MODE_PRIVATE);
        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                DetailPlaceResult response = addressService.getPlaceDetail(placeId, pref.getString(Constants.Geocoding.GOOGLE_AUTOCOMPLETE_AUTH, null));
                srcLatitude = response.result.geometry.location.lat;
                srcLongitude = response.result.geometry.location.lng;
                return true;
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof OperationCanceledException) {
                    //finish();
                }
            }

            @Override
            protected void onSuccess(final Boolean res) throws Exception {
                super.onSuccess(res);
                //Toaster.showLong(AddMapActivity.this, srcLatitude + " " + srcLongitude);
                goToLocationByAnimate(Double.parseDouble(srcLatitude), Double.parseDouble(srcLongitude), 15);
            }
        }.execute();
    }


    public boolean servicesOK() {

        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);

        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (isAvailable == ConnectionResult.SUCCESS)
            return true;
        else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, this, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(getApplicationContext(), "cant connect to mapping services", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    private void initMap() {
        if (mMap == null) {
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFav);

            mapFragment.getMapAsync(this);

        }

    }

    @Override
    public void onMapReady(final GoogleMap map) {

        mMap = map;

        if (mMap != null) {

            goToLocation(35.706882, 51.408412, 15);


//            mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
//                @Override
//                public void onMarkerDragStart(Marker marker) {
//
//                }
//
//                @Override
//                public void onMarkerDrag(Marker marker) {
//                    marker.setPosition(new LatLng());
//                }
//
//                @Override
//                public void onMarkerDragEnd(Marker marker) {
//                    lng = marker.getPosition();
//                    place_name = marker.getTitle();
//                    place_snippet = marker.getSnippet();
//                }
//            });

            mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                @Override
                public void onCameraIdle() {
                    latLng = mMap.getCameraPosition().target;
                }
            });


            fav_choose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final dialog_fav dialog = new dialog_fav(FavoriteMapActivity.this);
                    dialog.show();
                    dialog.btn_detail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (dialog.editText_detail.getText().toString().replaceAll("\\s+", "").matches(""))
                                Toast.makeText(getApplicationContext(), "لطفا عنوان آدرس خود را وارد کنید", Toast.LENGTH_LONG).show();
                            else {
                                place_name = dialog.editText_detail.getText().toString();
                                goToRecy();
                            }
                        }
                    });
                }
            });
        }
    }

    public void goToRecy() {
        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                apiResponse = userInfoService.addFavoritePlace(token, new favoriteModel(place_name, String.valueOf(latLng.latitude), String.valueOf(latLng.longitude), 0));
                return true;
            }

            @Override
            protected void onSuccess(Boolean aBoolean) throws Exception {
                super.onSuccess(aBoolean);
                finish();
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                super.onException(e);
                Toast.makeText(FavoriteMapActivity.this, getString(R.string.server_not_responde), Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    private void goToLocation(double lat, double lng, float zoom) {
        LatLng latLng = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        mMap.moveCamera(update);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


    }

    private void goToLocationByAnimate(double lat, double lng, float zoom) {
        LatLng latLng = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        mMap.animateCamera(update);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
