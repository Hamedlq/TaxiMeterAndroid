package com.mibarim.taximeter;

import android.accounts.AccountsException;
import android.app.Activity;

import java.io.IOException;

public interface BootstrapServiceProvider {
    String getAuthToken(Activity activity) throws IOException, AccountsException;

    void invalidateAuthToken();
}
