package com.mibarim.taximeter.ui.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.mibarim.taximeter.BootstrapApplication;
import com.mibarim.taximeter.R;
import com.mibarim.taximeter.dataBase.DataBaseFav;
import com.mibarim.taximeter.favorite.favoriteModel;
import com.mibarim.taximeter.models.enums.AddRouteStates;
import com.mibarim.taximeter.ui.activities.AddMapActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hamed on 3/4/2016.
 */
public class AddressFlagFragment extends Fragment {


    @Bind(R.id.address_layout)
    protected RelativeLayout address_layout;

    @Bind(R.id.src_address_editText)
    protected TextView src_address_editText;
    @Bind(R.id.dst_address_editText)
    protected TextView dst_address_editText;

    @Bind(R.id.flag_layout)
    protected RelativeLayout flag_layout;


    @Bind(R.id.src_mid_flag)
    protected TextView src_mid_flag;
    @Bind(R.id.dst_mid_flag)
    protected TextView dst_mid_flag;
    @Bind(R.id.home_mid_flag)
    protected TextView home_mid_flag;
    @Bind(R.id.work_mid_flag)
    protected TextView work_mid_flag;
    @Bind(R.id.src_dst_divide)
    protected View src_dst_divide;

    @Bind(R.id.flagImage)
    protected View flag_image;



    public AddressFlagFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);




    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_address_flag, container, false);
        return layout;


    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        if (((AddMapActivity) getActivity()).getSrcDstStateSelector() == AddRouteStates.SelectOriginState) {
            sourceState();
            src_address_editText.setText(((AddMapActivity) getActivity()).getSrcAddress());

        } else if (((AddMapActivity) getActivity()).getSrcDstStateSelector() == AddRouteStates.SelectDestinationState) {
            destinationState();
            src_address_editText.setText(((AddMapActivity) getActivity()).getSrcAddress());
            dst_address_editText.setText(((AddMapActivity) getActivity()).getDstAddress());
        } else if (((AddMapActivity) getActivity()).getSrcDstStateSelector() == AddRouteStates.SelectPriceState) {
            priceState();
        }
        address_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((AddMapActivity) getActivity()).gotoLocationActivity();
                    return true;
                }
                return false;
            }
        });

        flag_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof AddMapActivity) {
                    ((AddMapActivity) getActivity()).doBtnClicked();
                }
            }
        });

//        favMap1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((AddMapActivity) getActivity()).MoveMapFragment(items.get(0).getLat(), items.get(0).getLng());
//            }
//        });
//
//        favMap2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((AddMapActivity) getActivity()).MoveMapFragment(items.get(1).getLat(), items.get(1).getLng());
//            }
//        });
//
//        favMap3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((AddMapActivity) getActivity()).MoveMapFragment(items.get(2).getLat(), items.get(2).getLng());
//            }
//        });


    }

