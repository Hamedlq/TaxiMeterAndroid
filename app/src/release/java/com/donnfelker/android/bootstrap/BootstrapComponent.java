package com.mibarim.taximeter;

import com.mibarim.taximeter.favorite.favorite_map;
import com.mibarim.taximeter.services.GoogleAutocompleteService;
import com.mibarim.taximeter.ui.activities.AboutUsActivity;
import com.mibarim.taximeter.ui.activities.AddMapActivity;
import com.mibarim.taximeter.ui.activities.HelpActivity;
import com.mibarim.taximeter.ui.activities.LocationSearchActivity;
import com.mibarim.taximeter.ui.activities.SplashActivity;
import com.mibarim.taximeter.ui.BootstrapActivity;
import com.mibarim.taximeter.ui.BootstrapFragmentActivity;
import com.mibarim.taximeter.ui.fragments.AboutUsFragment;
import com.mibarim.taximeter.ui.fragments.AddMapFragment;
import com.mibarim.taximeter.ui.fragments.AddressFlagFragment;
import com.mibarim.taximeter.ui.fragments.HelpFragment;
import com.mibarim.taximeter.ui.fragments.LocationListFragment;
import com.mibarim.taximeter.ui.fragments.LocationSearchMainFragment;
import com.mibarim.taximeter.ui.fragments.MainAddMapFragment;
import com.mibarim.taximeter.ui.fragments.SrcDstFragment;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                AndroidModule.class,
                BootstrapModule.class
        }
)
public interface BootstrapComponent {

    void inject(BootstrapApplication target);

    void inject(SplashActivity target);

    void inject(BootstrapFragmentActivity target);

    void inject(BootstrapActivity target);

    void inject(AddMapActivity target);

    void inject(AboutUsActivity target);

    void inject(AboutUsFragment target);

    void inject(AddMapFragment target);

    void inject(AddressFlagFragment target);

    void inject(SrcDstFragment target);

    void inject(MainAddMapFragment target);

    void inject(LocationSearchActivity target);

    void inject(LocationListFragment target);

    void inject(LocationSearchMainFragment target);

    void inject(HelpActivity target);

    void inject(HelpFragment target);

}
