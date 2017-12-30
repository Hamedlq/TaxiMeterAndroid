package com.mibarim.taximeter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mibarim.taximeter.core.Constants;
import com.mibarim.taximeter.core.PostFromAnyThreadBus;
import com.mibarim.taximeter.core.RestAdapterRequestInterceptor;
import com.mibarim.taximeter.core.RestErrorHandler;
import com.mibarim.taximeter.services.AddressService;
import com.mibarim.taximeter.services.GoogleAutocompleteService;
import com.mibarim.taximeter.services.PriceService;
import com.mibarim.taximeter.services.ServiceOrderService;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Bus;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setSslSocketFactory(sslSocketFactory);
            okHttpClient.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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
    GoogleAutocompleteService provideAutocompleteService() {
        return new GoogleAutocompleteService();
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
    PriceService providePriceService(RestAdapter restAdapter, @Named("snapp") RestAdapter snappRestAdapter,
                                     @Named("snappAuth") RestAdapter snappAuthRestAdapter,
                                     @Named("tap30") RestAdapter tap30RestAdapter,
                                     @Named("tap30Auth") RestAdapter tap30AuthRestAdapter,
                                     @Named("carpino") RestAdapter carpinoRestAdapter,
                                     @Named("alopeyk") RestAdapter alopeykRestAdapter,
                                     @Named("maxim") RestAdapter maximRestAdapter,
                                     @Named("tochsi") RestAdapter tochsiRestAdapter) {
        return new PriceService(restAdapter, snappRestAdapter, snappAuthRestAdapter, tap30AuthRestAdapter, tap30RestAdapter, carpinoRestAdapter, alopeykRestAdapter, maximRestAdapter,tochsiRestAdapter);
    }

    @Provides
    ServiceOrderService provideOrder(@Named("serviceOrder") RestAdapter serviceOrderAdapter){
        return new ServiceOrderService(serviceOrderAdapter);
    }

    @Provides
    @Named("get_token")
    RestAdapter getToken(RestErrorHandler restErrorHandler, RestAdapterRequestInterceptor restRequestInterceptor, Gson gson) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);

        return new RestAdapter.Builder()
                .setEndpoint(Constants.Http.URL_BASE)
//                .setErrorHandler(restErrorHandler)
                .setRequestInterceptor(restRequestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
//                .setConverter(new DynamicJsonConverter())//problem in bood!!
                .setClient(new OkClient(okHttpClient))
                .build();
    }

    @Provides
    RestAdapter provideRestAdapter(RestErrorHandler restErrorHandler, RestAdapterRequestInterceptor restRequestInterceptor, Gson gson) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);

        return new RestAdapter.Builder()
                .setEndpoint(Constants.Http.URL_BASE)
//                .setErrorHandler(restErrorHandler)
                .setRequestInterceptor(restRequestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
//                .setConverter(new DynamicJsonConverter())
                .setClient(new OkClient(okHttpClient))
                .build();
    }

    @Provides
    @Named("snapp")
    RestAdapter provideRestAdapterSnapp(RestErrorHandler restErrorHandler, RestAdapterRequestInterceptor restRequestInterceptor, Gson gson) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);

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
    RestAdapter provideRestAdapterSnappAuth(RestErrorHandler restErrorHandler, RestAdapterRequestInterceptor restRequestInterceptor, Gson gson) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);
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
    @Named("tap30Auth")
    RestAdapter provideRestAdapterTap30Auth(RestErrorHandler restErrorHandler, RestAdapterRequestInterceptor restRequestInterceptor, Gson gson) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);

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
    RestAdapter provideRestAdapterTap30(RestErrorHandler restErrorHandler, @Named("json") RestAdapterRequestInterceptor restRequestInterceptor, Gson gson) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);
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
    @Named("carpinoAuth")
    RestAdapter provideRestAdapterCarpinoAuth(RestErrorHandler restErrorHandler, RestAdapterRequestInterceptor restRequestInterceptor, Gson gson) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);
        return new RestAdapter.Builder()
                .setEndpoint("https://api.carpino.io/")
//                .setErrorHandler(restErrorHandler)
                .setRequestInterceptor(restRequestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
//                .setConverter(new DynamicJsonConverter())//problem in bood!!
                .setClient(new OkClient(okHttpClient))
                .build();
    }

    @Provides
    @Named("carpino")
    RestAdapter provideCarpinoRestAdapter(RestErrorHandler restErrorHandler, @Named("json") RestAdapterRequestInterceptor restRequestInterceptor, Gson gson) {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setSslSocketFactory(sslSocketFactory);
            okHttpClient.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return new RestAdapter.Builder()
                    .setEndpoint("https://api.carpino.io/")
//                .setErrorHandler(restErrorHandler)
                    .setRequestInterceptor(restRequestInterceptor)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
//                .setConverter(new DynamicJsonConverter())//problem in bood!!
                    .setClient(new OkClient(okHttpClient))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Provides
    @Named("alopeyk")
    RestAdapter provideRestAdapterAlopeyk(RestErrorHandler restErrorHandler, @Named("json") RestAdapterRequestInterceptor restRequestInterceptor, Gson gson) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);
        return new RestAdapter.Builder()
                .setEndpoint("https://api.alopeyk.com")
//                .setErrorHandler(restErrorHandler)
                .setRequestInterceptor(restRequestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
//                .setConverter(new DynamicJsonConverter())//problem in bood!!
                .setClient(new OkClient(okHttpClient))
                .build();
    }

    @Provides
    @Named("maxim")
    RestAdapter provideRestAdapterMaxim(RestErrorHandler restErrorHandler, RestAdapterRequestInterceptor restRequestInterceptor, Gson gson) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);
        return new RestAdapter.Builder()
                .setEndpoint("http://cabinet.taximaxim.ir")
//                .setErrorHandler(restErrorHandler)
                .setRequestInterceptor(restRequestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
//                .setConverter(new DynamicJsonConverter())//problem in bood!!
                .setClient(new OkClient(okHttpClient))
                .build();
    }
    @Provides
    @Named("tochsi")
    RestAdapter provideRestAdapterTochsi(RestErrorHandler restErrorHandler,@Named("json") RestAdapterRequestInterceptor restRequestInterceptor, Gson gson) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);
        return new RestAdapter.Builder()
                .setEndpoint("https://tchp.mshdiau.ac.ir")
//                .setErrorHandler(restErrorHandler)
                .setRequestInterceptor(restRequestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
//                .setConverter(new DynamicJsonConverter())//problem in bood!!
                .setClient(new OkClient(okHttpClient))
                .build();
    }

    @Provides
    @Named("serviceOrder")
    RestAdapter provideRestAdapterServiceOrder(RestAdapterRequestInterceptor restRequestInterceptor){
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);
        return new RestAdapter.Builder()
                .setEndpoint(Constants.Http.URL_BASE)
                .setRequestInterceptor(restRequestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(okHttpClient))
                .build();
    }
}
