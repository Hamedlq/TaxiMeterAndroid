package com.mibarim.taximeter.ui.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mibarim.taximeter.BootstrapApplication;
import com.mibarim.taximeter.R;
import com.mibarim.taximeter.adapters.PricesAdapter;
import com.mibarim.taximeter.models.PathPrice;
import com.mibarim.taximeter.models.PriceListModel;
import com.mibarim.taximeter.ui.OpeningDialogTheme;
import com.mibarim.taximeter.ui.activities.AddMapActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Hamed on 3/4/2016.
 */
public class SrcDstFragment extends Fragment {

    @Bind(R.id.do_source_btn)
    protected Button do_source_btn;
    @Bind(R.id.up_down_arrow)
    protected ImageView upDown;
    //    @Bind(R.id.price_shared)
//    protected TextView price_shared;
//    @Bind(R.id.price_layout_shared)
//    protected LinearLayout price_layout_shared;
//    @Bind(R.id.price_private)
//    protected TextView price_private;
//    @Bind(R.id.price_layout_private)
//    protected LinearLayout price_layout_private;
//    @Bind(R.id.price_line)
//    protected TextView price_line;
//    @Bind(R.id.price_snapp)
//    protected TextView price_snapp;
//    @Bind(R.id.price_tap30)
//    protected TextView price_tap30;
//    @Bind(R.id.price_carpino)
//    protected TextView price_carpino;
//    @Bind(R.id.price_layout_snapp)
//    protected LinearLayout price_layout_snapp;
//    @Bind(R.id.price_layout_tap30)
//    protected LinearLayout price_layout_tap30;
//    @Bind(R.id.price_layout_carpino)
//    protected LinearLayout price_layout_carpino;
//    @Bind(R.id.price_layout_line)
//    protected LinearLayout price_layout_line;
    @Bind(R.id.waiting_layout)
    protected LinearLayout wait_layout;

    @Bind(R.id.my_location)
    protected ImageView my_location;
    @Bind(R.id.bottom_sheet)
    protected LinearLayout bottom_sheet;
    @Bind(R.id.fav_place)
    protected ImageView fav_place;
    BottomSheetBehavior bottomSheetBehavior;
    onItemClickListener onItemClickListener;
    private List<PriceListModel> priceModel;
    private PricesAdapter adapter;
    private RecyclerView priceLayout;
    private OpeningDialogTheme dialog;
    private Animation animation;
    private boolean isCollapsed;
    private List<String> PriceOrders;

