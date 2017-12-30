package com.mibarim.taximeter.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mibarim.taximeter.R;
import com.mibarim.taximeter.models.PriceListModel;
import com.mibarim.taximeter.ui.fragments.SrcDstFragment;

import java.util.List;

/**
 * Created by Arya on 12/9/2017.
 */

public class PricesAdapter extends RecyclerView.Adapter<PricesAdapter.ViewHolder> {

    private Activity activity;
    private List<PriceListModel> listModel;
    private SrcDstFragment.onItemClickListener listener;

    public PricesAdapter(Activity activity, List<PriceListModel> listModel, SrcDstFragment.onItemClickListener listener) {
        this.activity = activity;
        this.listModel = listModel;
        this.listener = listener;
    }

    @Override
    public PricesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.price_list_adapter_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PricesAdapter.ViewHolder holder, int position) {
        PriceListModel model = listModel.get(position);
        if (model != null) {
            switch (model.getServiceName()) {
                case "اسنپ اکو":
                    holder.serviceName.setText(model.getServiceName());
                    holder.serviceIcon.setImageResource(R.mipmap.snapp_icon);
                    holder.servicePrice.setText(model.getPrice());
                    break;
                case "اسنپ رز":
                    holder.serviceName.setText(model.getServiceName());
                    holder.serviceIcon.setImageResource(R.mipmap.snapp_icon);
                    holder.servicePrice.setText(model.getPrice());
                    break;
                case "اسنپ بایک":
                    holder.serviceName.setText(model.getServiceName());
                    holder.serviceIcon.setImageResource(R.mipmap.snapp_icon);
                    holder.servicePrice.setText(model.getPrice());
                    break;
                case "تپسی":
                    holder.serviceName.setText(model.getServiceName());
                    holder.serviceIcon.setImageResource(R.mipmap.tap30_icon);
                    holder.servicePrice.setText(model.getPrice());
                    break;
                case "کارپینو":
                    holder.serviceName.setText(model.getServiceName());
                    holder.serviceIcon.setImageResource(R.mipmap.carpino_icon);
                    holder.servicePrice.setText(model.getPrice());
                    break;
                case "الو\u200Cپیک":
                    holder.serviceName.setText(model.getServiceName());
                    holder.serviceIcon.setImageResource(R.mipmap.alopeyk_icon);
                    holder.servicePrice.setText(model.getPrice());
                    break;
                case "ماکسیم اقتصادی":
                    holder.serviceName.setText(model.getServiceName());
                    holder.serviceIcon.setImageResource(R.mipmap.maxim_icon);
                    holder.servicePrice.setText(model.getPrice());
                    break;
                case "ماکسیم لوکس":
                    holder.serviceName.setText(model.getServiceName());
                    holder.serviceIcon.setImageResource(R.mipmap.maxim_icon);
                    holder.servicePrice.setText(model.getPrice());
                    break;
                case "ماکسیم بانوان":
                    holder.serviceName.setText(model.getServiceName());
                    holder.serviceIcon.setImageResource(R.mipmap.maxim_icon);
                    holder.servicePrice.setText(model.getPrice());
                    break;
                case "ماکسیم تجاری":
                    holder.serviceName.setText(model.getServiceName());
                    holder.serviceIcon.setImageResource(R.mipmap.maxim_icon);
                    holder.servicePrice.setText(model.getPrice());
                    break;
                case "تاچ سی":
                    holder.serviceName.setText(model.getServiceName());
                    holder.serviceIcon.setImageResource(R.mipmap.touchsi_icon);
                    holder.servicePrice.setText(model.getPrice());
                    break;
                case "تاکسی اشتراکی می\u200Cبریم":
                    holder.serviceName.setText(model.getServiceName());
                    holder.serviceIcon.setImageResource(R.mipmap.mibarim_icon);
                    holder.servicePrice.setText(model.getPrice());
                    break;
                case "تاکسی تلفنی تهران":
                    holder.serviceName.setText(model.getServiceName());
                    holder.serviceIcon.setImageResource(R.mipmap.taxitehran);
                    holder.servicePrice.setText(model.getPrice());
                    break;
            }
        } else {

        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }

    @Override
    public int getItemCount() {
        return listModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView serviceName, servicePrice;
        public ImageView serviceIcon;
        LinearLayout recyclerView;

        public ViewHolder(View v) {
            super(v);
            serviceName = (TextView) v.findViewById(R.id.service_name);
            servicePrice = (TextView) v.findViewById(R.id.service_price);
            serviceIcon = (ImageView) v.findViewById(R.id.service_icon);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(getLayoutPosition(), listModel);
                }
            });
        }
    }
}
