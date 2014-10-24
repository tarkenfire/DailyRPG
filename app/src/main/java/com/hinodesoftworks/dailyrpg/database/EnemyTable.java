package com.hinodesoftworks.dailyrpg.database;

import android.database.sqlite.SQLiteDatabase;

public class EnemyTable
{
    //DB construction variables
    public static final String TABLE_NAME = "enemies";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "enemyName";
    public static final String COLUMN_LEVEL = "enemyLevel";
    public static final String COLUMN_HP = "enemyHP";
    public static final String COLUMN_MP = "enemyMP";
    public static final String COLUMN_ATK = "enemyAtk";
    public static final String COLUMN_DEF = "enemyDef";
    public static final String COLUMN_MATK = "enemyMAtk";
    public static final String COLUMN_MDEF = "enemyMDef";
    public static final String COLUMN_EXP_VALUE = "enemyExpValue";

    //DB construction statement
    public static final String CREATE_STRING = "create table " +
            TABLE_NAME +
            "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_NAME + " text not null, " +
            COLUMN_LEVEL + " integer not null, " +
            COLUMN_HP + " integer not null, " +
            COLUMN_MP + " integer not null, " +
            COLUMN_ATK + " integer not null, " +
            COLUMN_DEF + " integer not null, " +
            COLUMN_MATK + " integer not null, " +
            COLUMN_MDEF + " integer not null, " +
            COLUMN_EXP_VALUE + " integer not null" +
            ");";


    //helper methods
    public static void onCreate(SQLiteDatabase database){
        database.execSQL(CREATE_STRING);
    }

    public static void onUpgrade(){
        //TODO: implement upgrade logic
    }
}
