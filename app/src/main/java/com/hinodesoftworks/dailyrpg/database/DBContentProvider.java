package com.hinodesoftworks.dailyrpg.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class DBContentProvider extends ContentProvider {

    private DBController database;

    //ids for uris
    private static final int CHAR_TABLE = 10;
    private static final int CLASS_TABLE = 20;
    private static final int ENEMY_TABLE = 30;
    private static final int ITEM_TABLE = 40;

    //content uri vars
    private static final String AUTHORITY = "com.hinodesoftworks.dailyrpg.contentprovider";
    private static final String BASE_PATH = "data";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);


    //matcher
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static{
        uriMatcher.addURI(AUTHORITY, BASE_PATH, CHAR_TABLE);
        uriMatcher.addURI(AUTHORITY, BASE_PATH, CLASS_TABLE);
        uriMatcher.addURI(AUTHORITY, BASE_PATH, ENEMY_TABLE);
        uriMatcher.addURI(AUTHORITY, BASE_PATH, ITEM_TABLE);
    }


    public DBContentProvider() {
    }

    @Override
    public boolean onCreate() {
        database = new DBController(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        //query builder for safe-ish querys; basic injection protection.
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        //TODO: Check projection for valid column names for errors

        int uriType = uriMatcher.match(uri);

        switch (uriType){
            case CHAR_TABLE:
                queryBuilder.setTables(CharacterTable.TABLE_NAME);
                break;
            case CLASS_TABLE:
                queryBuilder.setTables(ClassesTable.TABLE_NAME);
                break;
            case ITEM_TABLE:
                queryBuilder.setTables(ItemTable.TABLE_NAME);
                break;
            case ENEMY_TABLE:
                queryBuilder.setTables(EnemyTable.TABLE_NAME);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs,
                null, null, sortOrder);


        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = database.getWritableDatabase();
        int uriType = uriMatcher.match(uri);
        long id = 0;

        switch (uriType){
            case CHAR_TABLE:
                id = db.insert(CharacterTable.TABLE_NAME, null, values);
                break;
            case CLASS_TABLE:
                id = db.insert(ClassesTable.TABLE_NAME, null, values);
                break;
            case ITEM_TABLE:
                id = db.insert(ItemTable.TABLE_NAME, null, values);
                break;
            case ENEMY_TABLE:
                id = db.insert(EnemyTable.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = database.getWritableDatabase();
        int uriType = uriMatcher.match(uri);
        int rowsDeleted = 0;

        switch (uriType){
            case CHAR_TABLE:
                rowsDeleted = db.delete(CharacterTable.TABLE_NAME, selection, selectionArgs);
                break;
            case CLASS_TABLE:
                rowsDeleted = db.delete(ClassesTable.TABLE_NAME, selection, selectionArgs);
                break;
            case ITEM_TABLE:
                rowsDeleted = db.delete(ItemTable.TABLE_NAME, selection, selectionArgs);
                break;
            case ENEMY_TABLE:
                rowsDeleted = db.delete(EnemyTable.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = database.getWritableDatabase();
        int uriType = uriMatcher.match(uri);
        int rowsUpdated = 0;

        switch (uriType){
            case CHAR_TABLE:
                rowsUpdated = db.update(CharacterTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case CLASS_TABLE:
                rowsUpdated = db.update(ClassesTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case ITEM_TABLE:
                rowsUpdated = db.update(ItemTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case ENEMY_TABLE:
                rowsUpdated = db.update(EnemyTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        return rowsUpdated;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }




}
