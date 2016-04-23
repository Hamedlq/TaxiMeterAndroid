package com.mibarim.main.ui.fragments.routeFragments;

import android.content.Intent;
import android.graphics.Bitmap;
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
public class GroupButtonsFragment extends Fragment {

    private LinearLayout layout;

    public GroupButtonsFragment() {
    }

    @Bind(R.id.leave_group)
    protected BootstrapButton leave_group;
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
        layout = (LinearLayout) inflater.inflate(R.layout.fragment_group_buttons, container, false);
        return layout;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        leave_group.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((GroupActivity) getActivity()).leaveGroup();
                    return true;
                }
                return false;
            }
        });
        appointment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((GroupActivity) getActivity()).goToMessaging();
                    return true;
                }
                return false;
            }
        });


    }

}
