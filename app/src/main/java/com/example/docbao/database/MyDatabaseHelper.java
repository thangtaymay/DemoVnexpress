package com.example.docbao.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.docbao.objects.VnExpressItem;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "vnexpress.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "VnExpressItem";

    private static final String COLUMN_TITLE = "title";

    private static final String COLUMN_DESCIPTION = "desciption";

    private static final String COLUMN_IMG = "img";

    private static final String COLUMN_LINK = "link";

    private static final String COLUMN_TIME = "time";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_LINK + " TEXT PRIMARY KEY, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DESCIPTION + " TEXT, " +
                COLUMN_IMG + " TEXT, " +
                COLUMN_TIME + " TEXT)";
        db.execSQL(createTable);
    }

    //    truy vấn không trả kết quả
    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);

    }

    //    truy vấn trả kết quả
    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // implement upgrade logic here
    }

    public void addUser(VnExpressItem vn) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, vn.getTitle());
        values.put(COLUMN_DESCIPTION, vn.getDesciption());
        values.put(COLUMN_IMG, vn.getImg());
        values.put(COLUMN_LINK, vn.getLink());
        values.put(COLUMN_TIME, vn.getTime());

        long result = db.insert(TABLE_NAME, null, values);

        db.close();
    }



}
