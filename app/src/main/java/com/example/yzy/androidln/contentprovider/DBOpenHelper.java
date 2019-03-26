package com.example.yzy.androidln.contentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by yzy on 2019/3/11 0011.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "test.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "test";

    public static final String FIELD_ID = "field_id";
    public static final String FIELD_SHOW = "field_show";
    public static final String FIELD_LOCATION = "field_location";

    public static final String AUTHORITY = "com.example.yzy.androidln.db.PersonContentProvider";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/person");

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "Create table " + TABLE_NAME + "(" + BaseColumns._ID + " integer primary key autoincrement,"
                + FIELD_ID + " integer not null, "
                + FIELD_LOCATION + " integer not null, "
                + FIELD_SHOW + " integer not null);";
        db.execSQL(sql);

        initDataBase(db);
    }

    private void initDataBase(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(FIELD_ID, 1);
        cv.put(FIELD_LOCATION, 1);
        cv.put(FIELD_SHOW, 1);
        db.insert(TABLE_NAME, null, cv);

        cv.clear();
        cv.put(FIELD_ID, 2);
        cv.put(FIELD_LOCATION, 2);
        cv.put(FIELD_SHOW, 1);
        db.insert(TABLE_NAME, null, cv);

        cv.clear();
        cv.put(FIELD_ID, 3);
        cv.put(FIELD_LOCATION, 3);
        cv.put(FIELD_SHOW, 0);
        db.insert(TABLE_NAME, null, cv);

        cv.clear();
        cv.put(FIELD_ID, 4);
        cv.put(FIELD_LOCATION, 4);
        cv.put(FIELD_SHOW, 1);
        db.insert(TABLE_NAME, null, cv);

        cv.clear();
        cv.put(FIELD_ID, 5);
        cv.put(FIELD_LOCATION, 5);
        cv.put(FIELD_SHOW, 0);
        db.insert(TABLE_NAME, null, cv);

        cv.clear();
        cv.put(FIELD_ID, 6);
        cv.put(FIELD_LOCATION, 6);
        cv.put(FIELD_SHOW, 0);
        db.insert(TABLE_NAME, null, cv);

        cv.clear();
        cv.put(FIELD_ID, 7);
        cv.put(FIELD_LOCATION, 7);
        cv.put(FIELD_SHOW, 0);
        db.insert(TABLE_NAME, null, cv);

        cv.clear();
        cv.put(FIELD_ID, 8);
        cv.put(FIELD_LOCATION, 8);
        cv.put(FIELD_SHOW, 1);
        db.insert(TABLE_NAME, null, cv);

        cv.clear();
        cv.put(FIELD_ID, 9);
        cv.put(FIELD_LOCATION, 9);
        cv.put(FIELD_SHOW, 0);
        db.insert(TABLE_NAME, null, cv);

        cv.clear();
        cv.put(FIELD_ID, 10);
        cv.put(FIELD_LOCATION, 10);
        cv.put(FIELD_SHOW, 0);
        db.insert(TABLE_NAME, null, cv);

        cv.clear();
        cv.put(FIELD_ID, 11);
        cv.put(FIELD_LOCATION, 11);
        cv.put(FIELD_SHOW, 0);
        db.insert(TABLE_NAME, null, cv);

        cv.clear();
        cv.put(FIELD_ID, 12);
        cv.put(FIELD_LOCATION, 12);
        cv.put(FIELD_SHOW, 0);
        db.insert(TABLE_NAME, null, cv);

        cv.clear();
        cv.put(FIELD_ID, 13);
        cv.put(FIELD_LOCATION, 13);
        cv.put(FIELD_SHOW, 0);
        db.insert(TABLE_NAME, null, cv);

        cv.clear();
        cv.put(FIELD_ID, 14);
        cv.put(FIELD_LOCATION, 14);
        cv.put(FIELD_SHOW, 1);
        db.insert(TABLE_NAME, null, cv);

        cv.clear();
        cv.put(FIELD_ID, 15);
        cv.put(FIELD_LOCATION, 15);
        cv.put(FIELD_SHOW, 1);
        db.insert(TABLE_NAME, null, cv);

        cv.clear();
        cv.put(FIELD_ID, 16);
        cv.put(FIELD_LOCATION, 16);
        cv.put(FIELD_SHOW, 1);
        db.insert(TABLE_NAME, null, cv);

        cv.clear();
        cv.put(FIELD_ID, 17);
        cv.put(FIELD_LOCATION, 17);
        cv.put(FIELD_SHOW, 0);
        db.insert(TABLE_NAME, null, cv);

        cv.clear();
        cv.put(FIELD_ID, 18);
        cv.put(FIELD_LOCATION, 18);
        cv.put(FIELD_SHOW, 1);
        db.insert(TABLE_NAME, null, cv);

        cv.clear();
        cv.put(FIELD_ID, 19);
        cv.put(FIELD_LOCATION, 19);
        cv.put(FIELD_SHOW, 0);
        db.insert(TABLE_NAME, null, cv);

        cv.clear();
        cv.put(FIELD_ID, 20);
        cv.put(FIELD_LOCATION, 20);
        cv.put(FIELD_SHOW, 0);
        db.insert(TABLE_NAME, null, cv);

        cv.clear();
        cv.put(FIELD_ID, 21);
        cv.put(FIELD_LOCATION, 21);
        cv.put(FIELD_SHOW, 0);
        db.insert(TABLE_NAME, null, cv);

        cv.clear();
        cv.put(FIELD_ID, 22);
        cv.put(FIELD_LOCATION, 22);
        cv.put(FIELD_SHOW, 0);
        db.insert(TABLE_NAME, null, cv);

        cv.clear();
        cv.put(FIELD_ID, 23);
        cv.put(FIELD_LOCATION, 23);
        cv.put(FIELD_SHOW, 0);
        db.insert(TABLE_NAME, null, cv);

        cv.clear();
        cv.put(FIELD_ID, 24);
        cv.put(FIELD_LOCATION, 24);
        cv.put(FIELD_SHOW, 0);
        db.insert(TABLE_NAME, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}
