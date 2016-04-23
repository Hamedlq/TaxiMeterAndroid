package com.mibarim.main.ui.fragments.userInfoFragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mibarim.main.R;
import com.mibarim.main.adapters.NewRoutePagerAdapter;
import com.mibarim.main.adapters.UserInfoPagerAdapter;
import com.mibarim.main.models.CarInfoModel;
import com.mibarim.main.models.LicenseInfoModel;
import com.mibarim.main.models.PersonalInfoModel;
import com.viewpagerindicator.TitlePageIndicator;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hamed on 3/5/2016.
 */
public class UserInfoCarouselFragment extends Fragment {
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

        pager.setAdapter(new UserInfoPagerAdapter(getResources(), getChildFragmentManager()));
        indicator.setViewPager(pager);
        pager.setCurrentItem(3);

    }

    public void setUserPic(Bitmap pic) {
        final FragmentManager fragmentManager = getChildFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("android:switcher:" + R.id.cf_pages + ":2");
        ((UserPersonFragment) fragment).setUserImage(pic);
    }

    public void setLicensePic(Bitmap pic) {
        final FragmentManager fragmentManager = getChildFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("android:switcher:" + R.id.cf_pages + ":1");
        ((LicenseFragment) fragment).setLicenseImage(pic);
    }

    public void setCarPic(Bitmap pic) {
        final FragmentManager fragmentManager = getChildFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("android:switcher:" + R.id.cf_pages + ":0");
        ((CarInfoFragment) fragment).setCarImage(pic);
    }

    public void setCarBkPic(Bitmap pic) {
        final FragmentManager fragmentManager = getChildFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("android:switcher:" + R.id.cf_pages + ":0");
        ((CarInfoFragment) fragment).setCarBkImage(pic);
    }


    public PersonalInfoModel getUserPersonalInfo() {
        final FragmentManager fragmentManager = getChildFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("android:switcher:" + R.id.cf_pages + ":2");
        PersonalInfoModel model = ((UserPersonFragment) fragment).getUserInfo();
        return model;
    }

    public CarInfoModel getCarInfo() {
        final FragmentManager fragmentManager = getChildFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("android:switcher:" + R.id.cf_pages + ":0");
        CarInfoModel model=null;
        if (fragment != null) {
            model = ((CarInfoFragment) fragment).getCarInfo();
        }
        return model;
    }

    public LicenseInfoModel getLicenseInfo() {
        final FragmentManager fragmentManager = getChildFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("android:switcher:" + R.id.cf_pages + ":1");
        LicenseInfoModel model = ((LicenseFragment) fragment).getLicenseInfo();
        return model;
    }
}
