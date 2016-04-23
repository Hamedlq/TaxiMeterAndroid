

package com.mibarim.main.activities;


import android.accounts.OperationCanceledException;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
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
import com.google.gson.Gson;
import com.mibarim.main.BootstrapApplication;
import com.mibarim.main.BootstrapServiceProvider;
import com.mibarim.main.R;
import com.mibarim.main.authenticator.LogoutService;
import com.mibarim.main.models.ApiResponse;
import com.mibarim.main.models.PersonalInfoModel;
import com.mibarim.main.models.Route.RouteResponse;
import com.mibarim.main.services.RouteRequestService;
import com.mibarim.main.services.RouteResponseService;
import com.mibarim.main.ui.BootstrapActivity;
import com.mibarim.main.ui.fragments.routeFragments.GroupFragment;
import com.mibarim.main.ui.fragments.routeFragments.MainRouteFragment;
import com.mibarim.main.util.SafeAsyncTask;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;


/**
 * Initial activity for the application.
 * * <p/>
 * If you need to remove the authentication from the application please see
 */
public class RouteActivity extends BootstrapActivity {

    @Inject
    protected BootstrapServiceProvider serviceProvider;
    @Inject
    RouteRequestService routeRequestService;
    @Inject
    LogoutService getLogoutService;
    @Inject
    RouteResponseService routeResponseService;

    private int RELOAD_REQUEST = 1234;
    private CharSequence title;
    private Toolbar toolbar;

    private String authToken;

    private RouteResponse theRoute;

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
            theRoute = (RouteResponse) getIntent().getExtras().getSerializable("RouteResponse");
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
                .add(R.id.route_container, new MainRouteFragment())
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

    public RouteResponse getRoute() {
        return theRoute;
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
                authToken = serviceProvider.getAuthToken(RouteActivity.this);
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

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                String token = serviceProvider.getAuthToken(RouteActivity.this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RELOAD_REQUEST) {
            reloadRoute();
        }
    }

    private void reloadRoute() {
        showProgress();
        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (authToken == null) {
                    authToken = serviceProvider.getAuthToken(RouteActivity.this);
                }
                ApiResponse res = routeResponseService.GetRoutes(authToken);
                if (res != null) {
                    for (String routeJson : res.Messages) {
                        RouteResponse route = new Gson().fromJson(routeJson, RouteResponse.class);
                        if (route.RouteId == theRoute.RouteId) {
                            theRoute = route;
                        }
                    }
                }
                return true;
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);
            }

            @Override
            protected void onSuccess(final Boolean hasAuthenticated) throws Exception {
                super.onSuccess(hasAuthenticated);
                if (Build.VERSION.SDK_INT >= 11) {
                    Intent intent = getIntent();
                    intent.putExtra("RouteResponse", theRoute);
                    recreate();
                } else {
                    Intent intent = getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("RouteResponse", theRoute);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
                hideProgress();
            }
        }.execute();

    }

}
