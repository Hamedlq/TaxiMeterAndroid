/*
package com.mibarim.main.core;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.mibarim.main.authenticator.ApiKeyProvider;
import com.mibarim.main.util.Strings;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Provider;

import timber.log.Timber;


public class UserTokenProvider implements Provider<String> {

    private static final String APP_NAME = "Mibarim";

    private ApiKeyProvider keyProvider;

    public UserTokenProvider(ApiKeyProvider keyProvider) {
        this.keyProvider = keyProvider;
    }

    protected String authToken;


    @Override
    public String get() {
        if (authToken == null) {
            synchronized (UserTokenProvider.class) {
                if (authToken == null) {
                    try {
                        authToken=keyProvider.getAuthKey(getActivity());

                    } catch (Exception ignored) {
                        Timber.d(ignored, ignored.getMessage());
                    }
                }
            }
        }

        return "Bearer "+authToken;
    }
}
*/
