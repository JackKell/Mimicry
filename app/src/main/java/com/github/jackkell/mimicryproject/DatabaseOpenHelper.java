package com.github.jackkell.mimicryproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "mimicry.db";

    //TWEET TABLE
    private static final String TWEET = "TWEET";
    private static final String TWEET_ID = "ID";
    private static final String TWEET_TWITTER_USER_ID = "TWITTER_USER_ID";
    private static final String TWEET_BODY= "TWEET_BODY";

    //POST TABLE
    private static final String POST = "POST";
    private static final String POST_ID = "ID";
    private static final String POST_IMPERSONATOR_ID = "IMPERSONATOR_ID";
    private static final String POST_BODY = "BODY";
    private static final String POST_IS_FAVORITED = "IS_FAVORITED";
    private static final String POST_IS_TWEETED = "IS_TWEETED";
    private static final String POST_DATE_CREATED = "DATE_CREATED";

    //IMPERSONATOR_TWITTER_USER TABLE
    private static final String IMPERSONATOR_TWITTER_USER = "IMPERSONATOR_TWITTER_USER";
    private static final String IMPERSONATOR_TWITTER_USER_ID = "ID";
    private static final String IMPERSONATOR_TWITTER_USER_IMPERSONATOR_ID = "IMPERSONATOR_ID";
    private static final String IMPERSONATOR_TWITTER_USER_TWITTER_USER_ID = "TWITTER_USER_ID";

    //IMPERSONATOR TABLE
    private static final String IMPERSONATOR = "IMPERSONATOR";
    private static final String IMPERSONATOR_ID = "ID";
    private static final String IMPERSONATOR_NAME = "NAME";
    private static final String IMPERSONATOR_DATE_CREATED = "DATE_CREATED";

    //MIMICRY_USER TABLE
    private static final String MIMICRY_USER = "MIMICRY_USER";
    private static final String MIMICRY_USER_ID = "ID";
    private static final String MIMICRY_USER_TWITTER_USERNAME = "TWITTER_USERNAME";

    private static final String TWITTER_USER = "TWITTER_USER";
    private static final String TWITTER_USER_ID = "ID";
    private static final String TWITTER_USER_USERNAME = "USERNAME";

    private static final String MIMICRY_USER_IMPERSONATOR = "MIMICRY_USER_IMPERSONATOR";
    private static final String MIMICRY_USER_IMPERSONATOR_ID = "ID";
    private static final String MIMICRY_USER_IMPERSONATOR_MIMICRY_USER_ID = "MIMICRY_USER_ID";
    private static final String MIMICRY_USER_IMPERSONATOR_IMPERSONATOR_ID = "IMPERSONATOR_ID";

    //Create Strings
    private static final String TWEET_TABLE_CREATE =
            "CREATE TABLE " + TWEET + " ( " +
                    TWEET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TWEET_TWITTER_USER_ID + " INTEGER NOT NULL, " +
                    "FOREIGN KEY(" + TWEET_TWITTER_USER_ID + ") REFERENCES " + TWITTER_USER + "(" + TWEET_TWITTER_USER_ID + "), " +
                    TWEET_BODY + " VARCHAR(140) NOT NULL);";
    private static final String POST_TABLE_CREATE =
            "CREATE TABLE " + POST + " ( " +
                    POST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    POST_IMPERSONATOR_ID + " INTEGER NOT NULL, " +
                    "FOREIGN KEY(" + POST_IMPERSONATOR_ID + ") REFERENCES " + IMPERSONATOR + "(" + IMPERSONATOR_ID + "), " +
                    POST_BODY + " VARCHAR(140) NOT NULL, " +
                    POST_IS_FAVORITED + " INTEGER NOT NULL, " +
                    POST_IS_TWEETED + " INTEGER NOT NULL, " +
                    POST_DATE_CREATED + "DATE NOT NULL);";
    private static final String IMPERSONATOR_TWITTER_USER_TABLE_CREATE =
            "CREATE TABLE " + IMPERSONATOR_TWITTER_USER + " ( " +
                    IMPERSONATOR_TWITTER_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    IMPERSONATOR_TWITTER_USER_IMPERSONATOR_ID + " INTEGER NOT NULL, " +
                    "FOREIGN KEY(" + IMPERSONATOR_TWITTER_USER_IMPERSONATOR_ID + ") REFERENCES " + IMPERSONATOR + "(" + IMPERSONATOR_ID + "), " +
                    IMPERSONATOR_TWITTER_USER_TWITTER_USER_ID + " INTEGER NOT NULL, " +
                    "FOREIGN KEY(" + IMPERSONATOR_TWITTER_USER_TWITTER_USER_ID + ") REFERENCES " + TWITTER_USER + "(" + TWITTER_USER_ID + ");";
    private static final String IMPERSONATOR_TABLE_CREATE =
            "CREATE TABLE " + IMPERSONATOR + " ( " +
                    IMPERSONATOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    IMPERSONATOR_NAME + " VARCHAR(140) NOT NULL, " +
                    IMPERSONATOR_DATE_CREATED + " DATE NOT NULL);";
    private static final String MIMICRY_USER_TABLE_CREATE =
            "CREATE TABLE " + MIMICRY_USER + " ( " +
                    MIMICRY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MIMICRY_USER_TWITTER_USERNAME + " VARCHAR(255) NOT NULL);";
    private static final String TWITTER_USER_TABLE_CREATE =
            "CREATE TABLE " + TWITTER_USER + " ( " +
                    TWITTER_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TWITTER_USER_USERNAME + " VARCHAR(255) NOT NULL);";
    private static final String MIMICRY_USER_IMPERSONATOR_TABLE_CREATE =
            "CREATE TABLE " + MIMICRY_USER_IMPERSONATOR + " ( " +
                    MIMICRY_USER_IMPERSONATOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MIMICRY_USER_IMPERSONATOR_MIMICRY_USER_ID + " INTEGER NOT NULL, " +
                    "FOREIGN KEY(" + MIMICRY_USER_IMPERSONATOR_MIMICRY_USER_ID + ") REFERENCES " + MIMICRY_USER + "(" + MIMICRY_USER_ID + "), " +
                    MIMICRY_USER_IMPERSONATOR_IMPERSONATOR_ID + " INTEGER NOT NULL, " +
                    "FOREIGN KEY(" + MIMICRY_USER_IMPERSONATOR_IMPERSONATOR_ID + ") REFERENCES " + IMPERSONATOR + "(" + IMPERSONATOR_ID + ");";


    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TWEET_TABLE_CREATE);
        db.execSQL(POST_TABLE_CREATE);
        db.execSQL(IMPERSONATOR_TWITTER_USER_TABLE_CREATE);
        db.execSQL(IMPERSONATOR_TABLE_CREATE);
        db.execSQL(MIMICRY_USER_TABLE_CREATE);
        db.execSQL(TWITTER_USER_TABLE_CREATE);
        db.execSQL(MIMICRY_USER_IMPERSONATOR_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TWEET);
        db.execSQL("DROP TABLE IF EXISTS " + POST);
        db.execSQL("DROP TABLE IF EXISTS " + IMPERSONATOR_TWITTER_USER);
        db.execSQL("DROP TABLE IF EXISTS " + IMPERSONATOR);
        db.execSQL("DROP TABLE IF EXISTS " + MIMICRY_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TWITTER_USER);
        db.execSQL("DROP TABLE IF EXISTS " + MIMICRY_USER_IMPERSONATOR);
        onCreate(db);
    }
}
