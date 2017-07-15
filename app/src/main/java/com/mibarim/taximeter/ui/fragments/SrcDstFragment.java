package com.mibarim.taximeter.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mibarim.taximeter.BootstrapApplication;
import com.mibarim.taximeter.R;
import com.mibarim.taximeter.models.PathPrice;
import com.mibarim.taximeter.ui.activities.AddMapActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hamed on 3/4/2016.
 */
public class SrcDstFragment extends Fragment implements View.OnTouchListener {

    @Bind(R.id.do_source_btn)
    protected TextView do_source_btn;


    @Bind(R.id.price_shared)
    protected TextView price_shared;
    @Bind(R.id.price_layout_shared)
    protected LinearLayout price_layout_shared;
    @Bind(R.id.price_private)
    protected TextView price_private;
    @Bind(R.id.price_layout_private)
    protected LinearLayout price_layout_private;
    @Bind(R.id.price_line)
    protected TextView price_line;
    @Bind(R.id.price_snapp)
    protected TextView price_snapp;
    @Bind(R.id.price_tap30)
    protected TextView price_tap30;
    @Bind(R.id.price_carpino)
    protected TextView price_carpino;
    @Bind(R.id.price_layout_snapp)
    protected LinearLayout price_layout_snapp;
    @Bind(R.id.price_layout_tap30)
    protected LinearLayout price_layout_tap30;
    @Bind(R.id.price_layout_carpino)
    protected LinearLayout price_layout_carpino;
    @Bind(R.id.price_layout_line)
    protected LinearLayout price_layout_line;
    @Bind(R.id.wait_layout)
    protected LinearLayout wait_layout;

    @Bind(R.id.my_location)
    protected ImageView my_location;

    public SrcDstFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_src_dst, container, false);
        return layout;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        do_source_btn.setOnTouchListener(this);
        my_location.setOnTouchListener(this);

        setAllBlocksInvisible();
        if (getActivity() instanceof AddMapActivity) {
            switch (((AddMapActivity) getActivity()).getSrcDstStateSelector()) {
                case SelectOriginState:
                    do_source_btn.setVisibility(View.VISIBLE);
                    do_source_btn.setText(R.string.do_source);
                    break;
                case SelectDestinationState:
                    do_source_btn.setVisibility(View.VISIBLE);
                    do_source_btn.setText(R.string.do_destination);
                    break;
                default:
                    break;
            }
        }
    }

    private void setAllBlocksInvisible() {
        price_layout_line.setVisibility(View.GONE);
        price_layout_private.setVisibility(View.GONE);
        price_layout_shared.setVisibility(View.GONE);
        price_layout_snapp.setVisibility(View.GONE);
        price_layout_tap30.setVisibility(View.GONE);
        price_layout_carpino.setVisibility(View.GONE);
        wait_layout.setVisibility(View.GONE);
        do_source_btn.setVisibility(View.GONE);
/*        route_path.setVisibility(View.GONE);
        driver_pass_layout.setVisibility(View.GONE);
        time_layout.setVisibility(View.GONE);*/
    }


    public void setPrice(PathPrice thePrice) {
        if (thePrice == null || thePrice.equals("")) {
            //price_line.setText("-");
        } else {
            setAllBlocksInvisible();
            if (thePrice.Tap30PathPrice != null) {
                price_layout_tap30.setVisibility(View.VISIBLE);
                price_tap30.setText(thePrice.Tap30PathPrice);
            }
            if (thePrice.SnappServicePrice != null){
                price_snapp.setText(thePrice.SnappServicePrice);
                price_layout_snapp.setVisibility(View.VISIBLE);
            }
            if (thePrice.CarpinoPathPrice != null){
                price_carpino.setText(thePrice.CarpinoPathPrice);
                price_layout_carpino.setVisibility(View.VISIBLE);
            }
            price_layout_private.setVisibility(View.VISIBLE);
            price_layout_shared.setVisibility(View.VISIBLE);
            price_private.setText(thePrice.PrivateServicePrice);
            price_shared.setText(thePrice.SharedServicePrice);
        }
    }

    public void setWait() {
        setAllBlocksInvisible();
        wait_layout.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.do_source_btn:
                    if (getActivity() instanceof AddMapActivity) {
                        ((AddMapActivity) getActivity()).doBtnClicked();
                    }
                    break;
                case R.id.my_location:
                    if (getActivity() instanceof AddMapActivity) {
                        ((AddMapActivity) getActivity()).gotoMyLocation();
                    }
                    break;
            }
            return true;
        }
        return false;
    }

}

