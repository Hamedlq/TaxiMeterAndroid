
package com.mibarim.taximeter;

import android.accounts.AccountsException;
import android.app.Activity;

import java.io.IOException;

import retrofit.RestAdapter;


public class BootstrapServiceProviderImpl implements BootstrapServiceProvider {

    private RestAdapter restAdapter;
    private String authToken;

    public BootstrapServiceProviderImpl(RestAdapter restAdapter) {
        this.restAdapter = restAdapter;
        //this.keyProvider = keyProvider;
    }

    /**
     * Get service for configured key provider
     * <p/>
     * This method gets an auth key and so it blocks and shouldn't be called on the taximeter thread.
     *
     * @return bootstrap service
     * @throws IOException
     * @throws AccountsException
     */


    public String getAuthToken(final Activity activity) throws IOException, AccountsException {
        if(authToken==null){
            //authToken=keyProvider.getAuthKey(activity);
        }
        return authToken;
    }
    public void invalidateAuthToken(){
        authToken=null;
    }
}
