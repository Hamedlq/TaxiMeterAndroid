package com.mibarim.taximeter.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mibarim.taximeter.R;

/**
 * Created by mohammad hossein on 16/12/2017.
 */

public class dialog_fav extends Dialog {
    Activity activity;
    public EditText editText_detail;
    public  Button btn_detail;

    public dialog_fav(@NonNull Context context) {
        super(context);
        activity = (Activity) context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.get_fav_detail);

        editText_detail = (EditText)findViewById(R.id.dialog_fav_text);
        btn_detail = (Button)findViewById(R.id.dialog_fav_btn);

    }


}
