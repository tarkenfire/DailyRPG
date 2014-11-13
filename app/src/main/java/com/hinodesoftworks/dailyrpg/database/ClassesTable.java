package com.hinodesoftworks.dailyrpg.database;


import android.database.sqlite.SQLiteDatabase;

public class ClassesTable {
    //DB construction variables
    public static final String TABLE_NAME = "classes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "className";
    public static final String COLUMN_BASE_HP = "classBaseHP";
    public static final String COLUMN_BASE_MP = "classBaseMP";
    public static final String COLUMN_BASE_ATK = "charBaseAtk";
    public static final String COLUMN_BASE_MATK = "charBaseMAtk";
    public static final String COLUMN_BASE_DEF = "charBaseDef";
    public static final String COLUMN_BASE_MDEF = "charBaseMDef";

    //DB construction statement
    public static final String CREATE_STRING = "create table " +
            TABLE_NAME +
            "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_NAME + " text not null, " +
            COLUMN_BASE_HP + " integer not null, " +
            COLUMN_BASE_MP + " integer not null, " +
            COLUMN_BASE_ATK + " integer not null, " +
            COLUMN_BASE_MATK + " integer not null, " +
            COLUMN_BASE_DEF + " integer not null, " +
            COLUMN_BASE_MDEF + " integer not null" +
            ");";


    //helper methods
    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_STRING);
    }

    public static void onUpgrade() {
        //TODO: implement upgrade logic
    }
}
