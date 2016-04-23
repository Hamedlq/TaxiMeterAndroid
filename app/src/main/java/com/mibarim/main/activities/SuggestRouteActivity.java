

package com.mibarim.main.activities;


import android.accounts.OperationCanceledException;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.mibarim.main.BootstrapApplication;
import com.mibarim.main.BootstrapServiceProvider;
import com.mibarim.main.R;
import com.mibarim.main.authenticator.LogoutService;
import com.mibarim.main.events.NetworkErrorEvent;
import com.mibarim.main.events.RestAdapterErrorEvent;
import com.mibarim.main.models.Address.Location;
import com.mibarim.main.models.ApiResponse;
import com.mibarim.main.models.PersonalInfoModel;
import com.mibarim.main.models.Route.BriefRouteModel;
import com.mibarim.main.models.Route.RouteResponse;
import com.mibarim.main.services.RouteRequestService;
import com.mibarim.main.services.RouteResponseService;
import com.mibarim.main.ui.BootstrapActivity;
import com.mibarim.main.ui.HandleApiMessages;
import com.mibarim.main.ui.fragments.MainGroupFragment;
import com.mibarim.main.ui.fragments.MapFragment;
import com.mibarim.main.ui.fragments.routeFragments.GroupFragment;
import com.mibarim.main.util.SafeAsyncTask;
import com.mibarim.main.util.Toaster;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;


/**
 * Initial activity for the application.
 * * <p/>
 * If you need to remove the authentication from the application please see
 */
public class SuggestRouteActivity extends BootstrapActivity {

    @Inject
    protected BootstrapServiceProvider serviceProvider;
    @Inject
    RouteRequestService routeRequestService;
    @Inject
    LogoutService getLogoutService;
    @Inject
    RouteResponseService routeResponseService;


    private CharSequence title;
    private Toolbar toolbar;

    private String authToken;
    private ApiResponse response;

    private ApiResponse theSuggestRoute;
    private RouteResponse selfRoute;
    private BriefRouteModel selectedRoute;

    protected Bitmap result;//concurrency must be considered

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        BootstrapApplication.component().inject(this);

        setContentView(R.layout.route_activity);

