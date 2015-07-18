package com.github.jackkell.mimicryproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "mimicry.db";
    private static final String TWEET_TABLE_NAME = "tweet";
    private static final String TWEET_TABLE_CREATE = "" +
            "CREATE TABLE " + TWEET_TABLE_NAME + " ( " +
            "id INTEGER, user VARCHAR(255) );";

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TWEET_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
