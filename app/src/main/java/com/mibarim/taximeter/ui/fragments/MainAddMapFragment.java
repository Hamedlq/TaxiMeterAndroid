package com.mibarim.taximeter.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.mibarim.taximeter.BootstrapApplication;
import com.mibarim.taximeter.R;
import com.mibarim.taximeter.models.PathPrice;
import com.mibarim.taximeter.ui.activities.AddMapActivity;

import butterknife.ButterKnife;

/**
 * Created by Hamed on 3/5/2016.
 */
public class MainAddMapFragment extends Fragment {

    private RelativeLayout layout;
/*
    @Bind(R.id.info_submit_fragment)
    protected FrameLayout info_submit_fragment;
*/

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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, layout);

    }

    private void initScreen() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.map_fragment, new AddMapFragment())
                .commit();
        fragmentManager.beginTransaction()
                .replace(R.id.route_src_dst_fragment, new SrcDstFragment())
                .commit();
        fragmentManager.beginTransaction()
                .replace(R.id.address_flag_fragment, new AddressFlagFragment())
                .commit();
//        fragmentManager.beginTransaction()
//                .replace(R.id.info_submit_fragment, new InfoMessageFragment())
//                .commit();

    }

    public void RebuildAddressFragment() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.address_flag_fragment, new AddressFlagFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    public void waitingAddress() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.address_flag_fragment);
        if (fragment instanceof AddressFlagFragment) {
            ((AddressFlagFragment) fragment).waitingState();
        }

    }

    public void ShowSrcFragment() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        //removeDstFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.map_fragment, new AddMapFragment())
                .replace(R.id.address_flag_fragment, new AddressFlagFragment())
                .addToBackStack(null)
                .commit();
        /*fragmentManager.executePendingTransactions();
        Fragment mapFragment = fragmentManager.findFragmentById(R.id.map_fragment);
        ((AddMapFragment) mapFragment).SourceState();*/

    }

    public void RebuildSrcDstFragment() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.route_src_dst_fragment, new SrcDstFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss();
        //fragmentManager.executePendingTransactions();
    }

    public void hideSourceFragment() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        removeMapFragment();
//        removeSrcFragment();
        removeAddressFlagFragment();
        /*fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_down, R.anim.slide_up)
                .replace(R.id.event_list_fragment, new EventListFragment())
                .addToBackStack(null)
                .commit();
        fragmentManager.executePendingTransactions();*/
    }


    private void removeRequestingFragment() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        //Fragment requestingFragment = fragmentManager.findFragmentById(R.id.newRoute_requesting_fragment);
        //if (requestingFragment instanceof RequestingFragment) {
