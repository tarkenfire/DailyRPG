package com.hinodesoftworks.dailyrpg.database;


import android.database.sqlite.SQLiteDatabase;

public class ItemTable
{
    //DB construction variables
    public static final String TABLE_NAME = "items";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEM_CLASS = "itemClass";
    public static final String COLUMN_ITEM_NAME = "itemName";
    public static final String COLUMN_EQUIP_MOD_STAT = "equipModStat";
    public static final String COLUMN_EQUIP_MOD_VALUE = "equipModValue";
    public static final String COLUMN_CONSUME_STAT = "consumeStat";
    public static final String COLUMN_CONSUME_VALUE = "consumeValue";

    //DB construction statement
    public static final String CREATE_STRING = "create table " +
            TABLE_NAME +
            "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_ITEM_CLASS + " text not null, " +
            COLUMN_ITEM_NAME + " text not null, " +
            COLUMN_EQUIP_MOD_STAT + " text, " +
            COLUMN_EQUIP_MOD_VALUE + " integer, " +
            COLUMN_CONSUME_STAT + " text, " +
            COLUMN_CONSUME_VALUE + " integer" +
            ");";


    //helper methods
    public static void onCreate(SQLiteDatabase database){
        database.execSQL(CREATE_STRING);
    }

    public static void onUpgrade(){
        //TODO: implement upgrade logic
    }
}
