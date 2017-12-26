package com.mibarim.taximeter.favorite;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mibarim.taximeter.R;
import com.mibarim.taximeter.dataBase.DataBaseFav;
import com.mibarim.taximeter.ui.OpeningDialogTheme;
import com.mibarim.taximeter.ui.activities.AddMapActivity;
import com.mibarim.taximeter.util.Strings;
import com.mibarim.taximeter.util.Toaster;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.fragment;
import static com.mibarim.taximeter.R.string.event;
import static com.mibarim.taximeter.R.string.three;

/**
 * Created by mohammad hossein on 11/12/2017.
 */

public class favorite_place extends AppCompatActivity {
    //    35.724997, 51.385846
    //35.706882, 51.408412
    public FloatingActionButton fab;
    private favoriteRecyclerAdapter mAdapter;
    List<favoriteModel> items;
    ItemClickListiner itemClickListiner;
    RecyclerView recyclerView;
    LinearLayout empty_image;
    private int MAP_SET = 9999;
    DataBaseFav db;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_place);
        empty_image = (LinearLayout) findViewById(R.id.empty_fav_image);
        empty_image.setAlpha((float) 0.5);
        db = new DataBaseFav(this);
        items = new ArrayList<>();
        setupRecycle();


        fab = (FloatingActionButton) findViewById(R.id.favorite_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(favorite_place.this, favorite_map.class);
                startActivityForResult(intent, MAP_SET);
            }
        });

        itemClickListiner = new ItemClickListiner() {
            @Override
            public void onLayoutClick(View view, int position) {
                favoriteModel selectItem = items.get(position);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("latFav", selectItem.getLat());
                resultIntent.putExtra("lngFav", selectItem.getLng());
                setResult(AddMapActivity.RESULT_OK, resultIntent);
                finish();

            }

            @Override
            public void onDeleteClick(View view, final int position) {
                final favoriteModel selectItem = items.get(position);
                final OpeningDialogTheme dialog = new OpeningDialogTheme(view.getContext());
                dialog.show();
                dialog.setText("حذف کردن آدرس", "آیا می خواهید آدرس منتخب را حذف کنید؟");
                dialog.yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "با موفقیت حدف شد", Toast.LENGTH_LONG).show();
                        db.deleteMenu(selectItem);
                        dialog.dismiss();
                        refresh();


                    }
                });
                dialog.no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        };

        refresh();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MAP_SET && resultCode == RESULT_OK) {
            favoriteModel fModel = new favoriteModel();
            fModel.setLat(String.valueOf(data.getDoubleExtra("lat", 0)));
            fModel.setLng(String.valueOf(data.getDoubleExtra("lng", 0)));
            fModel.setCardText(data.getStringExtra("text"));
            db.addOneItem(fModel);
            refresh();
            Toast.makeText(getApplicationContext(), "با موفقیت ذخیره شد", Toast.LENGTH_LONG).show();

        }
    }

    public void setupRecycle() {
        recyclerView = (RecyclerView) findViewById(R.id.fav_recycle);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManager);
    }


    public void refresh() {
        items = db.getAllItems();
        if (items.size() == 0) {
            mAdapter = new favoriteRecyclerAdapter(this, itemClickListiner, items);
            recyclerView.setAdapter(mAdapter);
            empty_image.setVisibility(View.GONE);
            empty_image.setVisibility(View.VISIBLE);
        } else {

            mAdapter = new favoriteRecyclerAdapter(this, itemClickListiner, items);
            recyclerView.setAdapter(mAdapter);
            empty_image.setVisibility(View.GONE);
        }
    }


    public interface ItemClickListiner {
        public void onLayoutClick(View view, int position);

        public void onDeleteClick(View view, int position);
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("latFav","0");
        resultIntent.putExtra("lngFav", "0");
        setResult(AddMapActivity.RESULT_OK, resultIntent);
        finish();
    }
}