//    public void favOnMainMap() {
//        favMap = (LinearLayout) getActivity().findViewById(R.id.favorite_on_map);
//        favMap1 = (LinearLayout) getActivity().findViewById(R.id.favorite_on_map_first);
//        favMap2 = (LinearLayout) getActivity().findViewById(R.id.favorite_on_map_second);
//        favMap3 = (LinearLayout) getActivity().findViewById(R.id.favorite_on_map_third);
//        favText1 = (TextView) getActivity().findViewById(R.id.text_fav_on_map_first);
//        favText2 = (TextView) getActivity().findViewById(R.id.text_fav_on_map_second);
//        favText3 = (TextView) getActivity().findViewById(R.id.text_fav_on_map_third);
//
//        db = new DataBaseFav(getActivity());
//        items = db.getAllItems();
//        if (items.size() == 0)
//            favMap.setVisibility(View.GONE);
//        else if (items.size() == 1) {
//            favMap.setVisibility(View.VISIBLE);
//            favMap1.setVisibility(View.VISIBLE);
//            favMap2.setVisibility(View.GONE);
//            favMap3.setVisibility(View.GONE);
//            favText1.setText(items.get(0).getFavPlace());
//        } else if (items.size() == 2) {
//            favMap.setVisibility(View.VISIBLE);
//            favMap1.setVisibility(View.VISIBLE);
//            favMap2.setVisibility(View.VISIBLE);
//            favMap3.setVisibility(View.GONE);
//            favText1.setText(items.get(0).getFavPlace());
//            favText2.setText(items.get(1).getFavPlace());
//        } else {
//            favMap.setVisibility(View.VISIBLE);
//            favMap1.setVisibility(View.VISIBLE);
//            favMap2.setVisibility(View.VISIBLE);
//            favMap3.setVisibility(View.VISIBLE);
//            favText1.setText(items.get(0).getFavPlace());
//            favText2.setText(items.get(1).getFavPlace());
//            favText3.setText(items.get(2).getFavPlace());
//        }
//
//
//    }

    private void ReturnHomeState() {
        flag_layout.setVisibility(View.VISIBLE);
        src_dst_divide.setVisibility(View.VISIBLE);
        dst_address_editText.setVisibility(View.VISIBLE);
        src_address_editText.setTypeface(null, Typeface.NORMAL);
        src_address_editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        dst_address_editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        dst_address_editText.setTypeface(null, Typeface.BOLD);
        InvisibleAllFlag();
        home_mid_flag.setVisibility(View.VISIBLE);
    }

    private void ReturnWorkState() {
        flag_layout.setVisibility(View.VISIBLE);
        src_dst_divide.setVisibility(View.GONE);
        dst_address_editText.setVisibility(View.GONE);
        src_address_editText.setTypeface(null, Typeface.BOLD);
        src_address_editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        InvisibleAllFlag();
        work_mid_flag.setVisibility(View.VISIBLE);
    }

    private void priceState() {
        flag_layout.setVisibility(View.GONE);
        address_layout.setVisibility(View.GONE);
    }

    private void InvisibleAllFlag() {
        src_mid_flag.setVisibility(View.GONE);
        dst_mid_flag.setVisibility(View.GONE);
        home_mid_flag.setVisibility(View.GONE);
        work_mid_flag.setVisibility(View.GONE);
    }

    private void HomeState() {
        flag_layout.setVisibility(View.VISIBLE);
        src_dst_divide.setVisibility(View.GONE);
        dst_address_editText.setVisibility(View.GONE);
        src_address_editText.setTypeface(null, Typeface.BOLD);
        src_address_editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        InvisibleAllFlag();
        home_mid_flag.setVisibility(View.VISIBLE);
    }

    private void WorkState() {
        flag_layout.setVisibility(View.VISIBLE);
        src_dst_divide.setVisibility(View.VISIBLE);
        dst_address_editText.setVisibility(View.VISIBLE);
        src_address_editText.setTypeface(null, Typeface.NORMAL);
        src_address_editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        dst_address_editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        dst_address_editText.setTypeface(null, Typeface.BOLD);
        InvisibleAllFlag();
        work_mid_flag.setVisibility(View.VISIBLE);

    }

    public void waitingState() {
        flag_layout.setVisibility(View.VISIBLE);
    }

    private void requestingState() {
        flag_layout.setVisibility(View.GONE);
    }

    private void sourceState() {
        flag_layout.setVisibility(View.VISIBLE);
        src_dst_divide.setVisibility(View.GONE);
        dst_address_editText.setVisibility(View.GONE);
        src_address_editText.setTypeface(null, Typeface.BOLD);
        src_address_editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        InvisibleAllFlag();
        src_mid_flag.setVisibility(View.VISIBLE);
    }

    private void destinationState() {
        flag_layout.setVisibility(View.VISIBLE);
        src_dst_divide.setVisibility(View.VISIBLE);
        dst_address_editText.setVisibility(View.VISIBLE);
        src_address_editText.setTypeface(null, Typeface.NORMAL);
        src_address_editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        dst_address_editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        dst_address_editText.setTypeface(null, Typeface.BOLD);
        InvisibleAllFlag();
        dst_mid_flag.setVisibility(View.VISIBLE);
    }

    private void eventSourceState() {
        flag_layout.setVisibility(View.VISIBLE);
        src_dst_divide.setVisibility(View.VISIBLE);
        dst_address_editText.setVisibility(View.VISIBLE);
        src_address_editText.setTypeface(null, Typeface.BOLD);
        src_address_editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        dst_address_editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        dst_address_editText.setTypeface(null, Typeface.NORMAL);
        InvisibleAllFlag();
        src_mid_flag.setVisibility(View.VISIBLE);
    }

}
