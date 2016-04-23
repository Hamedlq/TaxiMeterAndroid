

package com.mibarim.main.activities;


import android.accounts.OperationCanceledException;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.crashlytics.android.Crashlytics;

import com.mibarim.main.BootstrapApplication;
import com.mibarim.main.BootstrapServiceProvider;
import com.mibarim.main.R;
import com.mibarim.main.authenticator.LogoutService;
import com.mibarim.main.models.Address.AddressComponent;
import com.mibarim.main.models.Address.AddressObject;
import com.mibarim.main.models.Address.AddressResult;
import com.mibarim.main.models.ApiResponse;
import com.mibarim.main.models.CarInfoModel;
import com.mibarim.main.models.ConfirmationModel;
import com.mibarim.main.models.LicenseInfoModel;
import com.mibarim.main.models.PersonalInfoModel;
import com.mibarim.main.models.RouteRequest;
import com.mibarim.main.services.AddressService;
import com.mibarim.main.services.AuthenticateService;
import com.mibarim.main.services.RouteRequestService;
import com.mibarim.main.ui.BootstrapActivity;
import com.mibarim.main.ui.HandleApiMessages;
import com.mibarim.main.ui.fragments.addRouteFragments.AddMapFragment;
import com.mibarim.main.ui.fragments.MainAddMapFragment;
import com.mibarim.main.ui.fragments.addRouteFragments.SrcDstAddFragment;
import com.mibarim.main.util.SafeAsyncTask;
import com.mibarim.main.util.Toaster;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;


/**
 * Initial activity for the application.
 * * <p/>
 * If you need to remove the authentication from the application please see
 */
public class AddMapActivity extends BootstrapActivity implements AddMapFragment.OnMapClickedListener {

    @Inject
    BootstrapServiceProvider serviceProvider;
    @Inject
    AddressService addressService;
    @Inject
    RouteRequestService routeRequestService;
    @Inject
    LogoutService getLogoutService;

    private PersonalInfoModel personalInfoModel;
    private CarInfoModel carInfoModel;
    private LicenseInfoModel licenseInfoModel;

    private static final String PERSONAL_INFO = "personalInfo";
    private static final String LICENSE_INFO = "licenseInfo";
    private static final String CAR_INFO = "carInfo";

    private CharSequence title;
    private Toolbar toolbar;

    private ApiResponse confirmResult;

    private String srcAdd;
    private String dstAdd;

    private String srcAddDetail;
    private String dstAddDetail;

    public String srcBriefAdd;
    public String dstBriefAdd;

    public RouteRequest routeRequest;

    private String srcLatitude;
    private String srcLongitude;
    private String dstLatitude;
    private String dstLongitude;
    private List<Point> wayPoints;

    private List<String> prices;

    private ApiResponse response;

    private String authToken;

    private String routeIds;
    private String confirmationMsg;


    //private TrafficAddressResponse trafficAddress;
    private String trafficAddress;
/*
    private TaxiPriceModel taxiPriceModel;

    private List<RoutePriceModel> routePriceModel;
*/

//    public boolean dstShow=false;
//    public boolean calcShow=false;


    protected String srcDstStateSelector;//SOURCE_SELECTED DESTINATION_SELECTED WAY_POINT_SELECTED

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        BootstrapApplication.component().inject(this);

        setContentView(R.layout.add_map_activity);

        // View injection with Butterknife
        ButterKnife.bind(this);

        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        if (getIntent() != null && getIntent().getExtras() != null) {
            personalInfoModel = (PersonalInfoModel) getIntent().getExtras().getSerializable(PERSONAL_INFO);
            carInfoModel = (CarInfoModel) getIntent().getExtras().getSerializable(CAR_INFO);
            licenseInfoModel = (LicenseInfoModel) getIntent().getExtras().getSerializable(LICENSE_INFO);
        }
        // Set up navigation drawer
        title = getTitle();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu_forward);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        srcDstStateSelector = "SOURCE_SELECTED";
        wayPoints = new ArrayList<Point>();
        routeRequest = new RouteRequest();
        checkAuth();
        //initScreen();
    }


    private void initScreen() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.add_map_container, new MainAddMapFragment())
                .commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_route_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finishAddRoute();
                return true;
            case R.id.save_route_btn:
                saveRouteRequest();
                //navigateToAddRoute();
                return true;
