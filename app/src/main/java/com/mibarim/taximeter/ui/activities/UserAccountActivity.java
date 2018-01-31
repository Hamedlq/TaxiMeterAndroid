package com.mibarim.taximeter.ui.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

import com.mibarim.taximeter.R;
import com.mibarim.taximeter.ui.fragments.userAccount.UserSignUpFragment;

/**
 * Created by Arya on 1/14/2018.
 */

public class UserAccountActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_account_layout);
        initScreen();
    }

    private void initScreen() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.user_account, new UserSignUpFragment())
                .commit();
    }
}
