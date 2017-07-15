package com.mibarim.taximeter.ui.activities;


import android.accounts.OperationCanceledException;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.mibarim.taximeter.BootstrapApplication;
import com.mibarim.taximeter.BootstrapServiceProvider;
import com.mibarim.taximeter.R;
import com.mibarim.taximeter.core.LocationService;
import com.mibarim.taximeter.events.NetworkErrorEvent;
import com.mibarim.taximeter.models.Address.AddressComponent;
import com.mibarim.taximeter.models.Address.AddressObject;
import com.mibarim.taximeter.models.Address.AddressResult;
import com.mibarim.taximeter.models.Address.DetailPlaceResult;
import com.mibarim.taximeter.models.Address.Location;
import com.mibarim.taximeter.models.Address.LocationPoint;
import com.mibarim.taximeter.models.Address.PathPoint;
import com.mibarim.taximeter.models.ApiResponse;
import com.mibarim.taximeter.models.PathPrice;
import com.mibarim.taximeter.models.carpino.CarpinoResponse;
import com.mibarim.taximeter.models.snapp.SnappResponse;
import com.mibarim.taximeter.models.enums.AddRouteStates;
import com.mibarim.taximeter.models.tap30.Tap30Response;
import com.mibarim.taximeter.services.AddressService;
import com.mibarim.taximeter.services.PriceService;
import com.mibarim.taximeter.ui.BootstrapActivity;
import com.mibarim.taximeter.ui.fragments.AddMapFragment;
import com.mibarim.taximeter.ui.fragments.MainAddMapFragment;
import com.mibarim.taximeter.util.SafeAsyncTask;
import com.mibarim.taximeter.util.Toaster;
import com.squareup.otto.Subscribe;

//import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import retrofit.RetrofitError;


/**
 * Initial activity for the application.
 * * <p/>
 * If you need to remove the authentication from the application please see
 */
public class AddMapActivity extends BootstrapActivity implements AddMapFragment.OnMapClickedListener {
    static final String TAG = "AddMapActivity";

    @Inject
    BootstrapServiceProvider serviceProvider;
    @Inject
    AddressService addressService;
    @Inject
    PriceService priceService;


    // The minimum distance to change Updates in meters
//    private static final long LOCATION_REFRESH_DISTANCE = 50; // 10 meters
//
//    // The minimum time between updates in milliseconds
//    private static final long LOCATION_REFRESH_TIME = 1000; // 1 minute

    private Handler mHandler;
    private int mInterval = 600000;
    private int getInfoDelay = 1000;

    private static final String PERSONAL_INFO = "personalInfo";
    private static final String LICENSE_INFO = "licenseInfo";
    private static final String CAR_INFO = "carInfo";
    private static final int GET_PERSONAL_INFO = 256;
    private int RELOAD_REQUEST = 1234;
    private int TIME_SET = 1024;
    private int Drive_SET = 8191;
    private int Location_SET = 2010;

    private CharSequence title;
    private Toolbar toolbar;

    private String srcAdd;
    private String dstAdd;

    public int sequenceNo;
    ApiResponse recommendRoutes;
    List<PathPoint> recommendPathPointList;
    public int SelectedPathRouteInRecommendPath = 1;

    private String srcLatitude;
    private String srcLongitude;
    private String dstLatitude;
    private String dstLongitude;

    private String lastLatitude;
    private String lastLongitude;

    private ApiResponse cityLocations;
    private ApiResponse response;
    //private String pathPrice;
    private PathPrice pathPrice;

    private SnappResponse snappResponse;

    private Tap30Response tap30Response;

    private CarpinoResponse carpinoResponse;

    private List<Location> wayPoints;

    private String authToken;

    //public Menu theMenu;
    //private Tracker mTracker;
    //private TrafficAddressResponse trafficAddress;
    private String trafficAddress;

    boolean fetchingSnappPrice = false;
    boolean fetchingTap30Price = false;
    boolean fetchingCarpinoPrice = false;
    boolean fetchingMibarim = false;
    boolean refreshingTokens = false;

