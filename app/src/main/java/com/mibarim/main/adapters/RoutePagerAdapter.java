

package com.mibarim.main.adapters;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mibarim.main.R;
import com.mibarim.main.ui.fragments.routeFragments.SuggestGroupListFragment;
import com.mibarim.main.ui.fragments.routeFragments.SuggestRouteListFragment;

/**
 * Pager adapter
 */
public class RoutePagerAdapter extends FragmentPagerAdapter {

    private final Resources resources;

    /**
     * Create pager adapter
     *
     * @param resources
     * @param fragmentManager
     */
    public RoutePagerAdapter(final Resources resources, final FragmentManager fragmentManager) {
        super(fragmentManager);
        this.resources = resources;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(final int position) {
        final Fragment result;
        switch (position) {
            case 0:
                result = new SuggestRouteListFragment();
                break;
            case 1:
                result = new SuggestGroupListFragment();
                break;
//            case 2:
//
//                break;
            /*case 3:
                result = new NewRouteMapFragment();
                break;*/
            default:
                result = null;
                break;
        }
        if (result != null) {
            result.setArguments(new Bundle()); //TODO do we need this?
        }
        return result;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        switch (position) {
            case 0:
                return resources.getString(R.string.suggest_route);
            case 1:
                return resources.getString(R.string.suggest_group);
//            case 2:
//                return resources.getString(R.string.user_info);
/*
            case 3:

                return resources.getString(R.string.new_route);
*/
            default:
                return null;
        }
    }
}
