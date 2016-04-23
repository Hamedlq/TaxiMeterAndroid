package com.mibarim.main.ui.fragments.routeFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mibarim.main.BootstrapApplication;
import com.mibarim.main.R;
import com.mibarim.main.activities.SuggestGroupActivity;
import com.mibarim.main.models.Route.RouteResponse;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hamed on 3/5/2016.
 */
public class SuggestGroupButtonsFragment extends Fragment {

    private LinearLayout layout;

    public SuggestGroupButtonsFragment() {
    }

    @Bind(R.id.join_group)
    protected BootstrapButton join_group;
    @Bind(R.id.delete_group)
    protected BootstrapButton delete_group;


    private RouteResponse routeResponse;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = (LinearLayout) inflater.inflate(R.layout.fragment_suggest_group_buttons, container, false);
        return layout;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        join_group.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((SuggestGroupActivity) getActivity()).joinGroup();
                    return true;
                }
                return false;
            }
        });
        delete_group.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((SuggestGroupActivity) getActivity()).deleteGroupSuggest();
                    return true;
                }
                return false;
            }
        });

    }

}
