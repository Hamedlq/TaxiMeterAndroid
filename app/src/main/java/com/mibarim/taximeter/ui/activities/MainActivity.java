package com.mibarim.taximeter.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.crashlytics.android.Crashlytics;
import com.mibarim.taximeter.R;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Hamed on 3/30/2016.
 */
public class MainActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Intent mainIntent = new Intent(MainActivity.this, AddMapActivity.class);
        MainActivity.this.startActivity(mainIntent);
        MainActivity.this.finish();
    }
}