    protected AddRouteStates stateSelector;//SOURCE_SELECTED DESTINATION_SELECTED REQUESTING EVENT_LIST_SELECTED EVENT_SOURCE_SELECT EVENT_DESTINATION_SELECT

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);
        /*if (getCacheDir() != null) {
            OpenStreetMapTileProviderConstants.setCachePath(getCacheDir().getAbsolutePath());
        }*/
        Fabric.with(this, new Crashlytics());
        BootstrapApplication.component().inject(this);

        BootstrapApplication application = (BootstrapApplication) getApplication();
        /*mTracker = application.getDefaultTracker();
        mTracker.setScreenName("AddMapActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder().setCategory("Activity").setAction("AddMapActivity").build());*/

        setContentView(R.layout.main_activity);

        // View injection with Butterknife
        ButterKnife.bind(this);

        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        stateSelector = AddRouteStates.SelectOriginState;

        // Set up navigation drawer
        title = getTitle();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (Build.VERSION.SDK_INT >= 17) {
                actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu_forward);
            }
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }*/

        initScreen();
    }


    private void initScreen() {
        //userData.DeleteTime();
        SharedPreferences prefs = getSharedPreferences(
                "com.mibarim.main", Context.MODE_PRIVATE);
        lastLatitude = prefs.getString("SrcLatitude", "35.717110");
        lastLongitude = prefs.getString("SrcLongitude", "51.426830");
        srcLatitude = lastLatitude;
        srcLongitude = lastLongitude;
        gettingPathInfo();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.main_container, new MainAddMapFragment())
                .commitAllowingStateLoss();
        mHandler = new Handler();
        //Adad.prepareInterstitialAd();
    }

    private void showBackBtn(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (Build.VERSION.SDK_INT >= 17) {
                actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu_forward);
            }
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }
    private void hideBackBtn(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setHomeButtonEnabled(false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        switch (item.getItemId()) {
            case android.R.id.home:
                switch (getSrcDstStateSelector()) {
                    case SelectOriginState:
                        finish();
                        break;
                    case SelectDestinationState:
                        setSrcDstStateSelector(AddRouteStates.SelectOriginState);
                        hideBackBtn();
                        fragmentManager.beginTransaction()
                                .replace(R.id.main_container, new MainAddMapFragment())
                                .commitAllowingStateLoss();
                        break;
                    case SelectPriceState:
                        setSrcDstStateSelector(AddRouteStates.SelectOriginState);
                        hideBackBtn();
                        fragmentManager.beginTransaction()
                                .replace(R.id.main_container, new MainAddMapFragment())
                                .commitAllowingStateLoss();
                        break;

                    default:
                        finish();
                }
                return true;
            case R.id.help_item:
                gotoHelp();
                return true;
            case R.id.about_item:
                gotoAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void gotoAbout() {
        Intent intent = new Intent(this, AboutUsActivity.class);
        this.startActivity(intent);
    }

    private void gotoHelp() {
        Intent intent = new Intent(this, HelpActivity.class);
        this.startActivity(intent);
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            switch (getSrcDstStateSelector()) {
                case SelectOriginState:
                    finish();
                    break;
                case SelectDestinationState:
                    setSrcDstStateSelector(AddRouteStates.SelectOriginState);
                    hideBackBtn();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_container, new MainAddMapFragment())
                            .commitAllowingStateLoss();
                    break;
                case SelectPriceState:
                    setSrcDstStateSelector(AddRouteStates.SelectOriginState);
                    hideBackBtn();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_container, new MainAddMapFragment())
                            .commitAllowingStateLoss();
                    break;
/*
            case "REQUESTING":
                goToDestination();
                break;
            case "EVENT_LIST_SELECTED":
                goToSource();
                break;
*/
                default:
                    this.finishAffinity();
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Subscribe
    public void onNetworkErrorEvent(NetworkErrorEvent event) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.network_error)).setPositiveButton("تلاش مجدد", dialogClickListener).show();
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    doBtnClicked();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    public void setSrcDstStateSelector(AddRouteStates state) {
        stateSelector = state;
    }

    public AddRouteStates getSrcDstStateSelector() {
        return stateSelector;
    }

    public void setSrcAddress(String address) {
        srcAdd = address;
    }

    public String getSrcAddress() {
        return srcAdd;
    }

    @Override
    public void setSrcLatLng(String latitude, String longitude) {
        srcLatitude = latitude;
        srcLongitude = longitude;
    }

    @Override
    public LocationPoint getSrcLatLng() {
        LocationPoint location = new LocationPoint();
        location.Lat = srcLatitude;
        location.Lng = srcLongitude;
        return location;
    }

    @Override
    public void setDstLatLng(String latitude, String longitude) {
        dstLatitude = latitude;
        dstLongitude = longitude;
    }

    @Override
    public LocationPoint getDstLatLng() {
        LocationPoint location = new LocationPoint();
        location.Lat = dstLatitude;
        location.Lng = dstLongitude;
        return location;
    }

    public void setDstAddress(String address) {
        dstAdd = address;
    }

    public String getDstAddress() {
        return dstAdd;
    }


    @Override
    public AddRouteStates getRouteStates() {
        return getSrcDstStateSelector();
    }

    @Override
    public void onMapStartDrag() {
        /*StopDelayedTask();
        StartSetAddress();*/
        if (stateSelector == AddRouteStates.SelectOriginState) {
//            StartSetAddress();
        } else {

        }
    }

    @Override
    public void onMapStopDrag(String latitude, String longitude) {
        StopDelayedTask();
        StartSetAddress();
        lastLatitude = latitude;
        lastLongitude = longitude;
        StartDelayedTask();
    }

    private void gettingPathInfo() {
        String latitude = lastLatitude;
        String longitude = lastLongitude;
        if (stateSelector == AddRouteStates.SelectOriginState) {
            srcLatitude = latitude;
            srcLongitude = longitude;
            getAddress("SOURCE");
        } else {
            dstLatitude = latitude;
            dstLongitude = longitude;
            getAddress("DESTINATION");
        }
        if (stateSelector == AddRouteStates.SelectDestinationState) {
            //getPathPrice();
        }
    }

    Runnable mGettingInfo = new Runnable() {
        @Override
        public void run() {
            gettingPathInfo();
        }
    };


    void StartDelayedTask() {
        mHandler.postDelayed(mGettingInfo, getInfoDelay);
    }

    void StopDelayedTask() {
        if (mGettingInfo != null) {
            mHandler.removeCallbacks(mGettingInfo);
        }
    }


    private void SetPathPrice() {
        if (fetchingCarpinoPrice || fetchingMibarim || fetchingSnappPrice || fetchingTap30Price || refreshingTokens)
            return;
        if (snappResponse != null)
             pathPrice.SnappServicePrice = snappResponse.getData().getAmount();
        if (tap30Response != null)
            pathPrice.Tap30PathPrice = tap30Response.getData().getPrice();
        if (carpinoResponse != null)
            pathPrice.CarpinoPathPrice = carpinoResponse.getTotal();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_container);
        ((MainAddMapFragment) fragment).setPrice(pathPrice);

    }

    private void SetWaitState() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_container);
        ((MainAddMapFragment) fragment).SetWaitState();
    }

    @Override
    public List<PathPoint> getRecommendPathPointList() {
        if (recommendPathPointList == null) {
            return new ArrayList<>();
        }
        return recommendPathPointList;
    }

    @Override
    public int getSelectedPathPoint() {
        return SelectedPathRouteInRecommendPath;
    }

    public void setSelectedPathPoint(int i) {
        SelectedPathRouteInRecommendPath = i;
    }

    private void getAddress(final String srcdst) {
        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                if (srcdst == "SOURCE") {
                    AddressResult response = addressService.getAddress(srcLatitude, srcLongitude);
                    List<String> add = purifyAddress(response);
                    setSrcAddress(add.get(0));
                    //List<String> bAdd = purifyBriefAddress(response);
                    //srcBriefAdd = bAdd.get(0);
                    return true;
                }
                if (srcdst == "DESTINATION") {
                    AddressResult response = addressService.getAddress(dstLatitude, dstLongitude);
                    List<String> add = purifyAddress(response);
                    setDstAddress(add.get(0));
//                    List<String> bAdd = purifyBriefAddress(response);
//                    dstBriefAdd = bAdd.get(0);
                    return true;
                }
                return false;
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof OperationCanceledException) {
                    finish();
                }
            }

            @Override
            protected void onSuccess(final Boolean res) throws Exception {
                super.onSuccess(res);
                SetAddress();
            }
        }.execute();
    }


    private void StartSetAddress() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_container);
        ((MainAddMapFragment) fragment).waitingAddress();
    }

    private void SetAddress() {
//        if (srcDstStateSelector != "EVENT_LIST_SELECTED") {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_container);
        if (fragment != null && fragment instanceof MainAddMapFragment) {
            ((MainAddMapFragment) fragment).RebuildAddressFragment();
        }
//        }
    }

    private List<String> purifyAddress(AddressResult response) {
        String route;
        String neighborhood;
        String locality;
        List<String> result = new ArrayList<String>();
        for (AddressObject address : response.results) {
            route = "";
            neighborhood = "";
            locality = "";
            for (AddressComponent component : address.address_components) {
                for (String type : component.types) {
                    if (type.trim().equals("route")) {
                        route = component.long_name;
                    }
                    if (type.trim().equals("neighborhood")) {
                        neighborhood = component.long_name + "، ";
                    }
                    if (type.trim().equals("locality")) {
                        locality = component.long_name + "، ";
                    }
                }
            }
            result.add(locality + neighborhood + route);
        }
        return result;
    }

    public void StartRequesting() {
        mHandler = new Handler();
        startRepeatingTask();
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
//                requesting(); //this function can change value of mInterval.
            } catch (Exception e) {
                Log.d(TAG, "Runable task" + e);
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        if (mStatusChecker != null) {
            mHandler.removeCallbacks(mStatusChecker);
        }
    }

    /**
     * Hide progress dialog
     */
    @SuppressWarnings("deprecation")
    protected void hideProgress() {
        dismissDialog(0);
    }

    /**
     * Show progress dialog
     */
    @SuppressWarnings("deprecation")
    protected void showProgress() {
        showDialog(0);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getText(R.string.please_wait));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
