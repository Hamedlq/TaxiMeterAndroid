package com.mibarim.main.ui.fragments.routeFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mibarim.main.R;
import com.mibarim.main.adapters.NewRoutePagerAdapter;
import com.mibarim.main.adapters.RoutePagerAdapter;
import com.viewpagerindicator.TitlePageIndicator;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hamed on 3/5/2016.
 */
public class RouteCarouselFragment extends Fragment {
    @Bind(R.id.nr_header)
    protected TitlePageIndicator indicator;

    @Bind(R.id.cf_pages)
    protected ViewPager pager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.route_fragment_carousel, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ButterKnife.bind(this, getView());

        pager.setAdapter(new RoutePagerAdapter(getResources(), getChildFragmentManager()));
        indicator.setViewPager(pager);
        pager.setCurrentItem(3);

    }
}
