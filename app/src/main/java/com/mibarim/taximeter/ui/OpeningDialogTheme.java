package com.mibarim.taximeter.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.mibarim.taximeter.R;

/**
 * Created by Arya on 11/18/2017.
 */

public class OpeningDialogTheme extends Dialog {

    public OpeningDialogTheme(@NonNull Context context) {
        super(context);
        activity = (Activity) context;
    }

    Activity activity;
    public Button yes, no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.opening_app_dialog);
        yes = (Button) findViewById(R.id.btn_Yes);
        no = (Button) findViewById(R.id.btn_No);
    }

    public void setText(String header, String content) {
        TextView textView1 = (TextView) findViewById(R.id.header);
        TextView textView2 = (TextView) findViewById(R.id.content);
        textView1.setText(header);
        textView2.setText(content);
    }
}
