package com.example.yzy.androidln.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by yzy on 2019/3/11 0011.
 */

public class PersonContentProvider extends ContentProvider {
    // 数据集的MIME类型字符串应该以vnd.android.cursor.dir/开头
    public static final String APPS_TYPE = "vnd.android.cursor.dir/apps";
    // 单一数据的MIME类型字符串应该以vnd.android.cursor.item/开头
    public static final String APPS_ITEM_TYPE = "vnd.android.cursor.item/apps";
    // 主机名
    public static final String AUTHORITY = "com.example.yzy.androidln.db.PersonContentProvider";
    // 自定义匹配码
    public static final int APPS = 1;
    // 自定义匹配码
    public static final int APPS_ID = 2;

    public static final Uri PERSON_URI = Uri.parse("content://" + AUTHORITY + "/person");

    private DBOpenHelper mDBOpenHelper;

    // UriMatcher类用来匹配Uri，使用match()方法匹配路径时返回匹配码
    private static final UriMatcher sUriMatcher;

    static {
        // 常量UriMatcher.NO_MATCHER表示不匹配任何路径的返回码
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        // 如果match()方法匹配content://com.example.yzy.androidln.db.PersonContentProvider/person路径，
        // 返回匹配码为PERSONS
        sUriMatcher.addURI(AUTHORITY, "person", APPS);
        // 如果match()方法匹配content://com.example.yzy.androidln.db.PersonContentProvider/person/230路径，
        // 返回匹配码为PERSON
        sUriMatcher.addURI(AUTHORITY, "person/#", APPS_ID);
    }

    @Override
    public boolean onCreate() {
        mDBOpenHelper = new DBOpenHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (sUriMatcher.match(uri)) {
            case APPS:
                qb.setTables("test");
                break;
            case APPS_ID:
                qb.setTables("test");
                qb.appendWhere("_id = ");
                qb.appendWhere(uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknow Uri " + uri);
        }
        SQLiteDatabase db = mDBOpenHelper.getReadableDatabase();
        Cursor ret = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        if (ret == null) {
            Log.v("ddd", "apps: apps query failed.");
        } else {
            ret.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return ret;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case APPS:
                return APPS_TYPE;
            case APPS_ID:
                return APPS_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknow uri");
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues initialValues) {
        if (sUriMatcher.match(uri) != APPS) {
            throw new IllegalArgumentException("Can not insert into uri " + uri);
        }
        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        if (!values.containsKey(DBOpenHelper.FIELD_ID)) {
            values.put(DBOpenHelper.FIELD_ID, 0);
        }

        if (!values.containsKey(DBOpenHelper.FIELD_LOCATION)) {
            values.put(DBOpenHelper.FIELD_LOCATION, 0);
        }

        if (!values.containsKey(DBOpenHelper.FIELD_SHOW)) {
            values.put(DBOpenHelper.FIELD_SHOW, 0);
        }

        SQLiteDatabase db = mDBOpenHelper.getWritableDatabase();
        long rowId = db.insert(DBOpenHelper.TABLE_NAME, null, values);
        if (rowId < 0) {
            throw new IllegalArgumentException("Failed to insert to " + uri);
        }

        Uri newUri = ContentUris.withAppendedId(DBOpenHelper.CONTENT_URI, rowId);
        getContext().getContentResolver().notifyChange(newUri, null);
        return newUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mDBOpenHelper.getWritableDatabase();
        int count;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case APPS:
                count = db.delete(DBOpenHelper.TABLE_NAME, selection, selectionArgs);
                break;
            case APPS_ID:
                String segment = uri.getPathSegments().get(1);
                if (TextUtils.isEmpty(selection)) {
                    selection = "_id=" + segment;
                } else {
                    selection = "_id=" + segment + " and (" + selection + ")";
                }

                count = db.delete(DBOpenHelper.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("can not delete from uir " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count;
        long rowId = 0;
        int match = sUriMatcher.match(uri);
        SQLiteDatabase db = mDBOpenHelper.getWritableDatabase();
        switch (match) {
            case APPS_ID:
                String segment = uri.getPathSegments().get(1);
                rowId = Long.valueOf(segment);
                count = db.update(DBOpenHelper.TABLE_NAME, values, "_id= " + rowId, null);
                break;
            default:
                throw new IllegalArgumentException("can not update the uri " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
