package com.mibarim.main;

import com.mibarim.main.activities.AddMapActivity;
import com.mibarim.main.activities.GroupActivity;
import com.mibarim.main.activities.MapActivity;
import com.mibarim.main.activities.MessagingActivity;
import com.mibarim.main.activities.MobileValidationActivity;
import com.mibarim.main.activities.NewRouteDelActivity;
import com.mibarim.main.activities.RegisterActivity;
import com.mibarim.main.activities.RouteActivity;
import com.mibarim.main.activities.RouteListActivity;
import com.mibarim.main.activities.SuggestGroupActivity;
import com.mibarim.main.activities.SuggestRouteActivity;
import com.mibarim.main.activities.UserInfoActivity;
import com.mibarim.main.activities.UserPersonalActivity;
import com.mibarim.main.authenticator.AuthenticatorActivity;
import com.mibarim.main.core.TimerService;
import com.mibarim.main.ui.BootstrapActivity;
import com.mibarim.main.ui.BootstrapFragmentActivity;
import com.mibarim.main.ui.CheckInsListFragment;
import com.mibarim.main.ui.NavigationDrawerFragment;
import com.mibarim.main.ui.NewsActivity;
import com.mibarim.main.ui.NewsListFragment;
import com.mibarim.main.ui.UserActivity;
import com.mibarim.main.ui.UserListFragment;
import com.mibarim.main.ui.fragments.AddButtonFragment;
import com.mibarim.main.ui.fragments.RefreshButtonFragment;
import com.mibarim.main.ui.fragments.addRouteFragments.AddMapFragment;
import com.mibarim.main.ui.fragments.ConfirmFragment;
import com.mibarim.main.ui.fragments.MainAddMapFragment;
import com.mibarim.main.ui.fragments.MainGroupFragment;
import com.mibarim.main.ui.fragments.MainRouteListFragment;
import com.mibarim.main.ui.fragments.MapFragment;
import com.mibarim.main.ui.fragments.addRouteFragments.NewRouteAccompanyFragment;
import com.mibarim.main.ui.fragments.addRouteFragments.NewRouteMapFragment;
import com.mibarim.main.ui.fragments.addRouteFragments.NewRoutePricingFragment;
import com.mibarim.main.ui.fragments.addRouteFragments.NewRouteTimingFragment;
import com.mibarim.main.ui.fragments.RouteListFragment;
import com.mibarim.main.ui.fragments.addRouteFragments.SrcDstAddFragment;
import com.mibarim.main.ui.fragments.messagingFragments.MainMessagingFragment;
import com.mibarim.main.ui.fragments.messagingFragments.MessageListFragment;
import com.mibarim.main.ui.fragments.messagingFragments.SendMessageFragment;
import com.mibarim.main.ui.fragments.routeFragments.GroupButtonsFragment;
import com.mibarim.main.ui.fragments.routeFragments.GroupFragment;
import com.mibarim.main.ui.fragments.routeFragments.MainRouteFragment;
import com.mibarim.main.ui.fragments.routeFragments.OneRouteFragment;
import com.mibarim.main.ui.fragments.routeFragments.SuggestGroupButtonsFragment;
import com.mibarim.main.ui.fragments.routeFragments.SuggestGroupListFragment;
import com.mibarim.main.ui.fragments.routeFragments.SuggestRouteButtonsFragment;
import com.mibarim.main.ui.fragments.routeFragments.SuggestRouteListFragment;
import com.mibarim.main.ui.fragments.userInfoFragments.CarInfoFragment;
import com.mibarim.main.ui.fragments.userInfoFragments.LicenseFragment;
import com.mibarim.main.ui.fragments.userInfoFragments.UserPersonFragment;

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

    void inject(AuthenticatorActivity target);
    void inject(MobileValidationActivity target);

    void inject(MessagingActivity target);

    void inject(MainMessagingFragment target);

    void inject(MessageListFragment target);

    void inject(SendMessageFragment target);

    void inject(RegisterActivity target);

    void inject(AddMapActivity target);

    void inject(ConfirmFragment target);

    void inject(MapActivity target);

    void inject(NewRouteDelActivity target);

    void inject(NavigationDrawerFragment target);

    void inject(AddMapFragment target);

    void inject(RefreshButtonFragment target);

    void inject(SrcDstAddFragment target);

    void inject(MainAddMapFragment target);

    void inject(TimerService target);

    void inject(CheckInsListFragment target);

    void inject(MapFragment target);

    void inject(NewRouteMapFragment target);

    void inject(NewRouteTimingFragment target);

    void inject(NewRouteAccompanyFragment target);

    void inject(NewRoutePricingFragment target);

    void inject(RouteListActivity target);

    void inject(RouteListFragment target);

    void inject(AddButtonFragment target);

    void inject(MainRouteListFragment target);

    void inject(UserInfoActivity target);

    void inject(UserPersonFragment target);

    void inject(CarInfoFragment target);

    void inject(LicenseFragment target);

    void inject(RouteActivity target);

    void inject(MainRouteFragment target);

    void inject(OneRouteFragment target);

    void inject(GroupFragment target);

    void inject(SuggestRouteListFragment target);

    void inject(SuggestGroupListFragment target);

    void inject(GroupActivity target);

    void inject(MainGroupFragment target);

    void inject(SuggestRouteActivity target);

    void inject(SuggestGroupActivity target);

    void inject(GroupButtonsFragment target);

    void inject(SuggestGroupButtonsFragment target);

    void inject(SuggestRouteButtonsFragment target);


    void inject(UserPersonalActivity target);


    void inject(BootstrapFragmentActivity target);

    void inject(BootstrapActivity target);

    void inject(NewsActivity target);

    void inject(NewsListFragment target);

    void inject(UserActivity target);

    void inject(UserListFragment target);


}
