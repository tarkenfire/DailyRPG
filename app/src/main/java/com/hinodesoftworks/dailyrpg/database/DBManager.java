package com.hinodesoftworks.dailyrpg.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class DBManager {
    private Context ctx;
    private SQLiteDatabase database = null;
    private DBHelper dbHelper;

    private static DBManager _instance = null;

    public static DBManager getInstance(Context context) {
        if (_instance == null) {
            _instance = new DBManager(context);
        }
        return _instance;
    }

    private DBManager(Context context) {
        ctx = context;
        dbHelper = new DBHelper(context);
    }

    public void openDatabase() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    public void closeDatabase() {
        dbHelper.close();
    }

    //crud functions
    public void addItem(String tableName, ContentValues values) {
        if (database != null) {
            database.insert(tableName, null, values);
        }
    }

    public Cursor query(String tableName, String[] projection, String whereClause) {
        Cursor cursor = null;

        if (database != null) {
            cursor = database.query(tableName, projection, whereClause, null, null, null, null);
        }

        return cursor;
    }

    public void deleteItem(String tableName, String whereClause) {
        if (database != null) {
            database.delete(tableName, whereClause, null);
        }
    }

    public void updateItem(String tableName, ContentValues values, String whereClause) {
        if (database != null) {
            database.update(tableName, values, whereClause, null);
        }
    }


}
