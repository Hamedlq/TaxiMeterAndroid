package com.mibarim.main.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;

import com.mibarim.main.R;
import com.mibarim.main.models.Route.RouteResponse;
import com.mibarim.main.util.SingleTypeAdapter;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Adapter to display a list of traffic items
 */
public class RouteListAdapter extends SingleTypeAdapter<RouteResponse> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM dd");

    /**
     * @param inflater
     * @param items
     */
    public RouteListAdapter(final LayoutInflater inflater, final List<RouteResponse> items) {
        super(inflater, R.layout.route_list_item);

        setItems(items);
    }

    /**
     * @param inflater
     */
    public RouteListAdapter(final LayoutInflater inflater) {
        this(inflater, null);

    }

    @Override
    public long getItemId(final int position) {
        final String id = String.valueOf(getItem(position).RouteId);
        return !TextUtils.isEmpty(id) ? id.hashCode() : super
                .getItemId(position);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[]{R.id.src_address, R.id.dst_address,R.id.timing,R.id.accompany,R.id.pricing,R.id.carString};
    }

    @Override
    protected void update(final int position, final RouteResponse routeResponse) {

        setText(0, routeResponse.SrcAddress);
        setText(1, routeResponse.DstAddress);
        setText(2, routeResponse.TimingString);
        setText(3, String.valueOf(routeResponse.AccompanyCount));
        setText(4, routeResponse.PricingString);
        setText(5, routeResponse.CarString);
    }

}
