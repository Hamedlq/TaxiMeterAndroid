package com.mibarim.main.ui.fragments.messagingFragments;

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
import com.mibarim.main.activities.MessagingActivity;
import com.mibarim.main.activities.RouteActivity;
import com.mibarim.main.adapters.MessageListAdapter;
import com.mibarim.main.adapters.RouteListAdapter;
import com.mibarim.main.authenticator.LogoutService;
import com.mibarim.main.models.ApiResponse;
import com.mibarim.main.models.MessageModel;
import com.mibarim.main.models.Route.GroupModel;
import com.mibarim.main.models.Route.RouteResponse;
import com.mibarim.main.services.GroupService;
import com.mibarim.main.services.RouteResponseService;
import com.mibarim.main.ui.ItemListFragment;
import com.mibarim.main.ui.ItemListTwoViewFragment;
import com.mibarim.main.ui.ThrowableLoader;
import com.mibarim.main.util.SingleTypeAdapter;
import com.mibarim.main.util.SingleTypeTwoViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import retrofit.RetrofitError;

public class MessageListFragment extends ItemListTwoViewFragment<MessageModel> {

    @Inject
    protected BootstrapServiceProvider serviceProvider;
    @Inject
    protected GroupService groupService;
    @Inject
    protected LogoutService logoutService;

    List<MessageModel> latest;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_message);
    }

    @Override
    protected void configureList(final Activity activity, final ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);
        listView.setPadding(0, 0, 0, 100);
//        getListAdapter().addHeader(activity.getLayoutInflater()
//                .inflate(R.layout.route_list_item_labels, null));
    }

    @Override
    protected LogoutService getLogoutService() {
        return logoutService;
    }

    @Override
    public Loader<List<MessageModel>> onCreateLoader(final int id, final Bundle args) {
        final List<MessageModel> initialItems = items;
        return new ThrowableLoader<List<MessageModel>>(getActivity(), items) {
            @Override
            public List<MessageModel> loadData() throws Exception {
                Gson gson = new Gson();
                try {
                    ApiResponse res = new ApiResponse();
                    latest = new ArrayList<MessageModel>();
                    String authToken = serviceProvider.getAuthToken(getActivity());
                    if (getActivity() != null) {
                        RouteResponse routeResponse = ((MessagingActivity) getActivity()).getRouteResponse();
                        res = groupService.GetMessages(authToken, String.valueOf(routeResponse.GroupRoutes.get(0).GroupId));
                        if (res != null) {
                            for (String messageJson : res.Messages) {
                                MessageModel msg = gson.fromJson(messageJson, MessageModel.class);
                                latest.add(msg);
                            }
                        }
                    }
                    if (latest != null) {
                        return latest;
                    } else {
                        return Collections.emptyList();
                    }
                } catch (final OperationCanceledException e) {

//                    final Activity activity = getActivity();
//                    if (activity != null) {
//                        activity.finish();
//                    }
                    //return initialItems;
                    return Collections.emptyList();
                }
            }
        };

    }

    public void onListItemClick(final ListView l, final View v, final int position, final long id) {
    }

    @Override
    public void onLoadFinished(final Loader<List<MessageModel>> loader, final List<MessageModel> items) {
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
    protected SingleTypeTwoViewAdapter<MessageModel> createAdapter(final List<MessageModel> items) {
        return new MessageListAdapter(getActivity().getLayoutInflater(), items, getActivity());
    }
}