//        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            public void onCancel(final DialogInterface dialog) {
//            }
//        });
        return dialog;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if (requestCode == GET_PERSONAL_INFO && resultCode == RESULT_OK) {
            getPersonalInfoFromServer();
        }*/
        if (requestCode == RELOAD_REQUEST && resultCode == RESULT_OK) {
            authToken = null;
            Toaster.showLong(AddMapActivity.this, getString(R.string.retry), R.drawable.toast_warn);
        }
        if (requestCode == Drive_SET && resultCode == RESULT_OK) {
            RebuildSrcDstFragment();
        }
        if (requestCode == Location_SET && resultCode == RESULT_OK) {
            String PlaceId = data.getStringExtra("PlaceId");
            getPlaceDetail(PlaceId);
        }
    }

    private void RebuildSrcDstFragment() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_container);
        ((MainAddMapFragment) fragment).RebuildSrcDstFragment();
    }

    private void RebuildMainAddMapFragment() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_container);
        ((MainAddMapFragment) fragment).RebuildFragment();
    }

    private void RebuildDstFragment() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_container);
        ((MainAddMapFragment) fragment).RebuildDstFragment();
    }

    private void RebuildDstFragment(String lat, String lng) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_container);
        ((MainAddMapFragment) fragment).RebuildDstFragment(lat, lng);
    }

    private void MoveMapFragment(String lat, String lng) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_container);
        ((MainAddMapFragment) fragment).MoveMap(lat, lng);
    }

    private void getPlaceDetail(final String placeId) {
        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (getSrcDstStateSelector() == AddRouteStates.SelectOriginState) {
                    DetailPlaceResult response = addressService.getPlaceDetail(placeId);
                    srcLatitude = response.result.geometry.location.lat;
                    srcLongitude = response.result.geometry.location.lng;
                    return true;
                }
                if (getSrcDstStateSelector() == AddRouteStates.SelectDestinationState) {
                    DetailPlaceResult response = addressService.getPlaceDetail(placeId);
                    dstLatitude = response.result.geometry.location.lat;
                    dstLongitude = response.result.geometry.location.lng;
                    return true;
                }
                return false;
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
                if (getSrcDstStateSelector() == AddRouteStates.SelectOriginState) {
                    getAddress("SOURCE");
                    MoveMapFragment(srcLatitude, srcLongitude);
                } else if (getSrcDstStateSelector() == AddRouteStates.SelectDestinationState) {
                    getAddress("DESTINATION");
                    MoveMapFragment(dstLatitude, dstLongitude);
                }
            }
        }.execute();
    }

    @Override
    public void onDestroy() {
        stopRepeatingTask();
        super.onDestroy();
    }

    public void RebuildAddMapFragment() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_container);
        ((MainAddMapFragment) fragment).ReDrawAddMapFragment();
    }

    public void doBtnClicked() {
        switch (getSrcDstStateSelector()) {
            case SelectOriginState:
                setSrcDstStateSelector(AddRouteStates.SelectDestinationState);
                showBackBtn();
                RebuildDstFragment();
                break;
            case SelectDestinationState:
                setSrcDstStateSelector(AddRouteStates.SelectPriceState);
                RebuildDstFragment();
            case SelectPriceState:
                SetWaitState();
                startFetchingData();

                //Adad.showInterstitialAd(this);
                //returnOk();
                break;
        }
    }

    private void startFetchingData() {
        fetchingMibarim = true;
        fetchingSnappPrice = true;
        fetchingTap30Price = true;
        fetchingCarpinoPrice = true;
        getPathPrice();
        getPathPriceSnapp(true);
        getPathPriceTap30(true);
        getPathPriceCarpino(true);
    }

    private void returnOk() {
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
    }


    public void gotoLocationActivity() {
        Intent intent = new Intent(this, LocationSearchActivity.class);
        this.startActivityForResult(intent, Location_SET);
    }


    public void gotoMyLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            android.location.Location location = LocationService.getLocationManager(this).getLocation();
            if (location != null) {
                final FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.main_container);
                ((MainAddMapFragment) fragment).MoveMap(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
            }
        } else {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }

    private void getPathPrice() {
        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                ApiResponse response = priceService.GetPathPrice(srcLatitude, srcLongitude, dstLatitude, dstLongitude);
                Gson gson = new Gson();
                for (String json : response.Messages) {
                    pathPrice = gson.fromJson(json, PathPrice.class);
                }
                return true;
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof OperationCanceledException) {
                    finish();
                }
            }

            @Override
            protected void onSuccess(final Boolean res) throws Exception {
                super.onSuccess(res);
            }

            @Override
            protected void onFinally() throws RuntimeException {
                fetchingMibarim = false;
                SetPathPrice();
                super.onFinally();
            }
        }.execute();
    }
    public void getPathPriceSnapp(final boolean tryAgainForAuthorize)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("snappAuth",Context.MODE_PRIVATE);
        final String authorization = sharedPreferences.getString("authorization",null);
        if (authorization == null)
        {
            if (tryAgainForAuthorize) {
                refreshAuthorizationKeySnapp(new Callback() {
                    @Override

                    public void dosth() {
                        getPathPriceSnapp(false);
                    }
                });
            }
            return;
//            getPathPriceSnapp();
        }

        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                snappResponse = priceService.getPathPriceSnapp(srcLatitude, srcLongitude, dstLatitude, dstLongitude,authorization);
                return true;
            }

            @Override
            protected void onFinally() throws RuntimeException {
                fetchingSnappPrice = false;
                SetPathPrice();
                super.onFinally();
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof RetrofitError && ((RetrofitError) e).getResponse().getStatus() == 403)
                {
                    if (tryAgainForAuthorize){
                        refreshAuthorizationKeySnapp(new Callback() {
                            @Override
                            public void dosth() {
                                getPathPriceSnapp(false);
                            }
                        });
                    }
                }


