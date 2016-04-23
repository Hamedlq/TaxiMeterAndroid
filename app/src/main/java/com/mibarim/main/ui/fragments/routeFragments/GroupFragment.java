package com.mibarim.main.ui.fragments.routeFragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaRouter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.mibarim.main.BootstrapApplication;
import com.mibarim.main.R;
import com.mibarim.main.activities.GroupActivity;
import com.mibarim.main.activities.MessagingActivity;
import com.mibarim.main.activities.RouteActivity;
import com.mibarim.main.models.Route.RouteGroupModel;
import com.mibarim.main.models.Route.RouteResponse;
import com.mibarim.main.util.Toaster;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hamed on 3/5/2016.
 */
public class GroupFragment extends Fragment {

    private LinearLayout layout;
    private int RELOAD_REQUEST = 1234;

    public GroupFragment() {
    }

    @Bind(R.id.route1)
    protected BootstrapCircleThumbnail route1;
    @Bind(R.id.route2)
    protected BootstrapCircleThumbnail route2;
    @Bind(R.id.route3)
    protected BootstrapCircleThumbnail route3;
    @Bind(R.id.route4)
    protected BootstrapCircleThumbnail route4;
    @Bind(R.id.appointment)
    protected BootstrapButton appointment;


    private RouteResponse routeResponse;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = (LinearLayout) inflater.inflate(R.layout.fragment_group, container, false);
        routeResponse = ((RouteActivity) getActivity()).getRoute();
        return layout;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        routeResponse = ((RouteActivity) getActivity()).getRoute();
        appointment.setVisibility(View.GONE);
        if (routeResponse.GroupRoutes != null && routeResponse.GroupRoutes.size() > 0) {
            appointment.setVisibility(View.VISIBLE);
        }
        if (routeResponse.GroupRoutes != null && routeResponse.GroupRoutes.size() > 0) {
            RouteGroupModel model = routeResponse.GroupRoutes.get(0);
            ((RouteActivity) getActivity()).getRouteImage(model.RouteId, "GroupFragment");
        }
        if (routeResponse.GroupRoutes != null && routeResponse.GroupRoutes.size() > 1) {
            RouteGroupModel model = routeResponse.GroupRoutes.get(1);
            ((RouteActivity) getActivity()).getRouteImage(model.RouteId, "GroupFragment");
        }
        if (routeResponse.GroupRoutes != null && routeResponse.GroupRoutes.size() > 2) {
            RouteGroupModel model = routeResponse.GroupRoutes.get(2);
            ((RouteActivity) getActivity()).getRouteImage(model.RouteId, "GroupFragment");
        }
        if (routeResponse.GroupRoutes != null && routeResponse.GroupRoutes.size() > 3) {
            RouteGroupModel model = routeResponse.GroupRoutes.get(3);
            ((RouteActivity) getActivity()).getRouteImage(model.RouteId, "GroupFragment");
        }

        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (routeResponse.GroupRoutes != null && routeResponse.GroupRoutes.size() > 0) {
                        Intent intent = new Intent(getActivity(), GroupActivity.class);
                        intent.putExtra("RouteResponse", routeResponse);
                        getActivity().startActivityForResult(intent, RELOAD_REQUEST);
                    } else {
                        Toaster.showLong(getActivity(), getString(R.string.no_group),R.drawable.toast_info);
                    }
                }
                return true;
            }
        };
        layout.setOnTouchListener(touchListener);
        route1.setOnTouchListener(touchListener);
        route2.setOnTouchListener(touchListener);
        route3.setOnTouchListener(touchListener);
        route4.setOnTouchListener(touchListener);
        appointment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(getActivity(), MessagingActivity.class);
                    intent.putExtra("RouteResponse", routeResponse);
                    getActivity().startActivity(intent);
                    return true;
                }
                return false;
            }
        });

    }


    public void setRouteImage(long routeId, Bitmap decodedByte) {
        if (routeResponse.GroupRoutes.size() > 0 && routeResponse.GroupRoutes.get(0).RouteId == routeId) {
            route1.setImageBitmap(decodedByte);
        }
        if (routeResponse.GroupRoutes.size() > 1 && routeResponse.GroupRoutes.get(1).RouteId == routeId) {
            route1.setImageBitmap(decodedByte);
        }
        if (routeResponse.GroupRoutes.size() > 2 && routeResponse.GroupRoutes.get(2).RouteId == routeId) {
            route1.setImageBitmap(decodedByte);
        }
        if (routeResponse.GroupRoutes.size() > 3 && routeResponse.GroupRoutes.get(3).RouteId == routeId) {
            route1.setImageBitmap(decodedByte);
        }

    }
}
