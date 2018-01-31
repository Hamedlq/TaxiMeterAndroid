package com.mibarim.taximeter.favorite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mibarim.taximeter.BootstrapApplication;
import com.mibarim.taximeter.R;
import com.mibarim.taximeter.dataBase.DataBaseFav;
import com.mibarim.taximeter.models.ApiResponse;
import com.mibarim.taximeter.services.UserInfoService;
import com.mibarim.taximeter.ui.OpeningDialogTheme;
import com.mibarim.taximeter.ui.activities.AddMapActivity;
import com.mibarim.taximeter.util.SafeAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by mohammad hossein on 11/12/2017.
 */

public class FavoritePlaceActivity extends AppCompatActivity {
    public FloatingActionButton fab;
    List<favoriteModel> items;
    ItemClickListiner itemClickListiner;
    RecyclerView recyclerView;
    LinearLayout empty_image;
    @Inject
    UserInfoService userInfoService;
    ApiResponse apiResponse;
    String token;
    DataBaseFav db;
    private favoriteRecyclerAdapter mAdapter;
    private int MAP_SET = 9999;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_place);
        BootstrapApplication.component().inject(this);

        SharedPreferences preferences = getSharedPreferences("user_token", MODE_PRIVATE);
        token = preferences.getString("token", null);
        empty_image = (LinearLayout) findViewById(R.id.empty_fav_image);
        empty_image.setAlpha((float) 0.5);
        db = new DataBaseFav(this);
        items = new ArrayList<>();
        apiResponse = new ApiResponse();
        setupRecycle();


        fab = (FloatingActionButton) findViewById(R.id.favorite_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoritePlaceActivity.this, FavoriteMapActivity.class);
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
                        new SafeAsyncTask<Boolean>() {
                            @Override
                            public Boolean call() throws Exception {
                                apiResponse = userInfoService.deleteFavoritePlace(token, selectItem);
                                return true;
                            }

                            @Override
                            protected void onSuccess(Boolean aBoolean) throws Exception {
                                super.onSuccess(aBoolean);
                                items.clear();
                                getFavoriteResponse();
                            }

                            @Override
                            protected void onFinally() throws RuntimeException {
                                super.onFinally();
                                dialog.dismiss();
                            }
                        }.execute();
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        items.clear();
        getFavoriteResponse();
    }

    @Override
    protected void onDestroy() {
        db.deleteAll();
        for (favoriteModel favModel : items)
            db.addOneItem(favModel);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MAP_SET && resultCode == RESULT_OK) {
            favoriteModel fModel = new favoriteModel();
            fModel.setLat(String.valueOf(data.getDoubleExtra("lat", 0)));
            fModel.setLng(String.valueOf(data.getDoubleExtra("lng", 0)));
            fModel.setFavPlace(data.getStringExtra("text"));
            refresh();
            Toast.makeText(getApplicationContext(), "با موفقیت ذخیره شد", Toast.LENGTH_LONG).show();

        }
    }

    private void getFavoriteResponse() {
        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                apiResponse = userInfoService.getFavoritePlaces(token);
                return true;
            }

            @Override
            protected void onSuccess(Boolean aBoolean) throws Exception {
                super.onSuccess(aBoolean);
                getAllFavoritePlace();
            }
        }.execute();
    }

    private void getAllFavoritePlace() {
        if (apiResponse.Messages != null) {
            for (String json : apiResponse.Messages) {
                try {
                    JSONObject object = new JSONObject(json);
                    String favPlace = object.getString("favPlace");
                    String lat = object.getString("latitude");
                    String lng = object.getString("longitude");
                    int id = object.getInt("id");
                    items.add(new favoriteModel(favPlace, lat, lng, id));
                } catch (JSONException e) {
                    Toast.makeText(this, "خطا در برقراری ارتباط با سرور", Toast.LENGTH_SHORT).show();
                }
            }
        }
        refresh();

    }

    public void setupRecycle() {
        recyclerView = (RecyclerView) findViewById(R.id.fav_recycle);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManager);
    }


    public void refresh() {
//        items = db.getAllItems();
        if (items.size() == 0) {
            mAdapter = new favoriteRecyclerAdapter(this, itemClickListiner, items);
            recyclerView.setAdapter(mAdapter);
            empty_image.setVisibility(View.VISIBLE);
        } else {
            mAdapter = new favoriteRecyclerAdapter(this, itemClickListiner, items);
            recyclerView.setAdapter(mAdapter);
            empty_image.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("latFav", "0");
        resultIntent.putExtra("lngFav", "0");
        setResult(AddMapActivity.RESULT_OK, resultIntent);
        finish();
    }

    public interface ItemClickListiner {
        public void onLayoutClick(View view, int position);

        public void onDeleteClick(View view, int position);
    }
}
