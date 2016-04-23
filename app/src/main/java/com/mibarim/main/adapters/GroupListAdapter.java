package com.mibarim.main.adapters;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;

import com.mibarim.main.BootstrapServiceProvider;
import com.mibarim.main.R;
import com.mibarim.main.activities.RouteActivity;
import com.mibarim.main.models.Route.GroupModel;
import com.mibarim.main.services.RouteResponseService;
import com.mibarim.main.util.SingleTypeAdapter;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;

/**
 * Adapter to display a list of traffic items
 */
public class GroupListAdapter extends SingleTypeAdapter<GroupModel> {

    @Inject
    RouteResponseService routeResponseService;
    @Inject
    protected BootstrapServiceProvider serviceProvider;

    Activity activity;


    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM dd");

    /**
     * @param inflater
     * @param items
     */
    public GroupListAdapter(final LayoutInflater inflater, final List<GroupModel> items) {
        super(inflater, R.layout.suggest_group_list_item);

        setItems(items);
    }

    /**
     * @param inflater
     * @param items
     */
    public GroupListAdapter(final LayoutInflater inflater, final List<GroupModel> items, Activity activity) {
        super(inflater, R.layout.suggest_group_list_item);
        this.activity = activity;
        setItems(items);
    }

    /**
     * @param inflater
     */
    public GroupListAdapter(final LayoutInflater inflater) {
        this(inflater, null);

    }

    @Override
    public long getItemId(final int position) {
        final String id = String.valueOf(getItem(position).GroupId);
        return !TextUtils.isEmpty(id) ? id.hashCode() : super
                .getItemId(position);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[]{R.id.route1, R.id.route2, R.id.route3, R.id.route4};
    }

    @Override
    protected void update(final int position, final GroupModel groupModel) {

        if (groupModel.GroupMembers != null && groupModel.GroupMembers.size() > 0) {
            ((RouteActivity) activity).getRouteImage(bootstrapImageView(0), groupModel.GroupMembers.get(0).RouteId);
        } else if (groupModel.GroupMembers != null && groupModel.GroupMembers.size() > 1) {
            ((RouteActivity) activity).getRouteImage(bootstrapImageView(1), groupModel.GroupMembers.get(1).RouteId);
        } else if (groupModel.GroupMembers != null && groupModel.GroupMembers.size() > 2) {
            ((RouteActivity) activity).getRouteImage(bootstrapImageView(2), groupModel.GroupMembers.get(2).RouteId);
        } else if (groupModel.GroupMembers != null && groupModel.GroupMembers.size() > 3) {
            ((RouteActivity) activity).getRouteImage(bootstrapImageView(3), groupModel.GroupMembers.get(3).RouteId);
        }
//        setText(0, routeResponse.SrcAddress);
//        setText(1, routeResponse.DstAddress);
//        setText(2, routeResponse.TimingString);
//        setText(3, String.valueOf(routeResponse.AccompanyCount));
//        setText(4, routeResponse.PricingString);
//        setText(5, routeResponse.CarString);
    }


}