//            case R.id.timer:
            //navigateToContactUs();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void checkAuth() {
        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                final AuthenticateService svc = serviceProvider.getService(AddMapActivity.this);
                return svc != null;
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof OperationCanceledException) {
                    // User cancelled the authentication process (back button, etc).
                    // Since auth could not take place, lets finish this activity.
                    finish();
                }
            }

            @Override
            protected void onSuccess(final Boolean hasAuthenticated) throws Exception {
                super.onSuccess(hasAuthenticated);
                //userHasAuthenticated = true;
                initScreen();
            }
        }.execute();
    }

/*
    private void navigateToContactUs() {
        final Intent i = new Intent(this, ContactUsActivity.class);
        startActivity(i);
    }
*/

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    public void setSrcDstStateSelector(String state) {
        srcDstStateSelector = state;
    }

    public String getSrcDstStateSelector() {
        return srcDstStateSelector;
    }

    public void setSrcAddress(String address) {
        srcAdd = address;
        routeRequest.SrcGAddress = address;
    }

    public String getSrcAddress() {
        return srcAdd;
    }

    public void setDstAddress(String address) {
        dstAdd = address;
        routeRequest.DstGAddress = address;
    }

    public String getDstAddress() {
        return dstAdd;
    }


    @Override
    public void onMapClicked(String SrcDstStateSelect) {
        if (SrcDstStateSelect == "DESTINATION_SELECTED" || SrcDstStateSelect == "SOURCE_SELECTED") {
            RebuildSrcDstFragment();
        }
    }

    private void RebuildSrcDstFragment() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.srcdst_fragment, new SrcDstAddFragment())
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onSrcClicked(String latitude, String longitude) {
        srcLatitude = latitude;
        srcLongitude = longitude;
        getAddress("SOURCE");
    }


    @Override
    public void onDstClicked(String latitude, String longitude) {
        dstLatitude = latitude;
        dstLongitude = longitude;
        getAddress("DESTINATION");
    }

    @Override
    public void onPointClicked(String latitude, String longitude) {
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
                RebuildSrcDstFragment();
            }
        }.execute();
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


    private void logout() {
        getLogoutService.logout(new Runnable() {
            @Override
            public void run() {
                // Calling a refresh will force the service to look for a logged in user
                // and when it finds none the user will be requested to log in again.
                //forceRefresh();
            }
        });
        ReloadActivity();
    }

    private void ReloadActivity() {
        if (Build.VERSION.SDK_INT >= 11) {
            recreate();
        } else {
            Intent intent = getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            overridePendingTransition(0, 0);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

    private void saveRouteRequest() {
        showProgress();
        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                //final AuthenticateService svc = serviceProvider.getService(AddMapActivity.this);
                //if(svc != null){
                authToken = serviceProvider.getAuthToken(AddMapActivity.this);
                response = routeRequestService.SubmitNewRoute(authToken, routeRequest);
                if ((response.Errors == null || response.Errors.size() == 0) && response.Status.equals("OK")) {
                    return true;
                }
                //}
                return false;
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof OperationCanceledException) {
                    // User cancelled the authentication process (back button, etc).
                    // Since auth could not take place, lets finish this activity.
                    finish();
                }
                hideProgress();
            }

            @Override
            protected void onSuccess(final Boolean isRouteSubmitted) throws Exception {
                super.onSuccess(isRouteSubmitted);
                hideProgress();
                if (isRouteSubmitted) {
                    routeIds = response.Messages.get(0);
                    confirmationMsg = response.Messages.get(1);
                    showConfirmPanel();
                } else {
                    new HandleApiMessages(AddMapActivity.this, response).showMessages();
                }
                //TODO: gotoMyRoutesActivity
            }
        }.execute();
    }

    private void showConfirmPanel() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.add_map_container);
        ((MainAddMapFragment) fragment).showMesagePanel();
    }

    public String getConfirmMessage() {
        return confirmationMsg;
    }

    public void confirmRoute() {
        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                //final AuthenticateService svc = serviceProvider.getService(AddMapActivity.this);
                //if(svc != null){
                if (authToken == null) {
                    authToken = serviceProvider.getAuthToken(AddMapActivity.this);
                }
                ConfirmationModel confirmationModel = new ConfirmationModel();
                confirmationModel.Ids = routeIds;
                confirmationModel.ConfirmedText = confirmationMsg;
                confirmResult = routeRequestService.ConfirmRoute(authToken, confirmationModel);
                if ((confirmResult.Errors == null || confirmResult.Errors.size() == 0) && confirmResult.Status.equals("OK")) {
                    return true;
                }
                //}
                return false;
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof OperationCanceledException) {
                    // User cancelled the authentication process (back button, etc).
                    // Since auth could not take place, lets finish this activity.
                    finish();
                }
            }

            @Override
            protected void onSuccess(final Boolean isRouteSubmitted) throws Exception {
                super.onSuccess(isRouteSubmitted);
                if (isRouteSubmitted) {
                    if (confirmResult != null && confirmResult.Messages != null && confirmResult.Messages.size() > 0) {
                        Toaster.showLong(AddMapActivity.this, confirmResult.Messages.get(0), R.drawable.toast_success);
                    }
                    finishAddRoute();
                } else {
                    new HandleApiMessages(AddMapActivity.this, confirmResult).showMessages();
                }

            }
        }.execute();
    }

    private void finishAddRoute() {
        setResult(RESULT_OK);
        finish();

    }

    /*private List<String> purifyBriefAddress(AddressResult response) {
        String route;
        //String neighborhood;
        List<String> result = new ArrayList<String>();
        for (AddressObject address : response.results) {
            route = "";
            //neighborhood = "";
            for (AddressComponent component : address.address_components) {
                for (String type : component.types) {
                    if (type.trim().equals("route")) {
                        route = component.long_name;
                    }
                    *//*if (type.trim().equals("neighborhood")) {
                        neighborhood = component.long_name + "، ";
                    }*//*
                }
            }
            result.add(route);
        }
        return result;
    }*/


