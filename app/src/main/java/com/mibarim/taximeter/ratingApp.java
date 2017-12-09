package com.mibarim.taximeter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.Button;

/**
 * Created by mohammad hossein on 07/12/2017.
 */

public class ratingApp extends Dialog {
    public Button yesRate,noRate,neverRate;
    public ratingApp(@NonNull Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.rating_app);
        yesRate = (Button)findViewById(R.id.btn_YesRate);
        noRate = (Button)findViewById(R.id.btn_NoRate);
        neverRate = (Button)findViewById(R.id.btn_NeverRate);
    }
}
