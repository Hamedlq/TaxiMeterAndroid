package com.mibarim.taximeter.ui.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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
import com.mibarim.taximeter.models.enums.AddRouteStates;
import com.mibarim.taximeter.ui.activities.AddMapActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hamed on 3/4/2016.
 */
public class HelpFragment extends Fragment {

    @Bind(R.id.price_link)
    protected TextView price_link;
    @Bind(R.id.price_service_link)
    protected TextView price_service_link;

    public HelpFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_help, container, false);
        return layout;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());


        price_link.setMovementMethod(LinkMovementMethod.getInstance());
        price_link.setText(Html.fromHtml("<p>"+getString(R.string.faq_1)+"</p><br/><a href=\"" + "http://taxi.tehran.ir/Default.aspx?tabid=312" + "\">" + "وبسایت تاکسیرانی تهران" + "</a>"));

        price_service_link.setMovementMethod(LinkMovementMethod.getInstance());
        price_service_link.setText(Html.fromHtml("<a href=\"" + "http://mibarim.com/" + "\">" + "می‌بریم" + "</a>"
                +"<br/><a href=\"" + "https://snapp.ir/" + "\">" + "اسنپ" + "</a>"
        +"<br/><a href=\"" + "https://tap30.ir/" + "\">" + "تپسی" + "</a>"
                +"<br/><a href=\"" + "https://www.carpino.ir/" + "\">" + "کارپینو" + "</a>"));

    }

}
