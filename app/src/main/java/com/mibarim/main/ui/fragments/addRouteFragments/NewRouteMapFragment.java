package com.mibarim.main.ui.fragments.addRouteFragments;

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
import com.mibarim.main.ui.fragments.MapFragment;

/**
 * Created by Hamed on 3/5/2016.
 */
public class NewRouteMapFragment extends Fragment {

    private RelativeLayout mapLayout;

    public NewRouteMapFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mapLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_newroute_map, container, false);
        initScreen();
        return mapLayout;
    }

    private void initScreen() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.map_fragment, new MapFragment())
                .commit();
//        fragmentManager.beginTransaction()
//                .add(R.id.srcdst_fragment, new SrcDstFragment())
//                .commit();
    }
}
