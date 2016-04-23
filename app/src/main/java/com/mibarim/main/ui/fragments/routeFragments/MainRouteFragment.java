package com.mibarim.main.ui.fragments.routeFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mibarim.main.BootstrapApplication;
import com.mibarim.main.R;
import com.mibarim.main.ui.fragments.AddButtonFragment;
import com.mibarim.main.ui.fragments.RouteListFragment;

/**
 * Created by Hamed on 3/5/2016.
 */
public class MainRouteFragment extends Fragment {

    private LinearLayout layout;

    public MainRouteFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = (LinearLayout) inflater.inflate(R.layout.fragment_main_route, container, false);
        initScreen();
        return layout;
    }

    private void initScreen() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.one_route_fragment, new OneRouteFragment())
                .commit();
        fragmentManager.beginTransaction()
                .add(R.id.group_fragment, new GroupFragment())
                .commit();
        fragmentManager.beginTransaction()
                .add(R.id.route_carousel_fragment, new RouteCarouselFragment())
                .commit();
    }
}
