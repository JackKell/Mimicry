package com.github.jackkell.mimicryproject.databaseobjects;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.github.jackkell.mimicryproject.entity.Impersonator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

/*
DatabaseOpenHelper is a class that is used to interact with the SQL lite database from throughout
the application.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    //The current database version
    private static final int DATABASE_VERSION = 1;
    //The name of the database
    private static final String DATABASE_NAME = "mimicry.db";
    //The file path to the database
    private static String DB_PATH;

    //The actual sqlite database object
    private SQLiteDatabase myDataBase;

    //The current context that the database is operating within
    private final Context myContext;

    //A date formatter that is used to change date-times to show only the month-day-year:hours-seconds-milliseconds
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("MM-dd-yyyy:HH-mm-ss-SSS", Locale.ENGLISH);
    public static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("MM-dd", Locale.ENGLISH);

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

    //MIMICRY_USER TABLE
    public static final String MIMICRY_USER = "MIMICRY_USER";
    public static final String MIMICRY_USER_ID = "ID";
    public static final String MIMICRY_USER_TWITTER_USERNAME = "TWITTER_USERNAME";

    //TWITTER_USER TABLE
    public static final String TWITTER_USER = "TWITTER_USER";
    public static final String TWITTER_USER_ID = "ID";
    public static final String TWITTER_USER_USERNAME = "USERNAME";

    //MIMICRY_USER_IMPERSONATOR TABLE
    public static final String MIMICRY_USER_IMPERSONATOR = "MIMICRY_USER_IMPERSONATOR";
    public static final String MIMICRY_USER_IMPERSONATOR_ID = "ID";
    public static final String MIMICRY_USER_IMPERSONATOR_MIMICRY_USER_ID = "MIMICRY_USER_ID";
    public static final String MIMICRY_USER_IMPERSONATOR_IMPERSONATOR_ID = "IMPERSONATOR_ID";

    //Create Strings
    //The SQL Statement used to create the TWEET table
    private static final String TWEET_TABLE_CREATE =
            "CREATE TABLE " + TWEET + " ( " +
                    TWEET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TWEET_TWITTER_USER_ID + " INTEGER NOT NULL, " +
                    TWEET_BODY + " TEXT NOT NULL, " +
                    "FOREIGN KEY(" + TWEET_TWITTER_USER_ID + ") REFERENCES " + TWITTER_USER + "(" + TWEET_TWITTER_USER_ID + "));";
    //The SQL Statement used to create the POST table
    private static final String POST_TABLE_CREATE =
            "CREATE TABLE " + POST + " ( " +
                    POST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    POST_IMPERSONATOR_ID + " INTEGER NOT NULL, " +
                    POST_BODY + " TEXT NOT NULL, " +
                    POST_IS_FAVORITED + " INTEGER NOT NULL, " +
                    POST_IS_TWEETED + " INTEGER NOT NULL, " +
                    POST_DATE_CREATED + "DATE NOT NULL, " +
                    "FOREIGN KEY(" + POST_IMPERSONATOR_ID + ") REFERENCES " + Impersonator.TABLE_NAME + "(" + Impersonator.ID + "));";
    //The SQL Statement used to create the IMPERSONATOR_TWITTER_USER table
    private static final String IMPERSONATOR_TWITTER_USER_TABLE_CREATE =
            "CREATE TABLE " + IMPERSONATOR_TWITTER_USER + " ( " +
                    IMPERSONATOR_TWITTER_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    IMPERSONATOR_TWITTER_USER_IMPERSONATOR_ID + " INTEGER NOT NULL, " +
                    IMPERSONATOR_TWITTER_USER_TWITTER_USER_ID + " INTEGER NOT NULL, " +
                    "FOREIGN KEY(" + IMPERSONATOR_TWITTER_USER_IMPERSONATOR_ID + ") REFERENCES " + Impersonator.TABLE_NAME + "(" + Impersonator.ID + "), " +
                    "FOREIGN KEY(" + IMPERSONATOR_TWITTER_USER_TWITTER_USER_ID + ") REFERENCES " + TWITTER_USER + "(" + TWITTER_USER_ID + "));";

    //The SQL Statement used to create the MIMICRY_USER table
    private static final String MIMICRY_USER_TABLE_CREATE =
            "CREATE TABLE " + MIMICRY_USER + " ( " +
                    MIMICRY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MIMICRY_USER_TWITTER_USERNAME + " VARCHAR(255) NOT NULL);";
    //The SQL Statement used to create the TWITTER_USER table
    private static final String TWITTER_USER_TABLE_CREATE =
            "CREATE TABLE " + TWITTER_USER + " ( " +
                    TWITTER_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TWITTER_USER_USERNAME + " VARCHAR(255) NOT NULL);";

    //The SQL Statement used to create the MIMICRY_USER_IMPERSONATOR table
    private static final String MIMICRY_USER_IMPERSONATOR_TABLE_CREATE =
            "CREATE TABLE " + MIMICRY_USER_IMPERSONATOR + " ( " +
                    MIMICRY_USER_IMPERSONATOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MIMICRY_USER_IMPERSONATOR_MIMICRY_USER_ID + " INTEGER NOT NULL, " +
                    MIMICRY_USER_IMPERSONATOR_IMPERSONATOR_ID + " INTEGER NOT NULL, " +
                    "FOREIGN KEY(" + MIMICRY_USER_IMPERSONATOR_MIMICRY_USER_ID + ") REFERENCES " + MIMICRY_USER + "(" + MIMICRY_USER_ID + "), " +
                    "FOREIGN KEY(" + MIMICRY_USER_IMPERSONATOR_IMPERSONATOR_ID + ") REFERENCES " + Impersonator.TABLE_NAME + "(" + Impersonator.ID + "));";


    // Constructor
    // Used to create the DatabaseOpenHelper object
    public DatabaseOpenHelper(Context context) {
        //super uses the SQLiteOpenHelper Constructor to help form our DatabaseOpenHelper object
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        DB_PATH = myContext.getFilesDir().getAbsolutePath().replace("files", "databases") + File.separator;
    }


    @Override
    //This runs every time a DatabaseOpenHelper object is created
    public void onCreate(SQLiteDatabase db) {
        Log.d("DatabaseOpenHelper", "TEST LOG");
        //db.execSQL(TWEET_TABLE_CREATE);
        //db.execSQL(POST_TABLE_CREATE);
        //db.execSQL(IMPERSONATOR_TWITTER_USER_TABLE_CREATE);
        db.execSQL(Impersonator.CREATE_TABLE);
        //db.execSQL(MIMICRY_USER_TABLE_CREATE);
        //db.execSQL(TWITTER_USER_TABLE_CREATE);
        //db.execSQL(MIMICRY_USER_IMPERSONATOR_TABLE_CREATE);
    }

    @Override
    //This runs when something in the database is edited
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TWEET);
        db.execSQL("DROP TABLE IF EXISTS " + POST);
        db.execSQL("DROP TABLE IF EXISTS " + IMPERSONATOR_TWITTER_USER);
        db.execSQL("DROP TABLE IF EXISTS " + Impersonator.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MIMICRY_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TWITTER_USER);
        db.execSQL("DROP TABLE IF EXISTS " + MIMICRY_USER_IMPERSONATOR);
        onCreate(db);
    }

    @Override
    //This runs when the DatabaseObjectHelper object is closed
    //Objects are closed to prevent leakage
    public synchronized void close(){
        if (myDataBase != null)
            myDataBase.close();

        super.close();
    }

    //This runs when the database is created.  It makes a writable database that can be interacted with
    private void createDatabase() throws IOException {
        //onUpgrade(this.getWritableDatabase(), 1, 1);
        if (!checkDataBase()){
            this.getWritableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    //This grabs a pre-existing database
    public SQLiteDatabase getDatabase(Context context) {
        Log.d("DatabaseOpenHelper", "getDatabase()");
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
        return dbh.getWritableDatabase();
    }

    //This runs when the database is copied into memory
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

    //In order to interact with the database, it first must be opened
    private void openDataBase() throws SQLException {

        // Open the database
        String myPath = DB_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    //This checks the database
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
