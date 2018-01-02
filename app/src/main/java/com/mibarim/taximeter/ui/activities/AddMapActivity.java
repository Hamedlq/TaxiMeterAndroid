package com.mibarim.taximeter.ui.activities;


import android.accounts.OperationCanceledException;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.mibarim.taximeter.BootstrapApplication;
import com.mibarim.taximeter.BootstrapServiceProvider;
import com.mibarim.taximeter.R;
import com.mibarim.taximeter.core.Constants;
import com.mibarim.taximeter.core.LocationService;
import com.mibarim.taximeter.dataBase.DataBaseFav;
import com.mibarim.taximeter.events.NetworkErrorEvent;
import com.mibarim.taximeter.favorite.favoriteModel;
import com.mibarim.taximeter.favorite.favorite_place;
import com.mibarim.taximeter.models.Address.AddressComponent;
import com.mibarim.taximeter.models.Address.AddressObject;
import com.mibarim.taximeter.models.Address.AddressResult;
import com.mibarim.taximeter.models.Address.DetailPlaceResult;
import com.mibarim.taximeter.models.Address.Location;
import com.mibarim.taximeter.models.Address.LocationPoint;
import com.mibarim.taximeter.models.Address.PathPoint;
import com.mibarim.taximeter.models.ApiResponse;
import com.mibarim.taximeter.models.PathPrice;
import com.mibarim.taximeter.models.ServiceOrderResponse;
import com.mibarim.taximeter.models.UserInfoModel;
import com.mibarim.taximeter.models.alopeyk.AlopeykResponse;
import com.mibarim.taximeter.models.carpino.CarpinoResponse;
import com.mibarim.taximeter.models.enums.AddRouteStates;
import com.mibarim.taximeter.models.maxim.MaximResponse;
import com.mibarim.taximeter.models.qonqa.QonqaResponse;
import com.mibarim.taximeter.models.snapp.SnappResponse;
import com.mibarim.taximeter.models.tmTokensModel;
import com.mibarim.taximeter.models.tochsi.TochsiResponse;
import com.mibarim.taximeter.ratingApp;
import com.mibarim.taximeter.services.AddressService;
import com.mibarim.taximeter.services.PriceService;
import com.mibarim.taximeter.services.ServiceOrderService;
import com.mibarim.taximeter.ui.AlertDialogTheme;
import com.mibarim.taximeter.ui.BootstrapActivity;
import com.mibarim.taximeter.ui.fragments.AddMapFragment;
import com.mibarim.taximeter.ui.fragments.MainAddMapFragment;
import com.mibarim.taximeter.ui.fragments.SrcDstFragment;
import com.mibarim.taximeter.util.SafeAsyncTask;
import com.mibarim.taximeter.util.Toaster;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import retrofit.RetrofitError;
//import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants;
//import static com.mibarim.taximeter.core.Constants.Geocoding.GOOGLE_AUTOCOMPLETE_SERVICE_VALUE;


/**
 * Initial activity for the application.
 * * <p/>
 * If you need to remove the authentication from the application please see
 */
public class AddMapActivity extends BootstrapActivity implements AddMapFragment.OnMapClickedListener {
    static final String TAG = "AddMapActivity";
    private static final String PERSONAL_INFO = "personalInfo";
    private static final String LICENSE_INFO = "licenseInfo";
    private static final String CAR_INFO = "carInfo";
    private static final int GET_PERSONAL_INFO = 256;
    public int sequenceNo;
    public int SelectedPathRouteInRecommendPath = 1;
    public String thelat;
    protected AddRouteStates stateSelector;//SOURCE_SELECTED DESTINATION_SELECTED REQUESTING EVENT_LIST_SELECTED EVENT_SOURCE_SELECT EVENT_DESTINATION_SELECT
    // The minimum distance to change Updates in meters
//    private static final long LOCATION_REFRESH_DISTANCE = 50; // 10 meters
//
//    // The minimum time between updates in milliseconds
//    private static final long LOCATION_REFRESH_TIME = 1000; // 1 minute
    @Inject
    BootstrapServiceProvider serviceProvider;
    @Inject
    AddressService addressService;
    @Inject
    PriceService priceService;
    @Inject
    ServiceOrderService serviceOrderService;
    boolean isSnappShown = false;
    boolean isTap30Shown = false;
    boolean isCarpinoNormalShown = false;
    boolean isCarpinoVanShown = false;
    boolean isCarpinoWomenShown = false;
    boolean isCarpinoVipShown = false;
    boolean isTouchsiShown = false;
    boolean isMibarimShown = false;
    boolean isTelephonyShown = false;
    boolean isAlopeykShown = false;
    boolean isMaximShown = false;
    boolean isQonqaShown = false;
    boolean refreshingTokens = false;
    ApiResponse recommendRoutes;
    List<PathPoint> recommendPathPointList;
    PathPrice tap30PathPriceResponse;
    PathPrice snappPathPriceResponse;
    SharedPreferences prefs = null;
    String[] stc = new String[5];
    private Handler mHandler;
    private int mInterval = 600000;
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
    private int getInfoDelay = 1000;
    private int RELOAD_REQUEST = 1234;
    private int TIME_SET = 1024;
    private int Drive_SET = 8191;
    private int Location_SET = 2010;
    private int FAV_SET = 9999;
    private CharSequence title;
    private Toolbar toolbar;
    private String srcAdd;
    private String dstAdd;
    private String srcLatitude;
    private String srcLongitude;
    private String dstLatitude;
    private String dstLongitude;
    private String lastLatitude;
    private String lastLongitude;
    Runnable mGettingInfo = new Runnable() {
        @Override
        public void run() {
            gettingPathInfo();
        }
    };
    private ApiResponse cityLocations;
    private ApiResponse response;
    //private String pathPrice;
    private PathPrice pathPrice;
    private SnappResponse snappResponse;
    private CarpinoResponse[] carpinoResponse;
    private AlopeykResponse alopeykResponse;
    private QonqaResponse qonqaResponse;
    private List<MaximResponse> maximResponse;
    private TochsiResponse tochsiResponse;
    private List<Location> wayPoints;
    private String authToken;
    private boolean isGettingPrice;
    private LinearLayout fav_on_map, fav_on_map1, fav_on_map2, fav_on_map3, fav_on_map4, fav_on_map5;
    private TextView fav_on_map_text1, fav_on_map_text2, fav_on_map_text3, fav_on_map_text4, fav_on_map_text5;
    private DataBaseFav db;
    private List<favoriteModel> items;
    //public Menu theMenu;
    //private Tracker mTracker;
    //private TrafficAddressResponse trafficAddress;
    private SharedPreferences dstPrefs;
    private List<String> priceOrderList;
    private UserInfoModel userInfo;
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

