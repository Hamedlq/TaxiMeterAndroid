package com.mibarim.taximeter.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mibarim.taximeter.R;
import com.mibarim.taximeter.models.PriceListModel;
import com.mibarim.taximeter.ui.fragments.SrcDstFragment;

import java.util.List;

/**
 * Created by Arya on 12/9/2017.
 */

public class PricesAdapter extends RecyclerView.Adapter<PricesAdapter.ViewHolder> {

    private List<PriceListModel> listModel;
    private SrcDstFragment.onItemClickListener listener;

    public PricesAdapter(List<PriceListModel> listModel, SrcDstFragment.onItemClickListener listener) {
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
            holder.serviceName.setText(model.getServiceName());
            holder.serviceIcon.setImageResource(model.getIcon());
            holder.servicePrice.setText(model.getPrice());
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

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView serviceName, servicePrice;
        ImageView serviceIcon;

        ViewHolder(View v) {
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