//                if (e instanceof OperationCanceledException) {
//
//                    finish();
//
//                }
            }

            @Override
            protected void onSuccess(final Boolean res) throws Exception {
                super.onSuccess(res);
                SetPathPrice();
            }
        }.execute();

    }

    public void getPathPriceTap30(final boolean tryAgainForAuthorize)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("tap30Auth",Context.MODE_PRIVATE);
        final String authorization = sharedPreferences.getString("authorization",null);
        if (authorization == null)
        {
            if (tryAgainForAuthorize) {
                refreshAuthorizationKeyTap30(new Callback() {
                    @Override
                    public void dosth() {
                        getPathPriceTap30(false);
                    }
                });
            }
            return;
        }

        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                tap30Response = priceService.getPathPriceTap30(srcLatitude, srcLongitude, dstLatitude, dstLongitude,authorization);
                return true;
            }

            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof RetrofitError && ((RetrofitError) e).getResponse().getStatus() == 403 )
                {
                    if (tryAgainForAuthorize) {
                        refreshAuthorizationKeyTap30(new Callback() {
                            @Override
                            public void dosth() {
                                getPathPriceTap30(false);
                            }
                        });
                    }
                }

            }

            @Override
            protected void onFinally() throws RuntimeException {
                fetchingTap30Price = false;
                SetPathPrice();
                super.onFinally();
            }

            @Override
            protected void onSuccess(final Boolean res) throws Exception {
                super.onSuccess(res);
                SetPathPrice();
            }

        }.execute();

    }

    public void getPathPriceCarpino(final boolean tryAgainForAuthorize)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("carpino",Context.MODE_PRIVATE);
        final String authorization = sharedPreferences.getString("authorization",null);
        if (authorization == null)
        {
            if (tryAgainForAuthorize) {
                refreshAuthorizationKeyCarpino(new Callback() {
                    @Override
                    public void dosth() {
                        getPathPriceCarpino(false);
                    }
                });
            }
            return;
        }

        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                carpinoResponse = priceService.getPathPriceCarpino(srcLatitude, srcLongitude, dstLatitude, dstLongitude,authorization);
                return true;
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof RetrofitError && ((RetrofitError) e).getResponse().getStatus() == 403) {
                    if (tryAgainForAuthorize) {
                        refreshAuthorizationKeyCarpino(new Callback() {
                            @Override
                            public void dosth() {
                                getPathPriceCarpino(false);
                            }
                        });
                    }

//
                }
            }
            @Override
            protected void onFinally() throws RuntimeException {
                fetchingCarpinoPrice = false;
                SetPathPrice();
                super.onFinally();
            }

            @Override
            protected void onSuccess(final Boolean res) throws Exception {
                super.onSuccess(res);
                SetPathPrice();
            }

        }.execute();

    }

    private void refreshAuthorizationKeySnapp(final Callback callback)
    {
        refreshingTokens = true;
        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                SharedPreferences.Editor editor = getSharedPreferences("snappAuth", Context.MODE_PRIVATE).edit();
                editor.putString("authorization", priceService.getSnappAuthorizationKey());
                editor.commit();
                callback.dosth();
                refreshingTokens = false;
                return true;
            }
        }.execute();
    }
    private void refreshAuthorizationKeyTap30(final Callback callback)
    {
        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                SharedPreferences.Editor editor = getSharedPreferences("tap30Auth", Context.MODE_PRIVATE).edit();
                editor.putString("authorization", priceService.getTap30AuthorizationKey());
                editor.commit();
                callback.dosth();
                return true;
            }
        }.execute();
    }

    private void refreshAuthorizationKeyCarpino(final Callback callback)
    {
        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                SharedPreferences.Editor editor = getSharedPreferences("carpino", Context.MODE_PRIVATE).edit();
//                editor.putString("authorization", priceService.getCarpinoAuthorizationKey());
                editor.putString("authorization",
                      "Bearer eyJjdHkiOiJKV1QiLCJlbmMiOiJBMjU2R0NNIiwiYWxnIjoiZGlyIn0..npe025tzUFZUg4It.nZzDIkwk6uip4XtRTini72g24Z5UiBkuCLhyK0aVl25ZqJXrk1Y-zIa83VBRerWwQVR2eMuSrlXacM7YOzN14nP67KdgKdXZ5yVdyDcx0Z8wSMDt-0cqLv1kLYhWgbDbXlcUYx2FsAlM666phlk79CzFZXrwMTcvZCt5KH2yth6HakxXwodUhD4QyPB3zcqogYxwIzG6gvbq41a8muD7hrDLdcFkeu3j-IxpAPebZJmdFY5GFCX-nNfmX47xmicK9GaHSFspDSTsjo3Ewc2JwjnrZUxTnzVXSLC4ncQ3QHQmx1n6r1f__yjjmuxuKk6dx-xk4BD3ov4U05c7GG5ngP36jqJ5NPVN-XdjZh6-v2bSch29EigTg4V56upXDss8IwYyynnKJEV1KlHcpZoaDKbzy6QO1y-RmPl6Ezcef8tQuSXHYKQqaOR0OpZncrC5YdyVVkfstG1OLPaXPFc3cTswRa8RHADfjrUuqdplJ1epIFqjIw8ct3jWL23KgunpWRS3L_LY5Ce10a7FbJe3v5ix8RGckj5meAHuZ37Fa8bkBhBZz91nRqqi9ASnZxLMqeFDV2mMjq0Md3OSldRDf6I8SjfOmDUBsdcIo34h9zxfxnLUWv7-u-wqSB_m.Hf4KciJCM3oKnzZpkU_GIA"  );
                editor.commit();
                callback.dosth();
                return true;
            }
        }.execute();

    }
    private interface Callback{
        void dosth();
    }
}