    public List<String> getPriceOrderList() {
        carpinoResponse = new CarpinoResponse[4];
        return priceOrderList;
    }

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
        SharedPreferences pref = getSharedPreferences("com.mibarim.main", Context.MODE_PRIVATE);
        pref.edit().putString("DstLatitude", null).apply();
        pref.edit().putString("DstLongitude", null).apply();
        initScreen();
        final SharedPreferences snappPreferences;
        final SharedPreferences tap30Preferences;
        final SharedPreferences carpinoPreferences;
        final SharedPreferences alopeykPreferences;
        final SharedPreferences maximPreferences;
        final tmTokensModel firstLoad = new tmTokensModel();
        snappPreferences = getSharedPreferences("snappAuth", Context.MODE_PRIVATE);
        stc[0] = snappPreferences.getString("authorization", "");
        tap30Preferences = getSharedPreferences("tap30Auth", Context.MODE_PRIVATE);
        stc[1] = tap30Preferences.getString("authorization", "");
        carpinoPreferences = getSharedPreferences("carpino", Context.MODE_PRIVATE);
        stc[2] = carpinoPreferences.getString("authorization", "");
        alopeykPreferences = getSharedPreferences("alopeyk", Context.MODE_PRIVATE);
        stc[3] = carpinoPreferences.getString("authorization", "");
        maximPreferences = getSharedPreferences("maxim", Context.MODE_PRIVATE);
        stc[4] = carpinoPreferences.getString("authorization", "");
        new SafeAsyncTask() {

            @Override
            public Object call() throws Exception {
                if (stc[0].matches("") && stc[1].matches("") && stc[2].matches("")) {
                    firstLoad.getToken("all", tmTokensModel.tokenStatus.NOT_SET, "");
                    SharedPreferences.Editor snappEditor = snappPreferences.edit();
                    snappEditor.putString("authorization", firstLoad.getSnappToken())
                            .apply();
                    SharedPreferences.Editor tap30Editor = tap30Preferences.edit();
                    tap30Editor.putString("authorization", firstLoad.getTap30Token())
                            .apply();
                    SharedPreferences.Editor carpinoEditor = carpinoPreferences.edit();
                    carpinoEditor.putString("authorization", firstLoad.getCarpinoToken())
                            .apply();
                    SharedPreferences.Editor alopeykEditor = alopeykPreferences.edit();
                    alopeykEditor.putString("authorization", firstLoad.getAlopeykToken())
                            .apply();
                    SharedPreferences.Editor maximEditor = maximPreferences.edit();
                    maximEditor.putString("authorization", firstLoad.getMaximToken())
                            .apply();
                }
                return null;
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof RetrofitError)
                    Toast.makeText(AddMapActivity.this, getString(R.string.server_not_responde), Toast.LENGTH_SHORT).show();
            }
        }.execute();

        new SafeAsyncTask() {

            @Override
            public Object call() throws Exception {
                priceOrderList = serviceOrderService.getPriceOrder(new ServiceOrderResponse());
                return null;
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                super.onException(e);
                if (priceOrderList == null) {
                    priceOrderList = new ArrayList<>();
                    priceOrderList.add("1");
                    priceOrderList.add("2");
                    priceOrderList.add("3");
                    priceOrderList.add("4");
                    priceOrderList.add("5");
                    priceOrderList.add("6");
                    priceOrderList.add("7");
                    priceOrderList.add("8");
                    priceOrderList.add("9");
                }
            }
        }.execute();

        dstPrefs = getSharedPreferences("com.mibarim.main", Context.MODE_PRIVATE);
        prefs = getSharedPreferences("taximeter", MODE_PRIVATE);

        userInfo = new UserInfoModel();
        userInfo.setAndroid_id(Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID));
        setFavoriteOnMap();
    }


    public void setFavoriteOnMap() {
        fav_on_map = (LinearLayout) findViewById(R.id.fav_on_map);
        fav_on_map1 = (LinearLayout) findViewById(R.id.fav_on_map1);
        fav_on_map_text1 = (TextView) findViewById(R.id.fav_on_map_text1);
        fav_on_map2 = (LinearLayout) findViewById(R.id.fav_on_map2);
        fav_on_map_text2 = (TextView) findViewById(R.id.fav_on_map_text2);
        fav_on_map3 = (LinearLayout) findViewById(R.id.fav_on_map3);
        fav_on_map_text3 = (TextView) findViewById(R.id.fav_on_map_text3);
        fav_on_map4 = (LinearLayout) findViewById(R.id.fav_on_map4);
        fav_on_map_text4 = (TextView) findViewById(R.id.fav_on_map_text4);
        fav_on_map5 = (LinearLayout) findViewById(R.id.fav_on_map5);
        fav_on_map_text5 = (TextView) findViewById(R.id.fav_on_map_text5);
        db = new DataBaseFav(this);
        items = db.getAllItems();
        switch (items.size()) {
            case 0:
                fav_on_map.setVisibility(View.GONE);
                break;
            case 1:
                fav_on_map.setVisibility(View.VISIBLE);
                fav_on_map1.setVisibility(View.VISIBLE);
                fav_on_map2.setVisibility(View.GONE);
                fav_on_map3.setVisibility(View.GONE);
                fav_on_map4.setVisibility(View.GONE);
                fav_on_map5.setVisibility(View.GONE);
                fav_on_map_text1.setText(items.get(0).getCardText());
                break;
            case 2:
                fav_on_map.setVisibility(View.VISIBLE);
                fav_on_map1.setVisibility(View.VISIBLE);
                fav_on_map2.setVisibility(View.VISIBLE);
                fav_on_map3.setVisibility(View.GONE);
                fav_on_map4.setVisibility(View.GONE);
                fav_on_map5.setVisibility(View.GONE);
                fav_on_map_text1.setText(items.get(0).getCardText());
                fav_on_map_text2.setText(items.get(1).getCardText());
                break;
            case 3:
                fav_on_map.setVisibility(View.VISIBLE);
                fav_on_map1.setVisibility(View.VISIBLE);
                fav_on_map2.setVisibility(View.VISIBLE);
                fav_on_map3.setVisibility(View.VISIBLE);
                fav_on_map4.setVisibility(View.GONE);
                fav_on_map5.setVisibility(View.GONE);
                fav_on_map_text1.setText(items.get(0).getCardText());
                fav_on_map_text2.setText(items.get(1).getCardText());
                fav_on_map_text3.setText(items.get(2).getCardText());
                break;
            case 4:
                fav_on_map.setVisibility(View.VISIBLE);
                fav_on_map1.setVisibility(View.VISIBLE);
                fav_on_map2.setVisibility(View.VISIBLE);
                fav_on_map3.setVisibility(View.VISIBLE);
                fav_on_map4.setVisibility(View.VISIBLE);
                fav_on_map5.setVisibility(View.GONE);
                fav_on_map_text1.setText(items.get(0).getCardText());
                fav_on_map_text2.setText(items.get(1).getCardText());
                fav_on_map_text3.setText(items.get(2).getCardText());
                fav_on_map_text4.setText(items.get(3).getCardText());
                break;
            case 5:
                fav_on_map.setVisibility(View.VISIBLE);
                fav_on_map1.setVisibility(View.VISIBLE);
                fav_on_map2.setVisibility(View.VISIBLE);
                fav_on_map3.setVisibility(View.VISIBLE);
                fav_on_map4.setVisibility(View.VISIBLE);
                fav_on_map5.setVisibility(View.VISIBLE);
                fav_on_map_text1.setText(items.get(0).getCardText());
                fav_on_map_text2.setText(items.get(1).getCardText());
                fav_on_map_text3.setText(items.get(2).getCardText());
                fav_on_map_text4.setText(items.get(3).getCardText());
                fav_on_map_text5.setText(items.get(4).getCardText());
                break;
            default:
                fav_on_map.setVisibility(View.VISIBLE);
                fav_on_map1.setVisibility(View.VISIBLE);
                fav_on_map2.setVisibility(View.VISIBLE);
                fav_on_map3.setVisibility(View.VISIBLE);
                fav_on_map4.setVisibility(View.VISIBLE);
                fav_on_map5.setVisibility(View.VISIBLE);
                fav_on_map_text1.setText(items.get(0).getCardText());
                fav_on_map_text2.setText(items.get(1).getCardText());
                fav_on_map_text3.setText(items.get(2).getCardText());
                fav_on_map_text4.setText(items.get(3).getCardText());
                fav_on_map_text5.setText(items.get(4).getCardText());
                break;
        }

        fav_on_map1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveMapFragment(items.get(0).getLat(), items.get(0).getLng());
            }
        });
        fav_on_map2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveMapFragment(items.get(1).getLat(), items.get(1).getLng());
            }
        });
        fav_on_map3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveMapFragment(items.get(2).getLat(), items.get(2).getLng());
            }
        });
        fav_on_map4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveMapFragment(items.get(3).getLat(), items.get(3).getLng());
            }
        });
        fav_on_map5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveMapFragment(items.get(4).getLat(), items.get(4).getLng());
            }
        });
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

