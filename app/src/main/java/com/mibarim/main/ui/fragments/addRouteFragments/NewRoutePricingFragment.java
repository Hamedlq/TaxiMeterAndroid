package com.mibarim.main.ui.fragments.addRouteFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.mibarim.main.BootstrapApplication;
import com.mibarim.main.R;
import com.mibarim.main.activities.AddMapActivity;
import com.mibarim.main.models.enums.PricingOptions;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hamed on 3/5/2016.
 */
public class NewRoutePricingFragment extends Fragment implements View.OnClickListener {

    private LinearLayout pricingLayout;

    @Bind(R.id.min_max_layout)
    protected LinearLayout min_max_layout;

    @Bind(R.id.label_no_matter)
    protected TextView label_no_matter;
    @Bind(R.id.label_min_max)
    protected TextView label_min_max;
    @Bind(R.id.label_free)
    protected TextView label_free;

    @Bind(R.id.min_max_price)
    protected EditText min_max_price;

    @Bind(R.id.fa_no_matter)
    protected AwesomeTextView fa_no_matter;
    @Bind(R.id.fa_min_max)
    protected AwesomeTextView fa_min_max;
    @Bind(R.id.fa_free)
    protected AwesomeTextView fa_free;
    @Bind(R.id.fa_no_matter_icon)
    protected AwesomeTextView fa_no_matter_icon;
    @Bind(R.id.fa_min_max_icon)
    protected AwesomeTextView fa_min_max_icon;
    @Bind(R.id.fa_free_icon)
    protected AwesomeTextView fa_free_icon;


    public NewRoutePricingFragment() {
    }


    protected void UnSelectAll() {
        fa_no_matter.setVisibility(View.GONE);
        fa_min_max.setVisibility(View.GONE);
        fa_free.setVisibility(View.GONE);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        pricingLayout = (LinearLayout) inflater.inflate(R.layout.fragment_newroute_pricing, container, false);

        return pricingLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());


        label_no_matter.setOnClickListener(this);
        label_min_max.setOnClickListener(this);
        label_free.setOnClickListener(this);
        fa_no_matter_icon.setOnClickListener(this);
        fa_min_max_icon.setOnClickListener(this);
        fa_free_icon.setOnClickListener(this);


        min_max_layout.setVisibility(LinearLayout.GONE);

        min_max_price.addTextChangedListener(priceChangeListener);
        UnSelectAll();
    }

    protected  void collapseLayers(){
        min_max_layout.setVisibility(LinearLayout.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.label_no_matter:
            case R.id.fa_no_matter_icon:
                if (fa_no_matter.isShown()) {
                    UnSelectAll();
                    collapseLayers();
                    ((AddMapActivity) getActivity()).routeRequest.PricingOption = null;
                } else {
                    UnSelectAll();
                    collapseLayers();
                    fa_no_matter.setVisibility(View.VISIBLE);
                    ((AddMapActivity) getActivity()).routeRequest.PricingOption = PricingOptions.NoMatter;
                }
                break;
            case R.id.label_min_max:
            case R.id.fa_min_max_icon:
                UnSelectAll();
                if (min_max_layout.isShown()) {
                    collapseLayers();
                    ((AddMapActivity) getActivity()).routeRequest.PricingOption = null;
                } else {
                    collapseLayers();
                    fa_min_max.setVisibility(View.VISIBLE);
                    min_max_layout.setVisibility(LinearLayout.VISIBLE);
                    ((AddMapActivity) getActivity()).routeRequest.PricingOption = PricingOptions.MinMax;
                }
                break;
            case R.id.label_free:
            case R.id.fa_free_icon:
                if(fa_free.isShown()){
                    UnSelectAll();
                    collapseLayers();
                    ((AddMapActivity) getActivity()).routeRequest.PricingOption = null;
                }else{
                    UnSelectAll();
                    collapseLayers();
                    fa_free.setVisibility(View.VISIBLE);
                    ((AddMapActivity) getActivity()).routeRequest.PricingOption = PricingOptions.Free;
                }
                break;
        }
    }

    TextWatcher priceChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!min_max_price.getText().toString().equals(""))
                ((AddMapActivity) getActivity()).routeRequest.CostMinMax = Float.parseFloat(min_max_price.getText().toString());

        }
    };
}
