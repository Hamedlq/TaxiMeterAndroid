package com.mibarim.taximeter.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mibarim.taximeter.favorite.favoriteModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohammad hossein on 18/12/2017.
 */

public class DataBaseFav extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "kerayechi";
    // Contacts table name
    private static final String TABLE_NANE = "favorite";
    //columns
    private static final String KEY_TITLE = "title";
    private static final String KEY_SECOUND = "secound";
    private static final String KEY_SRCLAT = "lat";
    private static final String KEY_SRCLNG = "lng";

    public DataBaseFav(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NANE + "("
                + KEY_TITLE + " TEXT," + KEY_SECOUND + " TEXT," + KEY_SRCLAT + " TEXT,"
                + KEY_SRCLNG + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NANE);
// Creating tables again
        onCreate(db);
    }

    //add one item
    public void addOneItem(favoriteModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, model.getCardText());
        values.put(KEY_SECOUND,model.getCardSecondText());
        values.put(KEY_TITLE, model.getFavPlace());
        values.put(KEY_SRCLAT, model.getLat());
        values.put(KEY_SRCLNG, model.getLng());

        db.insert(TABLE_NANE, null, values);
        db.close();
    }

    //get all items
    public List<favoriteModel> getAllItems() {
        List<favoriteModel> models = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NANE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        //looping
        if (cursor.moveToFirst()) {
            do {
                favoriteModel model = new favoriteModel();
                model.setCardText(cursor.getString(0));
                model.setCardSecondText(cursor.getString(1));
                model.setLat(cursor.getString(2));
                model.setLng(cursor.getString(3));

                //add
                models.add(model);
            } while (cursor.moveToNext());
        }

        //return
        return models;
    }

    //is one menu in database or not??
    public boolean getOneItem(List<favoriteModel> models, String title) {

        boolean checking = false;
        for (int i = 0; i < models.size(); i++) {
            favoriteModel model = models.get(i);
            if (title.equals(model.getFavPlace()))
                checking = true;
        }

        return checking;
    }

    //delete a menu
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NANE, null, null);
        db.close();
    }
}
