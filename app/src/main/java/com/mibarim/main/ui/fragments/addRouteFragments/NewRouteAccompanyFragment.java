package com.mibarim.main.ui.fragments.addRouteFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.api.attributes.BootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.mibarim.main.BootstrapApplication;
import com.mibarim.main.R;
import com.mibarim.main.activities.AddMapActivity;
import com.mibarim.main.models.enums.TimingOptions;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hamed on 3/5/2016.
 */
public class NewRouteAccompanyFragment extends Fragment {

    @Bind(R.id.accompany_count)
    protected EditText accompany_count;
    @Bind(R.id.car_yes)
    protected BootstrapButton car_yes;
    @Bind(R.id.car_no)
    protected BootstrapButton car_no;


    private LinearLayout accompanyLayout;

    public NewRouteAccompanyFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        accompany_count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ((AddMapActivity) getActivity()).routeRequest.AccompanyCount =
                        Integer.parseInt(accompany_count.getText().toString());
            }
        });
        car_yes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((AddMapActivity) getActivity()).routeRequest.IsDrive = true;
                    car_yes.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
                    car_no.setBootstrapBrand(DefaultBootstrapBrand.REGULAR);
                    ((AddMapActivity) getActivity()).showUserPersonalActivity();
                    return true;
                }
                return false;
            }
        });
        car_no.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((AddMapActivity) getActivity()).routeRequest.IsDrive = false;
                    car_yes.setBootstrapBrand(DefaultBootstrapBrand.REGULAR);
                    car_no.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                    ((AddMapActivity) getActivity()).showUserPersonalActivity();
                    return true;
                }
                return false;
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        accompanyLayout = (LinearLayout) inflater.inflate(R.layout.fragment_newroute_accompany, container, false);
        return accompanyLayout;
    }

}