/*
    public void getGRouteFromServer() {
        showProgress();
        getGoogleRouteFromServer();
        //getResultFromTrafficServer();
    }
*/

    /*private void getGoogleRouteFromServer() {
        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                routePriceModel=new ArrayList<RoutePriceModel>();
                routePriceModel = grouteService.getRoute(srcLatitude, srcLongitude, dstLatitude, dstLongitude, wayPoints);
                if (routePriceModel.size() > 0 && routePriceModel.get(0).Path.path.size() > 0) {
                    return true;
                }
                return false;
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);
                hideProgress();
                FinishCalculate();
                Toaster.showLong(AddMapActivity.this, R.string.message_timeout);
            }

            @Override
            protected void onSuccess(final Boolean res) throws Exception {
                super.onSuccess(res);
                hideProgress();
                FinishCalculate();
                if (res) {
                    showWayPointPanel();
                    ShowTaxiRoute();
                } else {
                    Toaster.showLong(AddMapActivity.this, R.string.error_occured);
                }
            }
        }.execute();


    }

    private void showWayPointPanel() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.add_map_container);
        ((MainAddMapFragment) fragment).showWayPointPanel();
    }*/

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
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(final DialogInterface dialog) {
            }
        });
        return dialog;
    }

    public void showForms() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.add_map_container);
        ((MainAddMapFragment) fragment).showFormPanel();
    }

    public void deleteNotConfirmedRoute() {
        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (authToken == null) {
                    authToken = serviceProvider.getAuthToken(AddMapActivity.this);
                }
                ConfirmationModel confirmationModel = new ConfirmationModel();
                confirmationModel.Ids = routeIds;
                confirmResult = routeRequestService.notConfirmRoute(authToken, confirmationModel);
                if ((confirmResult.Errors == null || confirmResult.Errors.size() == 0) && confirmResult.Status.equals("OK")) {
                    return true;
                }
                //}
                return false;
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof OperationCanceledException) {
                    // User cancelled the authentication process (back button, etc).
                    // Since auth could not take place, lets finish this activity.
                    finish();
                }
            }

            @Override
            protected void onSuccess(final Boolean isRouteSubmitted) throws Exception {
                super.onSuccess(isRouteSubmitted);
                if (isRouteSubmitted) {
                    finishAddRoute();

                } else {
                    new HandleApiMessages(AddMapActivity.this, confirmResult).showMessages();
                }

            }
        }.execute();
    }

    public void showUserPersonalActivity() {
        if (personalInfoModel== null ||personalInfoModel.Base64UserPic == null || personalInfoModel.Base64UserPic.equals("")
                ||personalInfoModel.Name == null || personalInfoModel.Name.equals("")
                ||personalInfoModel.Family == null || personalInfoModel.Family.equals("")) {
            final Intent i = new Intent(this, UserPersonalActivity.class);
            i.putExtra(PERSONAL_INFO, personalInfoModel);
            startActivity(i);
        }
    }

    public void NotConfirmedRoute() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.message_frgment);
        fragmentManager.beginTransaction()
                .remove(fragment)
                .commit();
    }

}
