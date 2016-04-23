package com.mibarim.main.RestInterfaces;


import com.mibarim.main.core.Constants;
import com.mibarim.main.models.Address.AddressResult;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Hamed on 3/14/2016.
 */
public interface GetAddressService {
    @GET(Constants.Geocoding.SERVICE_URL)
    AddressResult getAddress(@Query(Constants.Geocoding.LAT_LONG_KEY) String latlong,
                           @Query(Constants.Geocoding.LANGUAGE_KEY) String language,
                           @Query(Constants.Geocoding.LOCATION_TYPE_VALUE) String locationType,
                           @Query(Constants.Geocoding.GOOGLE_SERVICE_KEY) String googleKey);
}
