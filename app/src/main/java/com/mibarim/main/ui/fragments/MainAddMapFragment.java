package com.mibarim.main.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.mibarim.main.BootstrapApplication;
import com.mibarim.main.R;
import com.mibarim.main.ui.fragments.addRouteFragments.AddMapFragment;
import com.mibarim.main.ui.fragments.addRouteFragments.NewRouteCarouselFragment;
import com.mibarim.main.ui.fragments.addRouteFragments.SrcDstAddFragment;

/**
 * Created by Hamed on 3/5/2016.
 */
public class MainAddMapFragment extends Fragment {

    private RelativeLayout layout;

    public MainAddMapFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = (RelativeLayout) inflater.inflate(R.layout.fragment_main_add_map, container, false);
        initScreen();
        return layout;
    }

    private void initScreen() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.map_fragment, new AddMapFragment())
                .commit();
        fragmentManager.beginTransaction()
                .replace(R.id.srcdst_fragment, new SrcDstAddFragment())
                .commit();
    }

    public void showFormPanel() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment srcDst_fragment = fragmentManager.findFragmentById(R.id.srcdst_fragment);
        Fragment map_fragment = fragmentManager.findFragmentById(R.id.map_fragment);
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_down, R.anim.slide_up)
                .remove(srcDst_fragment)
                .remove(map_fragment)
                .replace(R.id.newRoute_carousel_fragment, new NewRouteCarouselFragment())
                .addToBackStack(null)
                .commit();
        fragmentManager.executePendingTransactions();

    }


    public void showMesagePanel() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_down, R.anim.slide_up)
                .replace(R.id.message_frgment, new ConfirmFragment())
                .addToBackStack(null)
                .commit();
        fragmentManager.executePendingTransactions();
    }



/*

    public void showWayPointPanel() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment srcDst_fragment = fragmentManager.findFragmentById(R.id.srcdst_fragment);
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_down, R.anim.slide_up)
                .remove(srcDst_fragment)
                //.replace(R.id.waypoint_fragment, new WayPointFragment())
                .addToBackStack(null)
                .commit();
        fragmentManager.executePendingTransactions();
    }

    public void showPriceFormPanel() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment waypointFragment = fragmentManager.findFragmentById(R.id.waypoint_fragment);
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_down, R.anim.slide_up)
                .remove(waypointFragment)
                //.replace(R.id.price_fragment, new AddTaxiPriceFragment())
                .addToBackStack(null)
                .commit();
        fragmentManager.executePendingTransactions();
    }

    public void showMobileConfirmPanel() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment price_fragment = fragmentManager.findFragmentById(R.id.price_fragment);
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_down, R.anim.slide_up)
                .remove(price_fragment)
                //.replace(R.id.mobile_fragment, new ConfirmFragment())
                .addToBackStack(null)
                .commit();
        fragmentManager.executePendingTransactions();
    }

    public void resetRoute() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment waypointFragment = fragmentManager.findFragmentById(R.id.waypoint_fragment);
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_down, R.anim.slide_up)
                .remove(waypointFragment)
                .replace(R.id.srcdst_fragment, new SrcDstAddFragment())
                .addToBackStack(null)
                .commit();
        fragmentManager.executePendingTransactions();
        Fragment mapFragment = fragmentManager.findFragmentById(R.id.map_fragment);
        //((AddMapFragment)mapFragment).ClearRoute();
    }
*/

}
