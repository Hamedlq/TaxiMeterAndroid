

package com.mibarim.main.activities;

import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;

import com.crashlytics.android.Crashlytics;
import com.mibarim.main.BootstrapApplication;
import com.mibarim.main.BootstrapServiceProvider;
import com.mibarim.main.R;
import com.mibarim.main.authenticator.LogoutService;
import com.mibarim.main.events.NavItemSelectedEvent;
import com.mibarim.main.events.NetworkErrorEvent;
import com.mibarim.main.events.RestAdapterErrorEvent;
import com.mibarim.main.events.UnAuthorizedErrorEvent;
import com.mibarim.main.models.CarInfoModel;
import com.mibarim.main.models.LicenseInfoModel;
import com.mibarim.main.models.PersonalInfoModel;
import com.mibarim.main.models.RouteRequest;
import com.mibarim.main.services.AuthenticateService;
import com.mibarim.main.services.RouteRequestService;
import com.mibarim.main.services.UserInfoService;
import com.mibarim.main.ui.BootstrapActivity;
import com.mibarim.main.ui.NavigationDrawerFragment;
import com.mibarim.main.ui.fragments.AddButtonFragment;
import com.mibarim.main.ui.fragments.MainRouteListFragment;
import com.mibarim.main.ui.fragments.RouteListFragment;
import com.mibarim.main.util.SafeAsyncTask;
import com.mibarim.main.util.Toaster;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;


/**
 * Initial activity for the application.
 * <p/>
 * If you need to remove the authentication from the application please see
 * {@link com.mibarim.main.authenticator.ApiKeyProvider#getAuthKey(android.app.Activity)}
 */
public class RouteListActivity extends BootstrapActivity {

    @Inject
    BootstrapServiceProvider serviceProvider;
    @Inject
    UserInfoService userInfoService;
    @Inject
    LogoutService getLogoutService;


    private boolean userHasAuthenticated = false;

    private int RELOAD_REQUEST = 1234;
    private static final String PERSONAL_INFO = "personalInfo";
    private static final String LICENSE_INFO = "licenseInfo";
    private static final String CAR_INFO = "carInfo";

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private CharSequence drawerTitle;
    private CharSequence title;
    private NavigationDrawerFragment navigationDrawerFragment;
    private Toolbar toolbar;

    private String authToken = null;

    private PersonalInfoModel personalInfoModel;
    private CarInfoModel carInfoModel;
    private LicenseInfoModel licenseInfoModel;

//    protected String srcDstStateSelector;//SOURCE_SELECTED DESTINATION_SELECTED
//    public RouteRequest routeRequest;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        BootstrapApplication.component().inject(this);


        setContentView(R.layout.routelist_activity);

        // View injection with Butterknife
        ButterKnife.bind(this);

        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        // Set up navigation drawer
        title = drawerTitle = getTitle();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(
                this,                    /* Host activity */
                drawerLayout,           /* DrawerLayout object */
                R.string.navigation_drawer_open,    /* "open drawer" description */
                R.string.navigation_drawer_close) { /* "close drawer" description */

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(title);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                syncState();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                syncState();
            }
        };


        drawerToggle.syncState();

        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(drawerToggle);

        navigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        navigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeButtonEnabled(true);
