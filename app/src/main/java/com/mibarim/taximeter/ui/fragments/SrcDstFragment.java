package com.mibarim.taximeter.ui.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mibarim.taximeter.BootstrapApplication;
import com.mibarim.taximeter.R;
import com.mibarim.taximeter.models.PathPrice;
import com.mibarim.taximeter.ui.OpeningDialogTheme;
import com.mibarim.taximeter.ui.activities.AddMapActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hamed on 3/4/2016.
 */
public class SrcDstFragment extends Fragment {

    @Bind(R.id.do_source_btn)
    protected Button do_source_btn;


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
    @Bind(R.id.waiting_layout)
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
        price_layout_shared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final OpeningDialogTheme dialog = new OpeningDialogTheme(getActivity());
                dialog.show();
                dialog.setText("می‌بریم", "آیا تمایل به باز کردن این برنامه دارید؟");

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
                                Toast.makeText(getActivity(), "هیج گونه مارکتی برروی موبایل شما نصب نیست", Toast.LENGTH_SHORT).show();
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
        });
        price_layout_snapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final OpeningDialogTheme dialog = new OpeningDialogTheme(getActivity());
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
                                Toast.makeText(getActivity(), "هیج گونه مارکتی برروی موبایل شما نصب نیست", Toast.LENGTH_SHORT).show();
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
        });
        price_layout_tap30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final OpeningDialogTheme dialog = new OpeningDialogTheme(getActivity());
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
                                Toast.makeText(getActivity(), "هیج گونه مارکتی برروی موبایل شما نصب نیست", Toast.LENGTH_SHORT).show();
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
        });
        price_layout_carpino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final OpeningDialogTheme dialog = new OpeningDialogTheme(getActivity());
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
                                Toast.makeText(getActivity(), "هیج گونه مارکتی برروی موبایل شما نصب نیست", Toast.LENGTH_SHORT).show();
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
        });
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
            price_layout_private.setVisibility(View.VISIBLE);
            price_layout_shared.setVisibility(View.VISIBLE);
            price_private.setText(thePrice.PrivateServicePrice);
            price_shared.setText(thePrice.SharedServicePrice);
        }
    }


    public void setSnappPrice(String snappPrice) {
        price_layout_snapp.setVisibility(View.VISIBLE);
        int snappPriceToman = 0;

        try {
            snappPriceToman = Integer.parseInt(snappPrice);
            snappPriceToman = snappPriceToman/10;
        } catch(NumberFormatException nfe) {

        }
        price_snapp.setText(String.valueOf(snappPriceToman));

    }

    public void setTap30Price(String tap30Price) {

        price_layout_tap30.setVisibility(View.VISIBLE);
        price_tap30.setText(tap30Price);


    }

    public void setCarpinoPrice(String carpinoPrice) {
        price_layout_carpino.setVisibility(View.VISIBLE);
        int carpinoPriceToman = 0;

        try {
            carpinoPriceToman = Integer.parseInt(carpinoPrice);
            carpinoPriceToman = carpinoPriceToman/10;
        } catch(NumberFormatException nfe) {

        }
        price_carpino.setText(String.valueOf(carpinoPriceToman));

    }


    public void setWait() {
        setAllBlocksInvisible();
        wait_layout.setVisibility(View.VISIBLE);
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

