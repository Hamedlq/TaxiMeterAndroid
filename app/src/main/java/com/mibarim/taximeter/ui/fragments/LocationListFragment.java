package com.mibarim.taximeter.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.mibarim.taximeter.BootstrapApplication;
import com.mibarim.taximeter.R;
import com.mibarim.taximeter.adapters.LocationAdapter;
import com.mibarim.taximeter.core.Constants;
import com.mibarim.taximeter.models.Address.AutoCompleteResult;
import com.mibarim.taximeter.models.Address.Place;
import com.mibarim.taximeter.services.AddressService;
import com.mibarim.taximeter.services.GoogleAutocompleteService;
import com.mibarim.taximeter.ui.ItemListFragment;
import com.mibarim.taximeter.ui.ThrowableLoader;
import com.mibarim.taximeter.ui.activities.LocationSearchActivity;
import com.mibarim.taximeter.util.SafeAsyncTask;
import com.mibarim.taximeter.util.SingleTypeAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import retrofit.RetrofitError;

public class LocationListFragment extends ItemListFragment<Place> {
    
    
    @Inject
    protected AddressService addressService;
    @Inject
    protected GoogleAutocompleteService autocomplete;
    AutoCompleteResult res;
    boolean refreshingToken = false;
    List<Place> latest;
    private int RELOAD_REQUEST = 1234;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
        res = new AutoCompleteResult();
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setBackgroundColor(Color.parseColor("#99ffffff"));
        setEmptyText(R.string.empty_string);
    }

    @Override
    protected void configureList(final Activity activity, final ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        /*getListAdapter().addHeader(activity.getLayoutInflater()
                .inflate(R.layout.event_list_item_labels, null));*/
    }

    @Override
    public Loader<List<Place>> onCreateLoader(final int id, final Bundle args) {
        final List<Place> initialItems = items;
        return new ThrowableLoader<List<Place>>(getActivity(), items) {
            @Override
            public List<Place> loadData() throws Exception {
//                Gson gson = new Gson();
                try {
                    latest = new ArrayList<Place>();
                    if (getActivity() != null) {
                        String term = ((LocationSearchActivity) getActivity()).getSearchTerm();
                        if (!term.equals("")) {
                            SharedPreferences pref = getActivity().getSharedPreferences(Constants.Geocoding.GOOGLE_KEYS, Context.MODE_PRIVATE);
                            String googleKey = pref.getString(Constants.Geocoding.GOOGLE_AUTOCOMPLETE_AUTH, "");
                            res = addressService.getAutocomplete(term, googleKey);
                            if (!res.status.equals("OK") && !refreshingToken) {
                                new SafeAsyncTask<Boolean>() {
                                    @Override
                                    public Boolean call() throws Exception {
                                        SharedPreferences pref = getActivity().getSharedPreferences(Constants.Geocoding.GOOGLE_KEYS, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = pref.edit();
                                        String googleKey = autocomplete.getKey(pref.getString(Constants.Geocoding.GOOGLE_AUTOCOMPLETE_AUTH, ""));
                                        editor.putString(Constants.Geocoding.GOOGLE_AUTOCOMPLETE_AUTH, googleKey)
                                                .apply();
                                        refreshingToken = true;
                                        return true;
                                    }
                                }.execute();
                            }
                            if (res != null && res.predictions.size() > 0) {
                                refreshingToken = false;
                                for (Place placeJson : res.predictions) {
                                    latest.add(placeJson);
                                }
                            }
                        }
                    }
                    if (latest != null && latest.size() > 0) {
                        return latest;
                    } else {
                        return Collections.emptyList();
                    }
                } catch (final Exception e) {
                    //return initialItems;
                    return Collections.emptyList();
                }
            }
        };

    }

    public void onListItemClick(final ListView l, final View v, final int position, final long id) {
        final Place response = ((Place) l.getItemAtPosition(position));
        ((LocationSearchActivity) getActivity()).setSearchResult(response.place_id);
//        Intent intent = new Intent(getActivity(), RouteActivity.class);
//        intent.putExtra("EventResponse", latest.get(position - 1));
        //((AddMapActivity)getActivity()).selectEvent(eventResponse);
//        ((RouteListActivity)getActivity()).startActivityForResult(intent, RELOAD_REQUEST);
    }

    @Override
    public void onLoadFinished(final Loader<List<Place>> loader, final List<Place> items) {
        super.onLoadFinished(loader, items);
    }

    @Override
    protected int getErrorMessage(final Exception exception) {
        if (!(exception instanceof RetrofitError)) {
            return R.string.error_loading_addresses;
        }
        return 0;
    }

    @Override
    protected SingleTypeAdapter<Place> createAdapter(final List<Place> items) {
        return new LocationAdapter(getActivity().getLayoutInflater(), items);
    }
}