    public SrcDstFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
        PriceOrders = ((AddMapActivity) getActivity()).getPriceOrderList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isCollapsed = true;
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_src_dst, container, false);
        priceLayout = (RecyclerView) layout.findViewById(R.id.price_layout);
        animation = new AlphaAnimation(1, 0.3f);
        animation.setDuration(1000);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(10);
        animation.setRepeatMode(Animation.REVERSE);
        onItemClickListener = new onItemClickListener() {
            @Override
            public void onItemClicked(int position, List<PriceListModel> list) {
                if (isCollapsed)
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                else {
                    String serviceName = list.get(position).getServiceName();
                    if (serviceName.contains("اسنپ")) {
                        dialog = new OpeningDialogTheme(getActivity());
                        dialog.show();
                        dialog.setText("اسنپ", "آیا تمایل به باز کردن این برنامه دارید؟");

                        dialog.yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i;
                                PackageManager manager = getActivity().getPackageManager();
                                dialog.dismiss();
                                try {
                                    i = manager.getLaunchIntentForPackage("cab.snapp.passenger");
                                    if (i == null)
                                        throw new PackageManager.NameNotFoundException();
                                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                                    startActivity(i);
                                } catch (PackageManager.NameNotFoundException e) {
                                    try {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "cab.snapp.passenger")));
                                    } catch (Exception e1) {
                                        Toast.makeText(getActivity(), "هیج مارکتی برروی موبایل شما نصب نیست", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                        dialog.no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    } else if (serviceName.contains("تپسی")) {
                        dialog = new OpeningDialogTheme(getActivity());
                        dialog.show();
                        dialog.setText("تپسی", "آیا تمایل به باز کردن این برنامه دارید؟");

                        dialog.yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i;
                                PackageManager manager = getActivity().getPackageManager();
                                dialog.dismiss();
                                try {
                                    i = manager.getLaunchIntentForPackage("taxi.tap30.passenger");
                                    if (i == null)
                                        throw new PackageManager.NameNotFoundException();
                                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                                    startActivity(i);
                                } catch (PackageManager.NameNotFoundException e) {
                                    try {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "taxi.tap30.passenger")));
                                    } catch (Exception e1) {
                                        Toast.makeText(getActivity(), "هیج مارکتی برروی موبایل شما نصب نیست", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                        dialog.no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    } else if (serviceName.contains("کارپینو")) {
                        dialog = new OpeningDialogTheme(getActivity());
                        dialog.show();
                        dialog.setText("کارپینو", "آیا تمایل به باز کردن این برنامه دارید؟");

                        dialog.yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i;
                                PackageManager manager = getActivity().getPackageManager();
                                dialog.dismiss();
                                try {
                                    i = manager.getLaunchIntentForPackage("com.radnik.carpino.passenger");
                                    if (i == null)
                                        throw new PackageManager.NameNotFoundException();
                                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                                    startActivity(i);
                                } catch (PackageManager.NameNotFoundException e) {
                                    try {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.radnik.carpino.passenger")));
                                    } catch (Exception e1) {
                                        Toast.makeText(getActivity(), "هیج مارکتی برروی موبایل شما نصب نیست", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                        dialog.no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    } else if (serviceName.contains("الو")) {
                        dialog = new OpeningDialogTheme(getActivity());
                        dialog.show();
                        dialog.setText("الو\u200Cپیک", "آیا تمایل به باز کردن این برنامه دارید؟");

                        dialog.yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i;
                                PackageManager manager = getActivity().getPackageManager();
                                dialog.dismiss();
                                try {
                                    i = manager.getLaunchIntentForPackage("com.alopeyk.customer");
                                    if (i == null)
                                        throw new PackageManager.NameNotFoundException();
                                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                                    startActivity(i);
                                } catch (PackageManager.NameNotFoundException e) {
                                    try {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.alopeyk.customer")));
                                    } catch (Exception e1) {
                                        Toast.makeText(getActivity(), "هیج مارکتی برروی موبایل شما نصب نیست", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                        dialog.no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    } else if (serviceName.contains("ماکسیم")) {
                        dialog = new OpeningDialogTheme(getActivity());
                        dialog.show();
                        dialog.setText("ماکسیم", "آیا تمایل به باز کردن این برنامه دارید؟");

                        dialog.yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i;
                                PackageManager manager = getActivity().getPackageManager();
                                dialog.dismiss();
                                try {
                                    i = manager.getLaunchIntentForPackage("com.taxsee.taxsee");
                                    if (i == null)
                                        throw new PackageManager.NameNotFoundException();
                                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                                    startActivity(i);
                                } catch (PackageManager.NameNotFoundException e) {
                                    try {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.taxsee.taxsee")));
                                    } catch (Exception e1) {
                                        Toast.makeText(getActivity(), "هیج مارکتی برروی موبایل شما نصب نیست", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                        dialog.no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    } else if (serviceName.contains("می‌بریم")) {
                        dialog = new OpeningDialogTheme(getActivity());
                        dialog.show();
                        dialog.setText("تاکسی اشتراکی می\u200Cبریم", "آیا تمایل به باز کردن این برنامه دارید؟");

                        dialog.yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i;
                                PackageManager manager = getActivity().getPackageManager();
                                dialog.dismiss();
                                try {
                                    i = manager.getLaunchIntentForPackage("com.mibarim.main");
                                    if (i == null)
                                        throw new PackageManager.NameNotFoundException();
                                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                                    startActivity(i);
                                } catch (PackageManager.NameNotFoundException e) {
                                    try {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.mibarim.main")));
                                    } catch (Exception e1) {
                                        Toast.makeText(getActivity(), "هیج مارکتی برروی موبایل شما نصب نیست", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                        dialog.no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                }
            }
        };
        return layout;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(getActivity());
        priceLayout.setLayoutManager(mLayoutManager);
        ButterKnife.bind(this, getView());
        priceModel = new ArrayList<>();
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        isCollapsed = true;
                        upDown.animate().rotation(0);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        isCollapsed = false;
                        upDown.animate().rotation(180);
                        break;
                }
                upDown.clearAnimation();
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        int pixle = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                245, getResources().getDisplayMetrics());
        bottomSheetBehavior.setPeekHeight(pixle);

        upDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED)
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        do_source_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof AddMapActivity) {
                    ((AddMapActivity) getActivity()).doBtnClicked();
                }
            }
        });
        my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof AddMapActivity) {
                    ((AddMapActivity) getActivity()).gotoMyLocation();
                }
            }
        });

        fav_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AddMapActivity) getActivity()).gotoFavorite();
            }
        });

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        my_location.setVisibility(View.VISIBLE);
    }

    public boolean closeBottomSheet() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            return false;
        }
        return true;
    }

    private void setAllBlocksInvisible() {
        bottom_sheet.setVisibility(View.GONE);
        wait_layout.setVisibility(View.GONE);
        do_source_btn.setVisibility(View.GONE);
    }


    public void setPrice(PathPrice thePrice) {
        if (thePrice == null || thePrice.equals("")) {
            //price_line.setText("-");
        } else {
            setAllBlocksInvisible();
//            price_layout_private.setVisibility(View.VISIBLE);
//            price_layout_shared.setVisibility(View.VISIBLE);
//            price_private.setText(thePrice.PrivateServicePrice);
//            price_shared.setText(thePrice.SharedServicePrice);
        }
    }

    public void setMibarimPrice(String serviceName, PathPrice thePrice, int icon) {
        if (thePrice == null || thePrice.equals("")) {
            //price_line.setText("-");
        } else {
            PriceListModel model = new PriceListModel(serviceName, thePrice.SharedServicePrice, icon, PriceListModel.serviceId.MIBARIM);
            if (adapter == null) {
                priceModel.add(model);
                adapter = new PricesAdapter(getActivity(), priceModel, onItemClickListener);
                priceLayout.setAdapter(adapter);
            } else {
                for (int i = 0; i <= priceModel.size(); i++) {
                    if (i == priceModel.size()) {
                        priceModel.add(model);
                        break;
                    } else if (Integer.parseInt(PriceOrders.get(priceModel.get(i).id.getValue() - 1)) >= Integer.parseInt(PriceOrders.get(model.id.getValue() - 1))) {
                        priceModel.add(i, model);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }
            if (bottom_sheet.getVisibility() != View.VISIBLE)
                bottom_sheet.setVisibility(View.VISIBLE);

            if (my_location.getVisibility() == View.VISIBLE)
                my_location.setVisibility(View.GONE);

            if (upDown.getAnimation() == null)
                upDown.startAnimation(animation);
        }
    }

    public void setSnappPrice(String serviceName, String price, int icon) {
        int snappPriceToman;
        try {
            snappPriceToman = Integer.parseInt(price);
            snappPriceToman = snappPriceToman / 10;
        } catch (NumberFormatException nfe) {
            snappPriceToman = 0;
        }
        price = String.valueOf(snappPriceToman);
        PriceListModel model;
        if (serviceName.matches(getString(R.string.snapp_echo_price)))
            model = new PriceListModel(serviceName, price, icon, PriceListModel.serviceId.SNAPP);
        else
            model = new PriceListModel(serviceName, price, icon, PriceListModel.serviceId.OTHERS);
        if (adapter == null) {
            priceModel.add(model);
            adapter = new PricesAdapter(getActivity(), priceModel, onItemClickListener);
            priceLayout.setAdapter(adapter);
        } else {
            if (serviceName.matches(getString(R.string.snapp_echo_price)))
                for (int i = 0; i <= priceModel.size(); i++) {
                    if (i == priceModel.size()) {
                        priceModel.add(model);
                        break;
                    } else if (Integer.parseInt(PriceOrders.get(priceModel.get(i).id.getValue() - 1)) >= Integer.parseInt(PriceOrders.get(model.id.getValue() - 1))) {
                        priceModel.add(i, model);
                        break;
                    }
                }
            else
                for (int i = 0; i <= priceModel.size(); i++) {
                    if (i == priceModel.size()) {
                        priceModel.add(model);
                        break;
                    } else if (Integer.parseInt(PriceOrders.get(priceModel.get(i).id.getValue() - 1)) >= Integer.parseInt(PriceOrders.get(model.id.getValue() - 1))) {
                        priceModel.add(i, model);
                        break;
                    }
                }
            adapter.notifyDataSetChanged();
        }
        if (bottom_sheet.getVisibility() != View.VISIBLE)
            bottom_sheet.setVisibility(View.VISIBLE);

        if (my_location.getVisibility() == View.VISIBLE)
            my_location.setVisibility(View.GONE);

        if (upDown.getAnimation() == null)
            upDown.startAnimation(animation);

    }

    public void setTap30Price(String serviceName, String price, int icon) {

        PriceListModel model = new PriceListModel(serviceName, price, icon, PriceListModel.serviceId.TAP30);
        if (adapter == null) {
            priceModel.add(model);
            adapter = new PricesAdapter(getActivity(), priceModel, onItemClickListener);
            priceLayout.setAdapter(adapter);
        } else {
            for (int i = 0; i <= priceModel.size(); i++) {
                if (i == priceModel.size()) {
                    priceModel.add(model);
                    break;
                } else if (Integer.parseInt(PriceOrders.get(priceModel.get(i).id.getValue() - 1)) >= Integer.parseInt(PriceOrders.get(model.id.getValue() - 1))) {
                    priceModel.add(i, model);
                    break;
                }
            }
            adapter.notifyDataSetChanged();
        }
        if (bottom_sheet.getVisibility() != View.VISIBLE)
            bottom_sheet.setVisibility(View.VISIBLE);

        if (my_location.getVisibility() == View.VISIBLE)
            my_location.setVisibility(View.GONE);

        if (upDown.getAnimation() == null)
            upDown.startAnimation(animation);
    }
    public void setTouchsiPrice(String serviceName, String price, int icon) {

        PriceListModel model = new PriceListModel(serviceName, price, icon, PriceListModel.serviceId.TOUCHSI);
        if (adapter == null) {
            priceModel.add(model);
            adapter = new PricesAdapter(getActivity(), priceModel, onItemClickListener);
            priceLayout.setAdapter(adapter);
        } else {
            for (int i = 0; i <= priceModel.size(); i++) {
                if (i == priceModel.size()) {
                    priceModel.add(model);
                    break;
                } else if (Integer.parseInt(PriceOrders.get(priceModel.get(i).id.getValue() - 1)) >= Integer.parseInt(PriceOrders.get(model.id.getValue() - 1))) {
                    priceModel.add(i, model);
                    break;
                }
            }
            adapter.notifyDataSetChanged();
        }
        if (bottom_sheet.getVisibility() != View.VISIBLE)
            bottom_sheet.setVisibility(View.VISIBLE);

        if (my_location.getVisibility() == View.VISIBLE)
            my_location.setVisibility(View.GONE);

        if (upDown.getAnimation() == null)
            upDown.startAnimation(animation);
    }
    public void setCarpinoPrice(String serviceName, String price, int icon) {
        int carpinoPriceToman;
        try {
            carpinoPriceToman = Integer.parseInt(price);
            carpinoPriceToman = carpinoPriceToman / 10;
        } catch (NumberFormatException nfe) {
            carpinoPriceToman = 0;
        }
        price = String.valueOf(carpinoPriceToman);
        PriceListModel model = new PriceListModel(serviceName, price, icon, PriceListModel.serviceId.CARPINO);
        if (adapter == null) {
            priceModel.add(model);
            adapter = new PricesAdapter(getActivity(), priceModel, onItemClickListener);
            priceLayout.setAdapter(adapter);
        } else {
            for (int i = 0; i <= priceModel.size(); i++) {
                if (i == priceModel.size()) {
                    priceModel.add(model);
                    break;
                } else if (Integer.parseInt(PriceOrders.get(priceModel.get(i).id.getValue() - 1)) >= Integer.parseInt(PriceOrders.get(model.id.getValue() - 1))) {
                    priceModel.add(i, model);
                    break;
                }
            }
            adapter.notifyDataSetChanged();
        }

        if (bottom_sheet.getVisibility() != View.VISIBLE)
            bottom_sheet.setVisibility(View.VISIBLE);

        if (my_location.getVisibility() == View.VISIBLE)
            my_location.setVisibility(View.GONE);

        if (upDown.getAnimation() == null)
            upDown.startAnimation(animation);

    }

    public void setAlopeykPrice(String serviceName, String price, int icon) {

        PriceListModel model = new PriceListModel(serviceName, price, icon, PriceListModel.serviceId.ALOPEYK);
        if (adapter == null) {
            priceModel.add(model);
            adapter = new PricesAdapter(getActivity(), priceModel, onItemClickListener);
            priceLayout.setAdapter(adapter);
        } else {
            for (int i = 0; i <= priceModel.size(); i++) {
                if (i == priceModel.size()) {
                    priceModel.add(model);
                    break;
                } else if (Integer.parseInt(PriceOrders.get(priceModel.get(i).id.getValue() - 1)) >= Integer.parseInt(PriceOrders.get(model.id.getValue() - 1))) {
                    priceModel.add(i, model);
                    break;
                }
            }
            adapter.notifyDataSetChanged();
        }
        if (bottom_sheet.getVisibility() != View.VISIBLE)
            bottom_sheet.setVisibility(View.VISIBLE);

        if (my_location.getVisibility() == View.VISIBLE)
            my_location.setVisibility(View.GONE);

        if (upDown.getAnimation() == null)
            upDown.startAnimation(animation);
    }

    public void setMaximPrice(String serviceName, String price, int icon) {
        PriceListModel model;
        if (serviceName.matches(getString(R.string.maxim_echo_price)))
            model = new PriceListModel(serviceName, price, icon, PriceListModel.serviceId.MAXIM);
        else
            model = new PriceListModel(serviceName, price, icon, PriceListModel.serviceId.OTHERS);
        if (adapter == null) {
            priceModel.add(model);
            adapter = new PricesAdapter(getActivity(), priceModel, onItemClickListener);
            priceLayout.setAdapter(adapter);
        } else {
            if (serviceName.matches(getString(R.string.maxim_echo_price)))
                for (int i = 0; i <= priceModel.size(); i++) {
                    if (i == priceModel.size()) {
                        priceModel.add(model);
                        break;
                    } else if (Integer.parseInt(PriceOrders.get(priceModel.get(i).id.getValue() - 1)) >= Integer.parseInt(PriceOrders.get(model.id.getValue() - 1))) {
                        priceModel.add(i, model);
                        break;
                    }
                }
            else
                for (int i = 0; i <= priceModel.size(); i++) {
                    if (i == priceModel.size()) {
                        priceModel.add(model);
                        break;
                    } else if (Integer.parseInt(PriceOrders.get(priceModel.get(i).id.getValue() - 1)) >= Integer.parseInt(PriceOrders.get(8))) {
                        priceModel.add(i, model);
                        break;
                    }
                }
            adapter.notifyDataSetChanged();
        }
        if (bottom_sheet.getVisibility() != View.VISIBLE)
            bottom_sheet.setVisibility(View.VISIBLE);

        if (my_location.getVisibility() == View.VISIBLE)
            my_location.setVisibility(View.GONE);

        if (upDown.getAnimation() == null)
            upDown.startAnimation(animation);

    }

    public void setPrice(String serviceName, PathPrice pathPrice, int icon) {
        if (pathPrice == null || pathPrice.equals("")) {

        } else {
            PriceListModel model = new PriceListModel(serviceName, pathPrice.PrivateServicePrice, icon, PriceListModel.serviceId.Telephony);
            if (adapter == null) {
                priceModel.add(model);
                adapter = new PricesAdapter(getActivity(), priceModel, onItemClickListener);
                priceLayout.setAdapter(adapter);
            } else
                for (int i = 0; i <= priceModel.size(); i++) {
                    if (i == priceModel.size()) {
                        priceModel.add(model);
                        break;
                    } else if (Integer.parseInt(PriceOrders.get(priceModel.get(i).id.getValue() - 1)) >= Integer.parseInt(PriceOrders.get(model.id.getValue() - 1))) {
                        priceModel.add(i, model);
                        break;
                    }
                }
            adapter.notifyDataSetChanged();
            if (bottom_sheet.getVisibility() != View.VISIBLE)
                bottom_sheet.setVisibility(View.VISIBLE);

            if (my_location.getVisibility() == View.VISIBLE)
                my_location.setVisibility(View.GONE);

            if (upDown.getAnimation() == null)
                upDown.startAnimation(animation);
        }
    }


    public void setWait() {
        setAllBlocksInvisible();
        wait_layout.setVisibility(View.VISIBLE);
    }

    public interface onItemClickListener {

        void onItemClicked(int position, List<PriceListModel> list);
    }
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_UP) {
//            switch (v.getId()) {
//                case R.id.do_source_btn:
//                    if (getActivity() instanceof AddMapActivity) {
//                        ((AddMapActivity) getActivity()).doBtnClicked();
//                    }
//                    break;
////                case R.id.my_location:
////                    if (getActivity() instanceof AddMapActivity) {
////                        ((AddMapActivity) getActivity()).gotoMyLocation();
////                    }
////                    break;
//            }
//            return true;
//        }
//        return false;
//    }
}