//            fragmentManager.beginTransaction()
//                    .remove(requestingFragment)
//                    .commit();
        //}
    }

    private void removeMapFragment() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment mapFragment = fragmentManager.findFragmentById(R.id.map_fragment);
        if (mapFragment instanceof AddMapFragment) {
            fragmentManager.beginTransaction()
                    .remove(mapFragment)
                    .commit();
        }
    }

    private void removeSrcFragment() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        //Fragment srcFragment = fragmentManager.findFragmentById(R.id.main_carousel_fragment);
        /*if (srcFragment instanceof MainCarouselFragment) {
            fragmentManager.beginTransaction()
                    .remove(srcFragment)
                    .commit();
        }*/
    }

    private void removeAddressFlagFragment() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment addressFlagFragment = fragmentManager.findFragmentById(R.id.address_flag_fragment);
        if (addressFlagFragment instanceof AddressFlagFragment) {
            fragmentManager.beginTransaction()
                    .remove(addressFlagFragment)
                    .commit();
        }
    }


    public void MoveMap(String lat, String lng) {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.executePendingTransactions();
        Fragment mapFragment = fragmentManager.findFragmentById(R.id.map_fragment);
        ((AddMapFragment) mapFragment).MoveMap(lat, lng);
    }

    public void RebuildFragment() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.map_fragment, new AddMapFragment())
                .replace(R.id.route_src_dst_fragment, new SrcDstFragment())
                .replace(R.id.address_flag_fragment, new AddressFlagFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void ReDrawAddMapFragment() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.map_fragment);
        ((AddMapFragment) fragment).reDrawRoutePaths();
    }

    public void RebuildDstFragment() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.route_src_dst_fragment, new SrcDstFragment())
                .replace(R.id.address_flag_fragment, new AddressFlagFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
        Fragment mapFragment = fragmentManager.findFragmentById(R.id.map_fragment);
        ((AddMapFragment) mapFragment).DestinationState();
    }

    public void RebuildDstFragment(String lat, String lng) {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.route_src_dst_fragment, new SrcDstFragment())
                .replace(R.id.address_flag_fragment, new AddressFlagFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
        Fragment mapFragment = fragmentManager.findFragmentById(R.id.map_fragment);
        ((AddMapFragment) mapFragment).DestinationState(lat, lng);
    }

    public void setPrice(PathPrice pathPrice) {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.route_src_dst_fragment);
        if (fragment instanceof SrcDstFragment) {
            ((SrcDstFragment) fragment).setPrice(getString(R.string.private_price), pathPrice, R.mipmap.taxitehran);
        }
        Fragment mapFragment = fragmentManager.findFragmentById(R.id.map_fragment);
        ((AddMapFragment) mapFragment).setPriceState(pathPrice.PathRoute);
    }

    public void setMibarimPrice(PathPrice mibarimPrice) {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.route_src_dst_fragment);
        if (fragment instanceof SrcDstFragment) {
            ((SrcDstFragment) fragment).setMibarimPrice(getString(R.string.shared_price), mibarimPrice, R.mipmap.mibarim_icon);
        }
    }

    public void setSnappPrice(String snappPrice, int service) {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.route_src_dst_fragment);
        if (fragment instanceof SrcDstFragment) {
            if (service == 0) {
                ((SrcDstFragment) fragment).setSnappPrice(getString(R.string.snapp_echo_price), snappPrice, R.mipmap.snapp_icon);
            } else if (service == 1) {
                ((SrcDstFragment) fragment).setSnappPrice(getString(R.string.snapp_rose_price), snappPrice, R.mipmap.snapp_icon);
            } else if (service == 3) {
                ((SrcDstFragment) fragment).setSnappPrice(getString(R.string.snapp_bike_price), snappPrice, R.mipmap.snapp_icon);
            }
        }

    }

    public void setTap30Price(String tap30Price) {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.route_src_dst_fragment);
        if (fragment instanceof SrcDstFragment) {
            ((SrcDstFragment) fragment).setTap30Price(getString(R.string.tap30_price), tap30Price, R.mipmap.tap30_icon);
        }
    }

    public void setCarpinoPrice(String carpinoPrice) {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.route_src_dst_fragment);
        if (fragment instanceof SrcDstFragment) {
            ((SrcDstFragment) fragment).setCarpinoPrice(getString(R.string.carpino_price), carpinoPrice, R.mipmap.carpino_icon);
        }
    }

    public void setAlopeyk(String alopeykPrice) {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.route_src_dst_fragment);
        if (fragment instanceof SrcDstFragment) {
            ((SrcDstFragment) fragment).setAlopeykPrice(getString(R.string.alopeyk_price), alopeykPrice, R.mipmap.alopeyk_icon);
        }
    }

    public void setMaxim(String maximPrice, int service) {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.route_src_dst_fragment);
        if (fragment instanceof SrcDstFragment) {
            if (service == 0) {
                ((SrcDstFragment) fragment).setMaximPrice(getString(R.string.maxim_echo_price), maximPrice.replace("تومان", ""), R.mipmap.maxim_icon);
            } else if (service == 1) {
                ((SrcDstFragment) fragment).setMaximPrice(getString(R.string.maxim_luxury_price), maximPrice.replace("تومان", ""), R.mipmap.maxim_icon);
            } else if (service == 2) {
                ((SrcDstFragment) fragment).setMaximPrice(getString(R.string.maxim_women_price), maximPrice.replace("تومان", ""), R.mipmap.maxim_icon);
            } else if (service == 3) {
                ((SrcDstFragment) fragment).setMaximPrice(getString(R.string.maxim_commercial_price), maximPrice.replace("تومان", ""), R.mipmap.maxim_icon);
            }
        }
    }

    public void SetWaitState() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.route_src_dst_fragment);
        if (fragment instanceof SrcDstFragment) {
            ((SrcDstFragment) fragment).setWait();
        }
    }
}
