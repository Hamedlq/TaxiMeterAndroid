package com.mibarim.main.ui.fragments;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.mibarim.main.BootstrapApplication;
import com.mibarim.main.BootstrapServiceProvider;
import com.mibarim.main.R;
import com.mibarim.main.activities.RouteActivity;
import com.mibarim.main.activities.RouteListActivity;
import com.mibarim.main.adapters.RouteListAdapter;
import com.mibarim.main.authenticator.LogoutService;
import com.mibarim.main.models.ApiResponse;
import com.mibarim.main.models.Route.RouteResponse;
import com.mibarim.main.services.RouteResponseService;
import com.mibarim.main.ui.HandleApiMessages;
import com.mibarim.main.ui.ItemListFragment;
import com.mibarim.main.ui.ThrowableLoader;
import com.mibarim.main.util.SingleTypeAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import retrofit.RetrofitError;

public class RouteListFragment extends ItemListFragment<RouteResponse> {

    @Inject
    protected BootstrapServiceProvider serviceProvider;
    @Inject
    protected RouteResponseService routeResponseService;
    @Inject
    protected LogoutService logoutService;

    List<RouteResponse> latest;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_routes);
    }

    @Override
    protected void configureList(final Activity activity, final ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter().addHeader(activity.getLayoutInflater()
                .inflate(R.layout.route_list_item_labels, null));
    }

    @Override
    protected LogoutService getLogoutService() {
        return logoutService;
    }

    @Override
    public Loader<List<RouteResponse>> onCreateLoader(final int id, final Bundle args) {
        final List<RouteResponse> initialItems = items;
        return new ThrowableLoader<List<RouteResponse>>(getActivity(), items) {
            @Override
            public List<RouteResponse> loadData() throws Exception {
                Gson gson = new Gson();
                try {
                    ApiResponse res = new ApiResponse();
                    latest = new ArrayList<RouteResponse>();
                    String authToken = serviceProvider.getAuthToken(getActivity());
                    if (getActivity() != null) {
                        res = routeResponseService.GetRoutes(authToken);
                        if (res != null) {
                            for (String routeJson : res.Messages) {
                                RouteResponse route = gson.fromJson(routeJson, RouteResponse.class);
                                latest.add(route);
                            }
                            new HandleApiMessages(getActivity(), res).showMessages();
                        }
                    }
                    if (latest != null) {
                        ((RouteListActivity)getActivity()).setRouteList(latest);
                        return latest;
                    } else {
                        return Collections.emptyList();
                    }
                } catch (final OperationCanceledException e) {

                    //return initialItems;
                    return Collections.emptyList();
                }
            }
        };

    }

    public void onListItemClick(final ListView l, final View v, final int position, final long id) {
        final RouteResponse routeResponse = ((RouteResponse) l.getItemAtPosition(position));
        Intent intent = new Intent(getActivity(), RouteActivity.class);
        intent.putExtra("RouteResponse", latest.get(position - 1));
        startActivity(intent);
    }

    @Override
    public void onLoadFinished(final Loader<List<RouteResponse>> loader, final List<RouteResponse> items) {
        super.onLoadFinished(loader, items);
    }

    @Override
    protected int getErrorMessage(final Exception exception) {
        if (!(exception instanceof RetrofitError)) {
            return R.string.error_loading_routes;
        }
        return 0;
    }

    @Override
    protected SingleTypeAdapter<RouteResponse> createAdapter(final List<RouteResponse> items) {
        return new RouteListAdapter(getActivity().getLayoutInflater(), items);
    }
}