//        }

        //      srcDstStateSelector = "SOURCE_SELECTED";

        checkAuth();
        //routeRequest = new RouteRequest();
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        drawerToggle.onConfigurationChanged(newConfig);

    }


    private void initScreen() {
        if (userHasAuthenticated) {

            final FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.route_list_container, new MainRouteListFragment())
                    .commit();
            loadUserData();
        }

    }

    private void checkAuth() {
        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                final AuthenticateService svc = serviceProvider.getService(RouteListActivity.this);
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
                userHasAuthenticated = true;
                initScreen();
            }
        }.execute();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == android.R.id.list) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.route_list_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete_route_btn:
                Toaster.showLong(RouteListActivity.this, String.valueOf(info.position));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.newRoute:
                AddNewRouteActivity();
                return true;
            case R.id.myRoutes:
                refreshRouteList();
                return true;
            case R.id.userInfo:
                userInfoActivity();
                return true;
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Subscribe
    public void onNetworkErrorEvent(NetworkErrorEvent event) {
        Toaster.showLong(RouteListActivity.this, getString(R.string.network_error), R.drawable.toast_warn);
        refreshNeeded();
    }

    @Subscribe
    public void onRestAdapterErrorEvent(RestAdapterErrorEvent event) {
        Toaster.showLong(RouteListActivity.this, getString(R.string.network_error), R.drawable.toast_warn);
        refreshNeeded();
    }

    @Subscribe
    public void onUnAuthorizedErrorEvent(UnAuthorizedErrorEvent event) {
        Toaster.showLong(RouteListActivity.this, getString(R.string.network_error), R.drawable.toast_warn);
        logout();
    }

    @Subscribe
    public void onNavigationItemSelected(NavItemSelectedEvent event) {
        switch (event.getItemPosition()) {
            case 1:
                AddNewRouteActivity();
                break;
            case 2:
                ReloadActivity();
                break;
            case 3:
                userInfoActivity();
                break;
            case 4:
                logout();
                break;
        }
    }

    private void userInfoActivity() {
        final Intent i = new Intent(this, UserInfoActivity.class);
        i.putExtra(PERSONAL_INFO, personalInfoModel);
        i.putExtra(CAR_INFO, carInfoModel);
        i.putExtra(LICENSE_INFO, licenseInfoModel);
        startActivity(i);

    }

    private void logout() {
        getLogoutService.logout(new Runnable() {
            @Override
            public void run() {
                // Calling a refresh will force the service to look for a logged in user
                // and when it finds none the user will be requested to log in again.
                //forceRefresh();
                ReloadActivity();
            }
        });

    }

    public void AddNewRouteActivity() {
        final Intent i = new Intent(this, AddMapActivity.class);
        i.putExtra(PERSONAL_INFO, personalInfoModel);
        i.putExtra(CAR_INFO, carInfoModel);
        i.putExtra(LICENSE_INFO, licenseInfoModel);
        startActivityForResult(i, RELOAD_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RELOAD_REQUEST && resultCode == RESULT_OK && data != null) {
            refreshRouteList();
        }
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


    private void loadUserData() {
        getLicenseInfoFromServer();
        getCarInfoFromServer();
        getPersonalInfoFromServer();
    }

    private void getLicenseInfoFromServer() {
        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (authToken == null) {
                    authToken = serviceProvider.getAuthToken(RouteListActivity.this);
                }
                licenseInfoModel = userInfoService.getLicenseInfo(authToken);
                return true;
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);
            }

            @Override
            protected void onSuccess(final Boolean state) throws Exception {
                super.onSuccess(state);
            }
        }.execute();
    }

    private void getCarInfoFromServer() {
        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (authToken == null) {
                    authToken = serviceProvider.getAuthToken(RouteListActivity.this);
                }
                carInfoModel = userInfoService.getCarInfo(authToken);
                return true;
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);
            }

            @Override
            protected void onSuccess(final Boolean state) throws Exception {
                super.onSuccess(state);
            }
        }.execute();
    }

    private void getPersonalInfoFromServer() {
        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (authToken == null) {
                    authToken = serviceProvider.getAuthToken(RouteListActivity.this);
                }
                personalInfoModel = userInfoService.getUserInfo(authToken);
                return true;
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);
            }

            @Override
            protected void onSuccess(final Boolean state) throws Exception {
                super.onSuccess(state);
            }
        }.execute();
    }

    public void refreshRouteListBtn() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.route_list_container);
        ((MainRouteListFragment) fragment).hideRefreshBtn();
        refreshRouteList();
    }

    public void refreshRouteList() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.route_list_fragment);
        ((RouteListFragment) fragment).refresh();
    }

    public void refreshNeeded() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.route_list_container);
        ((MainRouteListFragment) fragment).showRefreshBtn();
    }
}
