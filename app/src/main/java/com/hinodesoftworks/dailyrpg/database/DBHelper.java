package com.hinodesoftworks.dailyrpg.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "drpg-data.db";
    private static final int DB_VERSION = 1;

    private Context ctx;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        ctx = context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //add tables
        CharacterTable.onCreate(sqLiteDatabase);
        EnemyTable.onCreate(sqLiteDatabase);
        ClassesTable.onCreate(sqLiteDatabase);
        ItemTable.onCreate(sqLiteDatabase);

        Log.e("HI", "Created db controller");

        //TODO: This is brutally inneficient. Look into prepopulating databases or populating from xml/json
        //brute force data population
        sqLiteDatabase.execSQL("INSERT INTO classes (className, classBaseHP, classBaseMP, classBaseAtk" +
                "classBaseMAtk, classBaseDef, classBaseMDef) VALUES ('Fighter', 100, 40, 30, 5, 20, 5)");
        sqLiteDatabase.execSQL("INSERT INTO classes (className, classBaseHP, classBaseMP, classBaseAtk" +
                "classBaseMAtk, classBaseDef, classBaseMDef) VALUES ('Cleric', 80, 100, 15, 15, 25, 25)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        //TODO: Actually do upgrade logic
    }
}