//        setFavoriteOnMap();
    }

    private void showBackBtn() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (Build.VERSION.SDK_INT >= 17) {
                actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu_forward);
            }
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    private void hideBackBtn() {
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
                        dstPrefs.edit().putString("DstLatitude", null).apply();
                        dstPrefs.edit().putString("DstLongitude", null).apply();
                        setSrcDstStateSelector(AddRouteStates.SelectOriginState);
                        hideBackBtn();
                        fragmentManager.beginTransaction()
                                .replace(R.id.main_container, new MainAddMapFragment())
                                .commitAllowingStateLoss();
                        break;
                    case SelectPriceState:
                        if (!isGettingPrice) {
                            setSrcDstStateSelector(AddRouteStates.SelectDestinationState);
                            fragmentManager.beginTransaction()
                                    .replace(R.id.main_container, new MainAddMapFragment())
                                    .commitAllowingStateLoss();
                            isAlopeykShown = true;
                            isMaximShown = true;
                            isTelephonyShown = true;
                            isMibarimShown = true;
                            isSnappShown = true;
                            isQonqaShown = true;
                            isCarpinoNormalShown = true;
                            isCarpinoVanShown = true;
                            isCarpinoWomenShown = true;
                            isCarpinoVipShown = true;
                            isTouchsiShown = true;
                            isTap30Shown = true;
                            pathPrice = null;
                            snappResponse = null;
                            tap30PathPriceResponse = null;
                            alopeykResponse = null;
                            carpinoResponse = null;
                            tochsiResponse = null;
                            maximResponse = null;
                            qonqaResponse = null;
                        }
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
        boolean isCollapse = false;
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            switch (getSrcDstStateSelector()) {
                case SelectOriginState:
                    finish();
                    break;
                case SelectDestinationState:
                    dstPrefs.edit().putString("DstLatitude", null).apply();
                    dstPrefs.edit().putString("DstLongitude", null).apply();
                    setSrcDstStateSelector(AddRouteStates.SelectOriginState);
                    hideBackBtn();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_container, new MainAddMapFragment())
                            .commitAllowingStateLoss();
                    break;
                case SelectPriceState:
                    Fragment fragment = fragmentManager.findFragmentById(R.id.route_src_dst_fragment);
                    if (fragment instanceof SrcDstFragment)
                        isCollapse = ((SrcDstFragment) fragment).closeBottomSheet();
                    if (!isGettingPrice && isCollapse) {
                        setSrcDstStateSelector(AddRouteStates.SelectDestinationState);
                        fragmentManager.beginTransaction()
                                .replace(R.id.main_container, new MainAddMapFragment())
                                .commitAllowingStateLoss();
                        isAlopeykShown = true;
                        isMaximShown = true;
                        isTelephonyShown = true;
                        isMibarimShown = true;
                        isSnappShown = true;
                        isQonqaShown = true;
                        isCarpinoNormalShown = true;
                        isCarpinoVanShown = true;
                        isCarpinoWomenShown = true;
                        isCarpinoVipShown = true;
                        isTouchsiShown = true;
                        isTap30Shown = true;
                        pathPrice = null;
                        snappResponse = null;
                        tap30PathPriceResponse = null;
                        alopeykResponse = null;
                        carpinoResponse = null;
                        tochsiResponse = null;
                        maximResponse = null;
                        qonqaResponse = null;
                    }

                    break;

                default:
                    this.finishAffinity();
            }
        }
        return isCollapse && super.onKeyUp(keyCode, event);
    }

    @Subscribe
    public void onNetworkErrorEvent(NetworkErrorEvent event) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.network_error)).setPositiveButton("???? ????", dialogClickListener).show();
    }

    public AddRouteStates getSrcDstStateSelector() {
        return stateSelector;
    }

    public void setSrcDstStateSelector(AddRouteStates state) {
        stateSelector = state;
    }

    public String getSrcAddress() {
        return srcAdd;
    }

    public void setSrcAddress(String address) {
        srcAdd = address;
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

    public String getDstAddress() {
        return dstAdd;
    }

    public void setDstAddress(String address) {
        dstAdd = address;
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

    void StartDelayedTask() {
        mHandler.postDelayed(mGettingInfo, getInfoDelay);
    }

    void StopDelayedTask() {
        if (mGettingInfo != null) {
            mHandler.removeCallbacks(mGettingInfo);
        }
    }

    private void SetPathPrice() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_container);

        if (snappResponse != null && !isSnappShown) {
            isSnappShown = true;
            ((MainAddMapFragment) fragment).setSnappPrice(snappResponse.data.getPrices().get(0).getAmount(), 0);
            removeWaitLayout();
            ((MainAddMapFragment) fragment).setSnappPrice(snappResponse.data.getPrices().get(1).getAmount(), 1);
            ((MainAddMapFragment) fragment).setSnappPrice(snappResponse.data.getPrices().get(3).getAmount(), 3);
        }
        if (snappPathPriceResponse != null && !isSnappShown) {
            isSnappShown = true;
            ((MainAddMapFragment) fragment).setSnappPrice(snappPathPriceResponse.SnappServicePrice, 0);
            removeWaitLayout();
            ((MainAddMapFragment) fragment).setSnappPrice(snappPathPriceResponse.SnappRoseServicePrice, 1);
            ((MainAddMapFragment) fragment).setSnappPrice(snappPathPriceResponse.SnappBikeServicePrice, 3);
        }
        if (tap30PathPriceResponse != null && !isTap30Shown) {
            isTap30Shown = true;
            ((MainAddMapFragment) fragment).setTap30Price(tap30PathPriceResponse.Tap30PathPrice);
            removeWaitLayout();
        }
        if (tochsiResponse != null && !isTouchsiShown) {
            isTouchsiShown = true;
            ((MainAddMapFragment) fragment).setTouchsiPrice(tochsiResponse.value.getPrice());
            removeWaitLayout();
        }
        if (carpinoResponse != null) {
            if (carpinoResponse[0] != null && !isCarpinoNormalShown) {
                ((MainAddMapFragment) fragment).setCarpinoPrice(carpinoResponse[0].getPayable(), 0);
                isCarpinoNormalShown = true;
            }
            removeWaitLayout();
            if (carpinoResponse[1] != null && !isCarpinoVanShown) {
                ((MainAddMapFragment) fragment).setCarpinoPrice(carpinoResponse[1].getPayable(), 1);
                isCarpinoVanShown = true;
            }
            if (carpinoResponse[2] != null && !isCarpinoWomenShown) {
                ((MainAddMapFragment) fragment).setCarpinoPrice(carpinoResponse[2].getPayable(), 2);
                isCarpinoWomenShown = true;
            }
            if (carpinoResponse[3] != null && !isCarpinoVipShown) {
                ((MainAddMapFragment) fragment).setCarpinoPrice(carpinoResponse[3].getPayable(), 3);
                isCarpinoVipShown = true;
            }
        }
        if (pathPrice != null && !isMibarimShown) {
            isMibarimShown = true;
            ((MainAddMapFragment) fragment).setMibarimPrice(pathPrice);
            removeWaitLayout();
        }
        if (alopeykResponse != null && !isAlopeykShown) {
            isAlopeykShown = true;
            ((MainAddMapFragment) fragment).setAlopeyk(alopeykResponse.object.getPrice());
            removeWaitLayout();
        }
        if (maximResponse != null && !isMaximShown) {
            isMaximShown = true;
            ((MainAddMapFragment) fragment).setMaxim(maximResponse.get(0).value, 0);
            removeWaitLayout();
            ((MainAddMapFragment) fragment).setMaxim(maximResponse.get(1).value, 1);
            ((MainAddMapFragment) fragment).setMaxim(maximResponse.get(2).value, 2);
            ((MainAddMapFragment) fragment).setMaxim(maximResponse.get(3).value, 3);
        }
        if (qonqaResponse != null && !isQonqaShown) {
            isQonqaShown = true;
            ((MainAddMapFragment) fragment).setQonqa(qonqaResponse.getCost());
        }
        if (pathPrice != null && !isTelephonyShown) {
            isTelephonyShown = true;
            ((MainAddMapFragment) fragment).setPrice(pathPrice);
            removeWaitLayout();
        }
        if (pathPrice == null && snappResponse == null && tap30PathPriceResponse == null && carpinoResponse == null && alopeykResponse == null && maximResponse == null && tochsiResponse == null && qonqaResponse == null && !isGettingPrice) {
            Toast.makeText(this, "خطا در محاسبه", Toast.LENGTH_SHORT).show();
            removeWaitLayout();
        } else if (prefs.getInt("rateApp", 0) >= 0 && prefs.getBoolean("btnClick", true)) {
            prefs.edit().putBoolean("btnClick", false).apply();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setRate();

                }
            }, 6000);
        }
        isGettingPrice = false;

    }

    public void removeWaitLayout() {
        LinearLayout Wait_layout = (LinearLayout) findViewById(R.id.waiting_layout);
        Wait_layout.setVisibility(View.GONE);


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
                        neighborhood = component.long_name + ", ";
                    }
                    if (type.trim().equals("locality")) {
                        locality = component.long_name + ", ";
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
        if (requestCode == FAV_SET && resultCode == RESULT_OK) {
            setFavoriteOnMap();
            if (!(data.getStringExtra("latFav").equals("0") && data.getStringExtra("lngFav").equals("0")))
                MoveMapFragment(data.getStringExtra("latFav"), data.getStringExtra("lngFav"));
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

    public void MoveMapFragment(String lat, String lng) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_container);
        ((MainAddMapFragment) fragment).MoveMap(lat, lng);
    }

    private void getPlaceDetail(final String placeId) {
        final SharedPreferences pref = getSharedPreferences(Constants.Geocoding.GOOGLE_KEYS, MODE_PRIVATE);
        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (getSrcDstStateSelector() == AddRouteStates.SelectOriginState) {
                    DetailPlaceResult response = addressService.getPlaceDetail(placeId, pref.getString(Constants.Geocoding.GOOGLE_AUTOCOMPLETE_AUTH, null));
                    srcLatitude = response.result.geometry.location.lat;
                    srcLongitude = response.result.geometry.location.lng;
                    return true;
                }
                if (getSrcDstStateSelector() == AddRouteStates.SelectDestinationState) {
                    DetailPlaceResult response = addressService.getPlaceDetail(placeId, pref.getString(Constants.Geocoding.GOOGLE_AUTOCOMPLETE_AUTH, null));
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
        if (isNetworkConnected()) {
            switch (getSrcDstStateSelector()) {
                case SelectOriginState:
                    setSrcDstStateSelector(AddRouteStates.SelectDestinationState);
                    showBackBtn();
                    RebuildDstFragment();
//                    prefs.edit().putString("favoriteSet","dst").apply();

//                    if (prefs.getInt("rateApp", 1) > 0) {
//                        setRate();
//
//                    }
                    break;
                case SelectDestinationState:
                    setSrcDstStateSelector(AddRouteStates.SelectPriceState);
                    RebuildDstFragment();
                case SelectPriceState:
                    SetWaitState();
                    startFetchingData();
                    prefs.edit().putBoolean("btnClick", true).apply();
                    ImageView fav_place = (ImageView) findViewById(R.id.fav_place);
                    fav_place.setVisibility(View.GONE);
                    LinearLayout favmap = (LinearLayout) findViewById(R.id.fav_on_map);
                    favmap.setVisibility(View.GONE);
//                    prefs.edit().putString("favoriteSet","src").apply();
                    //Adad.showInterstitialAd(this);
                    //returnOk();
                    break;
            }
        } else
            Toast.makeText(this, "اتصال خود به اینترنت را چک کنید", Toast.LENGTH_SHORT).show();
    }

    public int fibonacci(int number) {
        int a = 1, b = 1, c = 1;
        while (number != c) {
            a = b;
            b = c;
            c = a + b;

        }

        return c + b;
    }

    public void setRate() {

        Log.i("Rate", prefs.getInt("rateApp", 0) + "");
        prefs.edit().putInt("rateApp", (prefs.getInt("rateApp", 0) + 1)).apply();
        if (prefs.getInt("fibo", 3) == prefs.getInt("rateApp", 0)) {


            final ratingApp dialog = new ratingApp(this);
            dialog.setCancelable(false);
            dialog.show();
            dialog.neverRate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    prefs.edit().putInt("rateApp", -1).apply();
                    dialog.dismiss();
                }
            });
            dialog.yesRate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prefs.edit().putInt("rateApp", -1).apply();
                    dialog.dismiss();


                    String marketRate = getString(R.string.rate_link);

                    switch (marketRate) {
                        case "bazaar://details?id=com.mibarim.taximeter": {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(marketRate));
                            intent.setPackage("com.farsitel.bazaar");
                            try {
                                startActivity(intent);
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "بازار بر روی گوشی شما نصب نیست", Toast.LENGTH_LONG).show();
                            }
                            break;
                        }
                        case "myket://comment?id=com.mibarim.taximeter": {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(marketRate));
                            try {
                                startActivity(intent);
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "مایکت بر روی گوشی شما نصب نیست", Toast.LENGTH_LONG).show();
                            }
                            break;
                        }
                        case "http://iranapps.ir/app/com.mibarim.taximeter": {
                            Uri uri = Uri.parse(marketRate); // missing 'http://' will cause crashed
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            try {
                                startActivity(intent);
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "ایران اپس بر روی گوشی شما نصب نیست", Toast.LENGTH_LONG).show();
                            }
                            break;
                        }

                    }
                }
            });
            dialog.noRate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prefs.edit().putInt("rateApp", 0).apply();
                    prefs.edit().putInt("fibo", fibonacci(prefs.getInt("fibo", 3))).apply();
                    dialog.dismiss();

                }
            });

        }


    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private void startFetchingData() {
        isGettingPrice = true;
        isTelephonyShown = false;
        isMibarimShown = false;
        isMaximShown = false;
        isSnappShown = false;
        isTap30Shown = false;
        isQonqaShown = false;
        isCarpinoNormalShown = false;
        isCarpinoVanShown = false;
        isCarpinoWomenShown = false;
        isCarpinoVipShown = false;
        isAlopeykShown = false;
        isTouchsiShown = false;
        getPathPrice();
        getPathPriceSnapp(true);
        getPathPriceTap30_2();
        getPathPriceCarpino(true, "NORMAL");
        getPathPriceCarpino(true, "VAN");
        getPathPriceCarpino(true, "WOMEN");
        getPathPriceCarpino(true, "VIP");
        getPathPriceAlopeyk(true);
        getPathPriceMaxim(true);
        getPathPriceTouchsi(true);
        getPathPriceQonqa(true);
    }

    private void returnOk() {
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
    }

    public void gotoFavorite() {
        Intent intent = new Intent(AddMapActivity.this, favorite_place.class);
        startActivityForResult(intent, FAV_SET);
    }

    public void gotoLocationActivity() {
        Intent intent = new Intent(this, LocationSearchActivity.class);
        intent.putExtra("checkActivity", 0);
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
            AlertDialogTheme alertDialog = new AlertDialogTheme(AddMapActivity.this);
            alertDialog.show();
        }
    }

    public boolean isTabriz() {
        double srcLat = Double.parseDouble(srcLatitude);
        double srcLng = Double.parseDouble(srcLongitude);
        double dstLat = Double.parseDouble(dstLatitude);
        double dstLng = Double.parseDouble(dstLongitude);
        if (srcLat < 38.188843 && srcLat > 37.988124 && srcLng < 46.499364 && srcLng > 46.091843)
            if (dstLat < 38.188843 && dstLat > 37.988124 && dstLng < 46.499364 && dstLng > 46.091843)
                return true;
            else
                return false;
        else
            return false;
    }

    private void getPathPrice() {
        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                ApiResponse response = priceService.GetPathPrice(srcLatitude, srcLongitude, dstLatitude, dstLongitude, userInfo.getAndroid_id());
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
                SetPathPrice();
                super.onFinally();
            }
        }.execute();
    }

    public void getPathPriceSnapp(final boolean tryAgainForAuthorize) {
        SharedPreferences sharedPreferences = getSharedPreferences("snappAuth", Context.MODE_PRIVATE);
        final String authorization = sharedPreferences.getString("authorization", "");

        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                snappResponse = priceService.getPathPriceSnapp(srcLatitude, srcLongitude, dstLatitude, dstLongitude, authorization);
                return true;
            }

            @Override
            protected void onFinally() throws RuntimeException {
                SetPathPrice();
                super.onFinally();
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof RetrofitError) {
                    if (tryAgainForAuthorize) {
                        refreshAuthorizationKeySnapp(new Callback() {
                            @Override
                            public void dosth() {
                                getPathPriceSnapp(false);
                            }
                        }, authorization);
                    } else {
                        getSnappPrice_Server();
                    }
                }
            }

            @Override
            protected void onSuccess(final Boolean res) throws Exception {
                super.onSuccess(res);
                SetPathPrice();
            }
        }.execute();

    }

    public void getPathPriceTap30(final boolean tryAgainForAuthorize) {

        SharedPreferences sharedPreferences = getSharedPreferences("tap30Auth", Context.MODE_PRIVATE);
        final String authorization = sharedPreferences.getString("authorization", "");


        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {

                return true;
            }

            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof RetrofitError) {
                    if (tryAgainForAuthorize) {
                        refreshAuthorizationKeyTap30(new Callback() {
                            @Override
                            public void dosth() {
                                getPathPriceTap30(false);
                            }
                        }, authorization);
                    }
                }
            }

            @Override
            protected void onFinally() throws RuntimeException {
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

    private void getSnappPrice_Server() {
        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {

                snappPathPriceResponse = priceService.GetSnappPriceFromServer(srcLatitude, srcLongitude, dstLatitude, dstLongitude);
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
                SetPathPrice();
            }

            @Override
            protected void onFinally() throws RuntimeException {
                SetPathPrice();
                super.onFinally();
            }
        }.execute();
    }


    private void getPathPriceTap30_2() {
        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {

                tap30PathPriceResponse = priceService.GetTap30PriceFromServer(srcLatitude, srcLongitude, dstLatitude, dstLongitude);
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
                SetPathPrice();
            }

            @Override
            protected void onFinally() throws RuntimeException {
                SetPathPrice();
                super.onFinally();
            }
        }.execute();
    }


    public void getPathPriceCarpino(final boolean tryAgainForAuthorize, final String serviceType) {

        SharedPreferences sharedPreferences = getSharedPreferences("carpino", Context.MODE_PRIVATE);
        final String authorization = sharedPreferences.getString("authorization", "");

        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (serviceType.matches("NORMAL"))
                    carpinoResponse[0] = priceService.getPathPriceCarpino(srcLatitude, srcLongitude, dstLatitude, dstLongitude, serviceType, authorization);
                else if (serviceType.matches("VAN"))
                    carpinoResponse[1] = priceService.getPathPriceCarpino(srcLatitude, srcLongitude, dstLatitude, dstLongitude, serviceType, authorization);
                else if (serviceType.matches("WOMEN"))
                    carpinoResponse[2] = priceService.getPathPriceCarpino(srcLatitude, srcLongitude, dstLatitude, dstLongitude, serviceType, authorization);
                else if (serviceType.matches("VIP"))
                    carpinoResponse[3] = priceService.getPathPriceCarpino(srcLatitude, srcLongitude, dstLatitude, dstLongitude, serviceType, authorization);
                return true;
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);

                if (e instanceof RetrofitError && e.getMessage().matches("timeout")) {
                    getPathPriceCarpino(false, serviceType);
                } else if (e instanceof RetrofitError && ((RetrofitError) e).getResponse().getStatus() == 500)
                    getPathPriceCarpino(false, serviceType);
                else if (e instanceof RetrofitError) {
                    if (tryAgainForAuthorize) {
                        refreshAuthorizationKeyCarpino(new Callback() {
                            @Override
                            public void dosth() {
                                getPathPriceCarpino(false, serviceType);
                            }
                        }, authorization);
                    }
                }
            }

            @Override
            protected void onFinally() throws RuntimeException {
                SetPathPrice();
                super.onFinally();
            }

            @Override
            protected void onSuccess(final Boolean res) throws Exception {
                super.onSuccess(res);
            }

        }.execute();
    }

    public void getPathPriceAlopeyk(final boolean tryAgainForAuthorize) {

        SharedPreferences sharedPreferences = getSharedPreferences("alopeyk", Context.MODE_PRIVATE);
        final String authorization = sharedPreferences.getString("authorization", "");

        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                alopeykResponse = priceService.getPathPriceAlopeyk(srcLatitude, srcLongitude, dstLatitude, dstLongitude, authorization);
                return true;
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                super.onException(e);

                if (e instanceof RetrofitError) {
                    if (tryAgainForAuthorize) {
                        refreshAuthorizationKeyAlopeyk(new Callback() {
                            @Override
                            public void dosth() {
                                getPathPriceAlopeyk(false);
                            }
                        }, authorization);
                    }
                }
            }

            @Override
            protected void onFinally() throws RuntimeException {
                SetPathPrice();
                super.onFinally();
            }

            @Override
            protected void onSuccess(final Boolean res) throws Exception {
                super.onSuccess(res);
            }

        }.execute();
    }

    public void getPathPriceMaxim(final boolean tryAgainForAuthorize) {

        SharedPreferences sharedPreferences = getSharedPreferences("maxim", Context.MODE_PRIVATE);
        final String authorization = sharedPreferences.getString("authorization", "");

        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                maximResponse = priceService.getPathPriceMaxim(srcLatitude, srcLongitude, dstLatitude, dstLongitude, authorization);
                return true;
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                super.onException(e);

                if (e instanceof RetrofitError) {
                    if (tryAgainForAuthorize) {
                        refreshAuthorizationKeyMaxim(new Callback() {
                            @Override
                            public void dosth() {
                                getPathPriceMaxim(false);
                            }
                        }, authorization);
                    }
                }
            }

            @Override
            protected void onFinally() throws RuntimeException {
                SetPathPrice();
                super.onFinally();
            }

        }.execute();
    }

    public void getPathPriceTouchsi(final boolean tryAgainForAuthorize) {

        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                tochsiResponse = priceService.getPathPriceTochsi(srcLatitude, srcLongitude, dstLatitude, dstLongitude);
                return true;
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                super.onException(e);
            }

            @Override
            protected void onFinally() throws RuntimeException {
                SetPathPrice();
                super.onFinally();
            }

            @Override
            protected void onSuccess(final Boolean res) throws Exception {
                super.onSuccess(res);

            }

        }.execute();
    }

    public void getPathPriceQonqa(final boolean tryAgainForAuthorize) {

        SharedPreferences sharedPreferences = getSharedPreferences("qonqa", Context.MODE_PRIVATE);
        final String authorization = sharedPreferences.getString("authorization", "");

        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (isTabriz())
                    qonqaResponse = priceService.getPathPriceQonqa(srcLatitude, srcLongitude, dstLatitude, dstLongitude, authorization);
                return true;
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                super.onException(e);

                if (e instanceof RetrofitError) {
                    if (tryAgainForAuthorize) {
                        refreshAuthorizationKeyQonqa(new Callback() {
                            @Override
                            public void dosth() {
                                getPathPriceQonqa(false);
                            }
                        }, authorization);
                    }
                }
            }

            @Override
            protected void onFinally() throws RuntimeException {
                SetPathPrice();
                super.onFinally();
            }

        }.execute();
    }

    private void refreshAuthorizationKeySnapp(final Callback callback, final String authorization) {
        refreshingTokens = true;
        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                String snapp = priceService.snappUnauthorizationint(authorization);
                SharedPreferences.Editor editor = getSharedPreferences("snappAuth", Context.MODE_PRIVATE).edit();
                editor.putString("authorization", snapp);
                editor.apply();
                callback.dosth();
                return true;
            }
        }.execute();
    }

    private void refreshAuthorizationKeyTap30(final Callback callback, final String authorization) {
        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                String tap30 = priceService.tap30Unauthorizationint(authorization);
                SharedPreferences.Editor editor = getSharedPreferences("tap30Auth", Context.MODE_PRIVATE).edit();
                editor.putString("authorization", tap30);
                editor.apply();
                callback.dosth();
                return true;
            }
        }.execute();
    }

    private void refreshAuthorizationKeyCarpino(final Callback callback, final String authorization) {
        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                String carpino = priceService.carpinoUnauthorizationint(authorization);
                SharedPreferences.Editor editor = getSharedPreferences("carpino", Context.MODE_PRIVATE).edit();
                editor.putString("authorization", carpino);
                editor.apply();
                callback.dosth();
                return true;
            }
        }.execute();

    }

    private void refreshAuthorizationKeyAlopeyk(final Callback callback, final String authorization) {
        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                String alopeyk = priceService.alopeykUnauthorizationint(authorization);
                SharedPreferences.Editor editor = getSharedPreferences("alopeyk", Context.MODE_PRIVATE).edit();
                editor.putString("authorization", alopeyk);
                editor.apply();
                callback.dosth();
                return true;
            }
        }.execute();

    }

    private void refreshAuthorizationKeyMaxim(final Callback callback, final String authorization) {
        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                String maxim = priceService.maximUnauthorizationint(authorization);
                SharedPreferences.Editor editor = getSharedPreferences("maxim", Context.MODE_PRIVATE).edit();
                editor.putString("authorization", maxim);
                editor.apply();
                callback.dosth();
                return true;
            }
        }.execute();

    }

    private void refreshAuthorizationKeyQonqa(final Callback callback, final String authorization) {
        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                String qonqa = priceService.qonqaUnauthorizationint(authorization);
                SharedPreferences.Editor editor = getSharedPreferences("maxim", Context.MODE_PRIVATE).edit();
                editor.putString("authorization", qonqa);
                editor.apply();
                callback.dosth();
                return true;
            }
        }.execute();

    }

    private interface Callback {
        void dosth();
    }


}