        // View injection with Butterknife
        ButterKnife.bind(this);

        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        if (getIntent() != null && getIntent().getExtras() != null) {
            theSuggestRoute = (ApiResponse) getIntent().getExtras().getSerializable("SuggestRoutes");
            selfRoute = (RouteResponse) getIntent().getExtras().getSerializable("RouteResponse");
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu_forward);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        initScreen();
    }

    private void initScreen() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.route_container, new MainGroupFragment())
                .commit();

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Subscribe
    public void onNetworkErrorEvent(NetworkErrorEvent event) {
        Toaster.showLong(SuggestRouteActivity.this, getString(R.string.network_error), R.drawable.toast_warn);
    }

    @Subscribe
    public void onRestAdapterErrorEvent(RestAdapterErrorEvent event) {
        Toaster.showLong(SuggestRouteActivity.this, getString(R.string.network_error), R.drawable.toast_warn);
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
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(final DialogInterface dialog) {
            }
        });
        return dialog;
    }

    public Bitmap getRouteImage(final long routeId, final String requestedFragment) {
        new SafeAsyncTask<Boolean>() {
            Bitmap decodedByte;

            @Override
            public Boolean call() throws Exception {
                authToken = serviceProvider.getAuthToken(SuggestRouteActivity.this);
                PersonalInfoModel res = routeResponseService.GetRouteImage(authToken, routeId);
                if (res != null && res.Base64UserPic != null) {
                    byte[] decodedString = Base64.decode(res.Base64UserPic, Base64.DEFAULT);
                    decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    return true;
                }
                return false;
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof OperationCanceledException) {
                    // User cancelled the authentication process (back button, etc).
                    // Since auth could not take place, lets finish this activity.
//                    finish();
                }
            }

            @Override
            protected void onSuccess(final Boolean imageLoaded) throws Exception {
                if (imageLoaded) {
                    setImage(routeId, requestedFragment, decodedByte);
                }
            }
        }.execute();
        return result;
    }

    private void setImage(long routeId, String requestedFragment, Bitmap decodedByte) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        switch (requestedFragment) {
            case "GroupFragment":
                Fragment fragment = fragmentManager.findFragmentById(R.id.group_fragment);
                ((GroupFragment) fragment).setRouteImage(routeId, decodedByte);

                break;
        }
    }


    public void getRouteImage(final ImageView imageView, final int routeId) {
        new SafeAsyncTask<Boolean>() {
            Bitmap decodedByte;

            @Override
            public Boolean call() throws Exception {
                String token = serviceProvider.getAuthToken(SuggestRouteActivity.this);
                PersonalInfoModel res = routeResponseService.GetRouteImage(token, routeId);
                if (res != null && res.Base64UserPic != null) {
                    byte[] decodedString = Base64.decode(res.Base64UserPic, Base64.DEFAULT);
                    decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    return true;
                }
                return false;
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof android.os.OperationCanceledException) {
                    // User cancelled the authentication process (back button, etc).
                    // Since auth could not take place, lets finish this activity.
//                    finish();
                }
            }

            @Override
            protected void onSuccess(final Boolean imageLoaded) throws Exception {
                if (imageLoaded) {
                    imageView.setImageBitmap(decodedByte);
                }
            }
        }.execute();
    }

    public Location getRouteSource() {
        Location loc = new Location();
        if (selfRoute != null) {
            loc.lat = selfRoute.SrcLatitude;
            loc.lng = selfRoute.SrcLongitude;

        }
        return loc;
    }

    public Location getRouteDestination() {
        Location loc = new Location();
        if (selfRoute != null) {
            loc.lat = selfRoute.DstLatitude;
            loc.lng = selfRoute.DstLongitude;

        }
        return loc;
    }

    public void setRouteSrcDst(String srcLat, String srcLng, String dstLat, String dstLng, int position) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.map_fragment);
        ((MapFragment) fragment).setRoute(srcLat, srcLng, dstLat, dstLng, position % 4);

    }

    public ApiResponse getSuggestRoutes() {
        return theSuggestRoute;
    }

    public void AcceptSuggestRoute() {
        if (selectedRoute == null || selectedRoute.RouteId == 0) {
            Toaster.showLong(SuggestRouteActivity.this, R.string.please_select);
        } else {
            new SafeAsyncTask<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    if (authToken == null) {
                        authToken = serviceProvider.getAuthToken(SuggestRouteActivity.this);
                    }
                    response = routeRequestService.acceptSuggestion(authToken, String.valueOf(selectedRoute.RouteId),
                            String.valueOf(selfRoute.RouteId));
                    if ((response.Errors == null || response.Errors.size() == 0) && response.Status.equals("OK")) {
                        return true;
                    }
                    return false;
                }

                @Override
                protected void onException(final Exception e) throws RuntimeException {
                    super.onException(e);
                    if (e instanceof android.os.OperationCanceledException) {
                        // User cancelled the authentication process (back button, etc).
                        // Since auth could not take place, lets finish this activity.
//                    finish();
                    }
                }

                @Override
                protected void onSuccess(final Boolean succees) throws Exception {
                    if (!succees) {
                        new HandleApiMessages(SuggestRouteActivity.this, response).showMessages();
                    } else {
                        finishIt();
                    }

                }
            }.execute();
        }
    }

    private void finishIt() {
        final Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    public void setSelectedRoute(BriefRouteModel selectedItem) {
        selectedRoute = selectedItem;
    }

    public void DeleteRoute() {
        if (selectedRoute == null || selectedRoute.RouteId == 0) {
            Toaster.showLong(SuggestRouteActivity.this, R.string.please_select);
        } else {
            new SafeAsyncTask<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    if (authToken == null) {
                        authToken = serviceProvider.getAuthToken(SuggestRouteActivity.this);
                    }
                    response = routeRequestService.deleteRouteSuggestion(authToken, String.valueOf(selectedRoute.RouteId),
                            String.valueOf(selfRoute.RouteId));
                    if ((response.Errors == null || response.Errors.size() == 0) && response.Status.equals("OK")) {
                        return true;
                    }
                    return false;
                }

                @Override
                protected void onException(final Exception e) throws RuntimeException {
                    super.onException(e);
                    if (e instanceof android.os.OperationCanceledException) {
                        // User cancelled the authentication process (back button, etc).
                        // Since auth could not take place, lets finish this activity.
//                    finish();
                    }
                }

                @Override
                protected void onSuccess(final Boolean succees) throws Exception {
                    if (!succees) {
                        new HandleApiMessages(SuggestRouteActivity.this, response).showMessages();
                    } else {
                        finishIt();
                    }

                }
            }.execute();
        }
    }
}
