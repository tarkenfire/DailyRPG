package com.hinodesoftworks.dailyrpg.database;

import android.database.sqlite.SQLiteDatabase;

public class CharacterTable {
    //DB construction variables
    public static final String TABLE_NAME = "characters";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "charName";
    public static final String COLUMN_LEVEL = "charLevel";
    public static final String COLUMN_EXP = "charExp";
    public static final String COLUMN_CLASS = "charClass";
    public static final String COLUMN_ITEMS = "charItems";

    //DB construction statement
    public static final String CREATE_STRING = "create table " +
            TABLE_NAME +
            "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_NAME + " text not null, " +
            COLUMN_LEVEL + " integer not null, " +
            COLUMN_EXP + " integer not null, " +
            COLUMN_CLASS + " text not null, " +
            COLUMN_ITEMS + " text not null" +
            ");";


    //helper methods
    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_STRING);
    }

    public static void onUpgrade() {
        //TODO: implement upgrade logic
    }
}
