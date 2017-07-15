package com.mibarim.taximeter;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.telephony.TelephonyManager;

import com.mibarim.taximeter.core.PostFromAnyThreadBus;

import com.mibarim.taximeter.core.Constants;
import com.mibarim.taximeter.core.RestAdapterRequestInterceptor;
import com.mibarim.taximeter.core.RestErrorHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mibarim.taximeter.services.AddressService;
import com.mibarim.taximeter.services.PriceService;
import com.mibarim.taximeter.util.DynamicJsonConverter;
//import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Bus;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Dagger module for setting up provides statements.
 * Register all of your entry points below.
 */
@Module
public class BootstrapModule {
    @Singleton
    @Provides
    Bus provideOttoBus() {
        return new PostFromAnyThreadBus();
    }
    @Provides
    BootstrapServiceProvider provideBootstrapServiceProvider(RestAdapter restAdapter) {
        return new BootstrapServiceProviderImpl(restAdapter);
    }


    @Provides
    Gson provideGson() {
        /**
         * GSON instance to use for all request  with date format set up for proper parsing.
         * <p/>
         * You can also configure GSON with different naming policies for your API.
         * Maybe your API is Rails API and all json values are lower case with an underscore,
         * like this "first_name" instead of "firstName".
         * You can configure GSON as such below.
         * <p/>
         *
         * public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd")
         *         .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES).create();
         */
        return new GsonBuilder().disableHtmlEscaping().setDateFormat("yyyy-MM-dd").create();
    }

    @Provides
    RestErrorHandler provideRestErrorHandler(Bus bus) {
        return new RestErrorHandler(bus);
    }



    @Provides
    AddressService provideAddressService(RestAdapter restAdapter) {
        return new AddressService();
    }

    @Provides
    RestAdapterRequestInterceptor provideRestAdapterRequestInterceptor() {
        return new RestAdapterRequestInterceptor("application/x-www-form-urlencoded; charset=UTF-8");
    }

    @Provides
    @Named("json")
    RestAdapterRequestInterceptor provideRestAdapterRequestInterceptorJson() {
        return new RestAdapterRequestInterceptor("application/json; charset=UTF-8");
    }


    @Provides
    PriceService providePriceService(RestAdapter restAdapter,@Named("snapp") RestAdapter snappRestAdapter,
                                     @Named("snappAuth") RestAdapter snappAuthRestAdapter,
                                     @Named("tap30") RestAdapter tap30RestAdapter,
                                     @Named("carpino") RestAdapter carpinoRestAdapter) {
        return new PriceService(restAdapter,snappRestAdapter,snappAuthRestAdapter,tap30RestAdapter,carpinoRestAdapter);
    }

    @Provides
    RestAdapter provideRestAdapter(RestErrorHandler restErrorHandler, RestAdapterRequestInterceptor restRequestInterceptor, Gson gson) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);

        return new RestAdapter.Builder()
                .setEndpoint(Constants.Http.URL_BASE)
                .setErrorHandler(restErrorHandler)
                .setRequestInterceptor(restRequestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new DynamicJsonConverter())
                .setClient(new OkClient(okHttpClient))
                .build();
    }
    @Provides
    @Named("snapp")
    RestAdapter provideRestAdapterSnapp(RestErrorHandler restErrorHandler, RestAdapterRequestInterceptor restRequestInterceptor, Gson gson)
    {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);

        return new RestAdapter.Builder()
                .setEndpoint("https://api.snapp.site/")
//                .setErrorHandler(restErrorHandler)
                .setRequestInterceptor(restRequestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
//                .setConverter(new DynamicJsonConverter())//problem in bood!!
                .setClient(new OkClient(okHttpClient))
                .build();
    }

    @Provides
    @Named("snappAuth")
    RestAdapter provideRestAdapterSnappAuth(RestErrorHandler restErrorHandler, RestAdapterRequestInterceptor restRequestInterceptor, Gson gson){
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);

        return new RestAdapter.Builder()
                .setEndpoint("https://oauth-passenger.snapp.site/")
//                .setErrorHandler(restErrorHandler)
                .setRequestInterceptor(restRequestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
//                .setConverter(new DynamicJsonConverter())//problem in bood!!
                .setClient(new OkClient(okHttpClient))
                .build();
    }

    @Provides
    @Named("tap30")
    RestAdapter provideRestAdapterTap30(RestErrorHandler restErrorHandler,@Named("json") RestAdapterRequestInterceptor restRequestInterceptor, Gson gson)
    {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);

        return new RestAdapter.Builder()
                .setEndpoint("https://tap33.me/")
//                .setErrorHandler(restErrorHandler)
                .setRequestInterceptor(restRequestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
//                .setConverter(new DynamicJsonConverter())//problem in bood!!
                .setClient(new OkClient(okHttpClient))
                .build();
    }

    @Provides
    @Named("carpino")
    RestAdapter provideCarpinoRestAdapter(RestErrorHandler restErrorHandler,@Named("json") RestAdapterRequestInterceptor restRequestInterceptor, Gson gson)
    {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);

        return new RestAdapter.Builder()
                .setEndpoint("https://api.carpino.io/")
//                .setErrorHandler(restErrorHandler)
                .setRequestInterceptor(restRequestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
//                .setConverter(new DynamicJsonConverter())//problem in bood!!
                .setClient(new OkClient(okHttpClient))
                .build();

    }



}
