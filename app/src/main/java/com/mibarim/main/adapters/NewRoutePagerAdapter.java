

package com.mibarim.main.adapters;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mibarim.main.R;
import com.mibarim.main.ui.fragments.addRouteFragments.NewRouteAccompanyFragment;
import com.mibarim.main.ui.fragments.addRouteFragments.NewRoutePricingFragment;
import com.mibarim.main.ui.fragments.addRouteFragments.NewRouteTimingFragment;

/**
 * Pager adapter
 */
public class NewRoutePagerAdapter extends FragmentPagerAdapter {

    private final Resources resources;

    /**
     * Create pager adapter
     *
     * @param resources
     * @param fragmentManager
     */
    public NewRoutePagerAdapter(final Resources resources, final FragmentManager fragmentManager) {
        super(fragmentManager);
        this.resources = resources;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(final int position) {
        final Fragment result;
        switch (position) {
            case 0:
                result = new NewRoutePricingFragment();
                break;
            case 1:
                result = new NewRouteAccompanyFragment();
                break;
            case 2:
                result = new NewRouteTimingFragment();
                break;
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
                return resources.getString(R.string.page_pricing);
            case 1:
                return resources.getString(R.string.page_acoompany);
            case 2:
                return resources.getString(R.string.page_timing);
/*
            case 3:

                return resources.getString(R.string.new_route);
*/
            default:
                return null;
        }
    }
}
