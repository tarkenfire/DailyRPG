package com.hinodesoftworks.dailyrpg.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBController extends SQLiteOpenHelper
{
    private static final String DB_NAME = "drpg-data.db";
    private static final int DB_VERSION = 1;

    public DBController(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        //add tables
        CharacterTable.onCreate(sqLiteDatabase);
        EnemyTable.onCreate(sqLiteDatabase);
        ClassesTable.onCreate(sqLiteDatabase);
        ItemTable.onCreate(sqLiteDatabase);
    }

    private void populateInitialData(){
        //in the future, I will do this in a more automated way

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2){
        //TODO: Actually do upgrade logic
    }
}
