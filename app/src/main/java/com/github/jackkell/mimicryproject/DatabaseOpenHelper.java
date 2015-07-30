package com.github.jackkell.mimicryproject;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "mimicry.db";

    private static String DB_PATH;

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    //TWEET TABLE
    public static final String TWEET = "TWEET";
    public static final String TWEET_ID = "ID";
    public static final String TWEET_TWITTER_USER_ID = "TWITTER_USER_ID";
    public static final String TWEET_BODY = "TWEET_BODY";

    //POST TABLE
    public static final String POST = "POST";
    public static final String POST_ID = "ID";
    public static final String POST_IMPERSONATOR_ID = "IMPERSONATOR_ID";
    public static final String POST_BODY = "BODY";
    public static final String POST_IS_FAVORITED = "IS_FAVORITED";
    public static final String POST_IS_TWEETED = "IS_TWEETED";
    public static final String POST_DATE_CREATED = "DATE_CREATED";

    //IMPERSONATOR_TWITTER_USER TABLE
    public static final String IMPERSONATOR_TWITTER_USER = "IMPERSONATOR_TWITTER_USER";
    public static final String IMPERSONATOR_TWITTER_USER_ID = "ID";
    public static final String IMPERSONATOR_TWITTER_USER_IMPERSONATOR_ID = "IMPERSONATOR_ID";
    public static final String IMPERSONATOR_TWITTER_USER_TWITTER_USER_ID = "TWITTER_USER_ID";

    //IMPERSONATOR TABLE
    public static final String IMPERSONATOR = "IMPERSONATOR";
    public static final String IMPERSONATOR_ID = "ID";
    public static final String IMPERSONATOR_NAME = "NAME";
    public static final String IMPERSONATOR_DATE_CREATED = "DATE_CREATED";

    //MIMICRY_USER TABLE
    public static final String MIMICRY_USER = "MIMICRY_USER";
    public static final String MIMICRY_USER_ID = "ID";
    public static final String MIMICRY_USER_TWITTER_USERNAME = "TWITTER_USERNAME";

    public static final String TWITTER_USER = "TWITTER_USER";
    public static final String TWITTER_USER_ID = "ID";
    public static final String TWITTER_USER_USERNAME = "USERNAME";

    public static final String MIMICRY_USER_IMPERSONATOR = "MIMICRY_USER_IMPERSONATOR";
    public static final String MIMICRY_USER_IMPERSONATOR_ID = "ID";
    public static final String MIMICRY_USER_IMPERSONATOR_MIMICRY_USER_ID = "MIMICRY_USER_ID";
    public static final String MIMICRY_USER_IMPERSONATOR_IMPERSONATOR_ID = "IMPERSONATOR_ID";

    //Create Strings
    private static final String TWEET_TABLE_CREATE =
            "CREATE TABLE " + TWEET + " ( " +
                    TWEET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TWEET_TWITTER_USER_ID + " INTEGER NOT NULL, " +
                    TWEET_BODY + " TEXT NOT NULL, " +
                    "FOREIGN KEY(" + TWEET_TWITTER_USER_ID + ") REFERENCES " + TWITTER_USER + "(" + TWEET_TWITTER_USER_ID + "));";
    private static final String POST_TABLE_CREATE =
            "CREATE TABLE " + POST + " ( " +
                    POST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    POST_IMPERSONATOR_ID + " INTEGER NOT NULL, " +
                    POST_BODY + " TEXT NOT NULL, " +
                    POST_IS_FAVORITED + " INTEGER NOT NULL, " +
                    POST_IS_TWEETED + " INTEGER NOT NULL, " +
                    POST_DATE_CREATED + "DATE NOT NULL, " +
                    "FOREIGN KEY(" + POST_IMPERSONATOR_ID + ") REFERENCES " + IMPERSONATOR + "(" + IMPERSONATOR_ID + "));";
    private static final String IMPERSONATOR_TWITTER_USER_TABLE_CREATE =
            "CREATE TABLE " + IMPERSONATOR_TWITTER_USER + " ( " +
                    IMPERSONATOR_TWITTER_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    IMPERSONATOR_TWITTER_USER_IMPERSONATOR_ID + " INTEGER NOT NULL, " +
                    IMPERSONATOR_TWITTER_USER_TWITTER_USER_ID + " INTEGER NOT NULL, " +
                    "FOREIGN KEY(" + IMPERSONATOR_TWITTER_USER_IMPERSONATOR_ID + ") REFERENCES " + IMPERSONATOR + "(" + IMPERSONATOR_ID + "), " +
                    "FOREIGN KEY(" + IMPERSONATOR_TWITTER_USER_TWITTER_USER_ID + ") REFERENCES " + TWITTER_USER + "(" + TWITTER_USER_ID + "));";
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
                    MIMICRY_USER_IMPERSONATOR_IMPERSONATOR_ID + " INTEGER NOT NULL, " +
                    "FOREIGN KEY(" + MIMICRY_USER_IMPERSONATOR_MIMICRY_USER_ID + ") REFERENCES " + MIMICRY_USER + "(" + MIMICRY_USER_ID + "), " +
                    "FOREIGN KEY(" + MIMICRY_USER_IMPERSONATOR_IMPERSONATOR_ID + ") REFERENCES " + IMPERSONATOR + "(" + IMPERSONATOR_ID + "));";


    // Constructor
    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        DB_PATH = myContext.getFilesDir().getAbsolutePath().replace("files", "databases") + File.separator;
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

    @Override
    public synchronized void close(){
        if (myDataBase != null)
            myDataBase.close();

        super.close();
    }

    private void createDatabase() throws IOException {
        if (!checkDataBase()){
            this.getReadableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    public DatabaseOpenHelper getDatabase(Context context) {
        DatabaseOpenHelper dbh = new DatabaseOpenHelper(context);

        try {
            dbh.createDatabase();
        } catch (IOException e) {
            throw new Error("Unable to create database");
        }

        try {
            dbh.openDataBase();
        } catch (SQLException  sqle) {
            throw new Error("Unable to open database");
        }
        return dbh;
    }

    private void copyDataBase() throws IOException{
        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // Transfer bytes from the inputFile to the outputFile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    private void openDataBase() throws SQLException {

        // Open the database
        String myPath = DB_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }

        if (checkDB != null)
            checkDB.close();

        return checkDB != null;
    }


}
