package com.mibarim.taximeter.favorite;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mibarim.taximeter.R;

import java.util.List;

/**
 * Created by mohammad hossein on 13/12/2017.
 */

public class favoriteRecyclerAdapter  extends RecyclerView.Adapter<favoriteRecyclerAdapter.ViewHolder>{

    private Activity _activity;
    private FavoritePlaceActivity.ItemClickListiner onItemClickListiner;
    private List<favoriteModel> items;



    //create a new
    @Override
    public favoriteRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_card_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.fav_text.setText(items.get(position).cardText);
        holder.fav_second_text.setText(items.get(position).cardSecondText);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public RelativeLayout fav_linear;
        public TextView fav_text,fav_second_text;
        public LinearLayout delete;
        public ViewHolder(View itemView) {
            super(itemView);

            fav_linear = (RelativeLayout) itemView.findViewById(R.id.linear_fav);
            fav_text = (TextView) itemView.findViewById(R.id.fav_card_text);
            delete = (LinearLayout) itemView.findViewById(R.id.fav_delete);
            fav_second_text = (TextView)itemView.findViewById(R.id.fav_card_secound_text);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListiner.onDeleteClick(v,getPosition());
                }
            });
            fav_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListiner.onLayoutClick(v,getPosition());

                }
            });
        }
    }


    public favoriteRecyclerAdapter(Activity _activity, FavoritePlaceActivity.ItemClickListiner onItemClickListiner, List<favoriteModel> items) {
        this._activity = _activity;
        this.onItemClickListiner = onItemClickListiner;
        this.items = items;
    }



}
