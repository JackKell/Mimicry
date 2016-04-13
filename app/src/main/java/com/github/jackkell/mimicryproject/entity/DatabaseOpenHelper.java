package com.github.jackkell.mimicryproject.entity;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
        db.execSQL(ImpersonatorPost.CREATE_TABLE);
        db.execSQL(Impersonator.CREATE_TABLE);
        db.execSQL(TwitterUser.CREATE_TABLE);
    }

    @Override
    //This runs when something in the database is edited
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ImpersonatorPost.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Impersonator.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TwitterUser.TABLE_NAME);
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
