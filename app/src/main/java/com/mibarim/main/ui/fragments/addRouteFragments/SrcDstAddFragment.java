package com.mibarim.main.ui.fragments.addRouteFragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.mibarim.main.BootstrapApplication;
import com.mibarim.main.R;
import com.mibarim.main.activities.AddMapActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hamed on 3/4/2016.
 */
public class SrcDstAddFragment extends Fragment {

    @Bind(R.id.source_layout)
    protected RelativeLayout source_layout;
    @Bind(R.id.destination_layout)
    protected RelativeLayout destination_layout;

    @Bind(R.id.src_label)
    protected TextView src_label;
    @Bind(R.id.dst_label)
    protected TextView dst_label;
    @Bind(R.id.dst_editText)
    protected TextView dst_editText;
    @Bind(R.id.src_editText)
    protected TextView src_editText;
    @Bind(R.id.src_detail)
    protected EditText src_detail;
    @Bind(R.id.dst_detail)
    protected EditText dst_detail;
    @Bind(R.id.do_continue_btn)
    protected TextView do_continue_btn;



    private Context context;
    private String dstAddDetail;
    private String srcAddDetail;


    public SrcDstAddFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_srcdst, container, false);


//        SetView();



//        dstDetailBtn.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
//                    final BootstrapEditText dstDetailEditText = new BootstrapEditText(context);
//                    alert.setTitle(R.string.dst_address_detail);
//                    alert.setView(dstDetailEditText);
//                    dstDetailEditText.setGravity(Gravity.RIGHT);
//                    alert.setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int whichButton) {
//                            dstAddDetail = dstDetailEditText.getText().toString();
//                        }
//                    });
//                    alert.show();
//                    return true;
//                }
//                return false;
//            }
//        });
//        srcDetailBtn.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
//                    final BootstrapEditText srcDetailEditText = new BootstrapEditText(context);
//                    srcDetailEditText.setGravity(Gravity.RIGHT);
//                    alert.setTitle(R.string.src_address_detail);
//                    alert.setView(srcDetailEditText);
//                    alert.setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int whichButton) {
//                            srcAddDetail = srcDetailEditText.getText().toString();
//                        }
//                    });
//                    alert.show();
//                    return true;
//                }
//                return false;
//            }
//        });


        return layout;
    }


    private void srcSelected() {
        source_layout.setBackgroundColor(Color.parseColor("#ffffff"));
        destination_layout.setBackgroundColor(Color.parseColor("#e0f2f1"));
        ((AddMapActivity) getActivity()).setSrcDstStateSelector("SOURCE_SELECTED");
    }

    private void dstSelected() {
        source_layout.setBackgroundColor(Color.parseColor("#e0f2f1"));
        destination_layout.setBackgroundColor(Color.parseColor("#ffffff"));
        ((AddMapActivity) getActivity()).setSrcDstStateSelector("DESTINATION_SELECTED");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());

        if (((AddMapActivity) getActivity()).getSrcDstStateSelector() == "SOURCE_SELECTED") {
            srcSelected();
        }
        if (((AddMapActivity) getActivity()).getSrcDstStateSelector() == "DESTINATION_SELECTED") {
            dstSelected();
        }
        SetAddresses();

        src_label.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    srcSelected();
                    return true;
                }
                return false;
            }
        });
        dst_label.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    dstSelected();
                    return true;
                }
                return false;
            }
        });
        src_editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    srcSelected();
                    return true;
                }
                return false;
            }
        });
        dst_editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    dstSelected();
                    return true;
                }
                return false;
            }
        });
        src_detail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    srcSelected();
                    return true;
                }
                return false;
            }
        });
        dst_detail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    dstSelected();
                    return true;
                }
                return false;
            }
        });
        do_continue_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((AddMapActivity) getActivity()).routeRequest.SrcDetailAddress = src_detail.getText().toString();
                    ((AddMapActivity) getActivity()).routeRequest.DstDetailAddress = dst_detail.getText().toString();
                    ((AddMapActivity) getActivity()).showForms();
                    return true;
                }
                return false;
            }
        });

    }

    public void SetAddresses() {
        String srcAddress = "";
        String dstAddress = "";
        srcAddress = ((AddMapActivity) getActivity()).getSrcAddress();
        if (srcAddress != null && srcAddress != "") {
            src_editText.setText(srcAddress);
            src_detail.setHint(R.string.src_address_detail);
        }
        dstAddress = ((AddMapActivity) getActivity()).getDstAddress();
        if (dstAddress != null && dstAddress != "") {
            dst_editText.setText(dstAddress);
            dst_detail.setHint(R.string.dst_address_detail);
        }

    }


}
