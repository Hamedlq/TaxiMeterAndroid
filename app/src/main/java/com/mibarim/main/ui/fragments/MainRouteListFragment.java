package com.mibarim.main.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.mibarim.main.BootstrapApplication;
import com.mibarim.main.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hamed on 3/5/2016.
 */
public class MainRouteListFragment extends Fragment {

    private RelativeLayout layout;

    @Bind(R.id.refresh_btn_fragment)
    protected FrameLayout refresh_btn_fragment;

    public MainRouteListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = (RelativeLayout) inflater.inflate(R.layout.fragment_main_route_list, container, false);
        initScreen();
        return layout;
    }

    private void initScreen() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.route_list_fragment, new RouteListFragment())
                .commit();
        fragmentManager.beginTransaction()
                .replace(R.id.refresh_btn_fragment, new RefreshButtonFragment())
                .commit();
        fragmentManager.beginTransaction()
                .replace(R.id.add_btn_fragment, new AddButtonFragment())
                .commit();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        refresh_btn_fragment.setVisibility(View.GONE);
    }

    public void showRefreshBtn(){
        refresh_btn_fragment.setVisibility(View.VISIBLE);
    }

    public void hideRefreshBtn(){
        refresh_btn_fragment.setVisibility(View.GONE);
    }

}
