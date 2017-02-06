package com.mibarim.taximeter.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mibarim.taximeter.BootstrapApplication;
import com.mibarim.taximeter.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hamed on 3/4/2016.
 */
public class AboutUsFragment extends Fragment {

    @Bind(R.id.price_link)
    protected TextView price_link;

    public AboutUsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_about_us, container, false);
        return layout;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());


        price_link.setMovementMethod(LinkMovementMethod.getInstance());
        price_link.setText(Html.fromHtml("<p>"+getString(R.string.about_us_txt)+"</p><br/><a href=\"" +  Uri.parse(getString(R.string.update_link)) + "\">" + "لینک دانلود" + "</a>"));
    }

}
