

package com.mibarim.main.activities;

import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.crashlytics.android.Crashlytics;
import com.mibarim.main.BootstrapApplication;
import com.mibarim.main.BootstrapServiceProvider;
import com.mibarim.main.R;
import com.mibarim.main.models.ApiResponse;
import com.mibarim.main.models.CarInfoModel;
import com.mibarim.main.models.LicenseInfoModel;
import com.mibarim.main.models.PersonalInfoModel;
import com.mibarim.main.services.AuthenticateService;
import com.mibarim.main.services.RouteRequestService;
import com.mibarim.main.services.UserImageService;
import com.mibarim.main.services.UserInfoService;
import com.mibarim.main.ui.BootstrapActivity;
import com.mibarim.main.ui.HandleApiMessages;
import com.mibarim.main.ui.fragments.userInfoFragments.UserInfoCarouselFragment;
import com.mibarim.main.ui.fragments.userInfoFragments.UserPersonFragment;
import com.mibarim.main.util.SafeAsyncTask;
import com.mibarim.main.util.Toaster;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import retrofit.mime.TypedFile;


/**
 * Initial activity for the application.
 * <p/>
 * If you need to remove the authentication from the application please see
 * {@link com.mibarim.main.authenticator.ApiKeyProvider#getAuthKey(android.app.Activity)}
 */
public class UserPersonalActivity extends BootstrapActivity {

    private static final int USER_REQUEST_CAMERA = 1;
    private static final int USER_SELECT_FILE = 2;

    @Inject
    BootstrapServiceProvider serviceProvider;
    @Inject
    RouteRequestService routeRequestService;
    @Inject
    UserInfoService userInfoService;
    @Inject
    UserImageService userImageService;

    protected String authToken;
    protected ApiResponse response;

    private boolean userHasAuthenticated = false;

    private PersonalInfoModel personalInfoModel;

    private static final String PERSONAL_INFO = "personalInfo";

    private boolean isPersonalInfoSaved = false;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        BootstrapApplication.component().inject(this);

        setContentView(R.layout.user_info_activity);

        // View injection with Butterknife
        ButterKnife.bind(this);

        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        if (getIntent() != null && getIntent().getExtras() != null) {
            personalInfoModel = (PersonalInfoModel) getIntent().getExtras().getSerializable(PERSONAL_INFO);
        }


//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeButtonEnabled(true);
//        }

        checkAuth();
    }


    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }


    private void initScreen() {
        if (userHasAuthenticated) {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_container, new UserPersonFragment())
                    .commit();
        }
    }

    private void checkAuth() {
        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                final AuthenticateService svc = serviceProvider.getService(UserPersonalActivity.this);
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
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_route_btn:
                saveUserInfo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveUserInfo() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_container);
        personalInfoModel = ((UserPersonFragment) fragment).getUserInfo();
        saveUserPersonalInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_route_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public PersonalInfoModel getPersonalInfo() {
        return personalInfoModel;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.main_container);
            if (requestCode == USER_REQUEST_CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                TypedFile typedFile = new TypedFile("image/jpeg", destination);
                ((UserPersonFragment) fragment).setUserImage(thumbnail);
                saveUserImage(typedFile);
            } else if (requestCode == USER_SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null,
                        null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 500;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);
                TypedFile typedFile = new TypedFile("image/jpeg", new File(selectedImagePath));
                ((UserPersonFragment) fragment).setUserImage(bm);
                saveUserImage(typedFile);
            }
        }
    }

    private void saveUserImage(final TypedFile pic) {
        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (authToken == null) {
                    authToken = serviceProvider.getAuthToken(UserPersonalActivity.this);
                }
                response = userImageService.SaveUserImage(authToken, pic);
                if ((response.Errors == null || response.Errors.size() == 0) && response.Status.equals("OK")) {
                    return true;
                }
                return true;
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);
            }

            @Override
            protected void onSuccess(final Boolean uploadSuccess) throws Exception {
                super.onSuccess(uploadSuccess);
                if (!uploadSuccess) {
                    new HandleApiMessages(UserPersonalActivity.this, response).showMessages();
                }
            }
        }.execute();
    }

    private void saveUserPersonalInfo() {
        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (authToken == null) {
                    authToken = serviceProvider.getAuthToken(UserPersonalActivity.this);
                }
                response = userInfoService.SaveUserPersonalInfo(authToken, personalInfoModel);
                if ((response.Errors == null || response.Errors.size() == 0) && response.Status.equals("OK")) {
                    return true;
                }
                return false;
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);
            }

            @Override
            protected void onSuccess(final Boolean uploadSuccess) throws Exception {
                super.onSuccess(uploadSuccess);
                if (!uploadSuccess) {
                    new HandleApiMessages(UserPersonalActivity.this, response).showMessages();
                } else {
                    finishIt();
                }
            }
        }.execute();
    }

    private void finishIt() {
        //Toaster.showLong(UserPersonalActivity.this, R.string.info_saved);
        this.finish();
    }

}
