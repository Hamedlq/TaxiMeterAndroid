package com.mibarim.main.ui.fragments.routeFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.mibarim.main.BootstrapApplication;
import com.mibarim.main.R;
import com.mibarim.main.activities.RouteActivity;
import com.mibarim.main.activities.SuggestGroupActivity;
import com.mibarim.main.adapters.GroupListAdapter;
import com.mibarim.main.authenticator.LogoutService;
import com.mibarim.main.models.Route.GroupModel;
import com.mibarim.main.models.Route.RouteResponse;
import com.mibarim.main.ui.ItemListFragment;
import com.mibarim.main.ui.ThrowableLoader;
import com.mibarim.main.util.SingleTypeAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by Hamed on 3/5/2016.
 */
public class SuggestGroupListFragment extends ItemListFragment<GroupModel> {


    List<GroupModel> latest;
    GroupListAdapter groupListAdapter;

    private RouteResponse routeResponse;
    private int RELOAD_REQUEST=1234;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
    }

    @Override
    protected LogoutService getLogoutService() {
        return null;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_suggest);
    }

    @Override
    protected void configureList(final Activity activity, final ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);
//
//        getListAdapter().addHeader(activity.getLayoutInflater()
//                .inflate(R.layout.route_list_item_labels, null));
    }

    @Override
    public Loader<List<GroupModel>> onCreateLoader(final int id, final Bundle args) {
        final List<GroupModel> initialItems = items;
        return new ThrowableLoader<List<GroupModel>>(getActivity(), items) {
            @Override
            public List<GroupModel> loadData() throws Exception {
                latest = new ArrayList<GroupModel>();
                routeResponse = ((RouteActivity) getActivity()).getRoute();
                for (GroupModel groupModel : routeResponse.SuggestGroups) {
                    latest.add(groupModel);
                }
                if (latest != null) {
                    return latest;
                } else {
                    return Collections.emptyList();
                }
            }
        };

    }


    public void onListItemClick(final ListView l, final View v, final int position, final long id) {
        final GroupModel selectedItem = ((GroupModel) l.getItemAtPosition(position));
        Intent intent = new Intent(getActivity(), SuggestGroupActivity.class);
        intent.putExtra("RouteResponse", routeResponse);
        intent.putExtra("GroupModel", selectedItem);
        getActivity().startActivityForResult(intent, RELOAD_REQUEST);
    }

    @Override
    public void onLoadFinished(final Loader<List<GroupModel>> loader, final List<GroupModel> items) {
        super.onLoadFinished(loader, items);
    }

    @Override
    protected int getErrorMessage(final Exception exception)
    {
        if (!(exception instanceof RetrofitError)) {
            return R.string.error_loading_routes;
        }
        return 0;
    }

    @Override
    protected SingleTypeAdapter<GroupModel> createAdapter(final List<GroupModel> items) {
        groupListAdapter=new GroupListAdapter(getActivity().getLayoutInflater(), items, getActivity());
        return groupListAdapter;
    }



}
