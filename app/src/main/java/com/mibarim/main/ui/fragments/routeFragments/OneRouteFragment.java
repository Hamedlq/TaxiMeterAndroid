package com.mibarim.main.ui.fragments.routeFragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.mibarim.main.BootstrapApplication;
import com.mibarim.main.R;
import com.mibarim.main.activities.NewRouteDelActivity;
import com.mibarim.main.activities.RouteActivity;
import com.mibarim.main.models.Route.RouteResponse;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hamed on 3/4/2016.
 */
public class OneRouteFragment extends Fragment {
    private Context context;
    public OneRouteFragment(){
    }

    @Bind(R.id.src_address)
    protected TextView src_address;
    @Bind(R.id.dst_address)
    protected TextView dst_address;
    @Bind(R.id.timing)
    protected TextView timing;
    @Bind(R.id.pricing)
    protected TextView pricing;
    @Bind(R.id.carString)
    protected TextView carString;
    @Bind(R.id.accompany)
    protected TextView accompany;

    private RouteResponse route;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout=(LinearLayout) inflater.inflate(R.layout.fragment_one_route, container, false);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        route=((RouteActivity)getActivity()).getRoute();
        src_address.setText(route.SrcAddress);
        dst_address.setText(route.DstAddress);
        timing.setText(route.TimingString);
        pricing.setText(route.PricingString);
        carString.setText(route.CarString);
        accompany.setText(String.valueOf(route.AccompanyCount));
    }
}
