package com.github.jackkell.mimicryproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

public class TwitterUser implements DatabaseStorable {
    private String username;

    public TwitterUser(String username) {
        this.username = username;
    }

    @Override
    public void addToDatabase(SQLiteDatabase db) {
        String twitterUserTable = DatabaseOpenHelper.TWITTER_USER;
        ContentValues cv = new ContentValues();
        cv.put(DatabaseOpenHelper.TWITTER_USER_USERNAME, username);
        db.insert(twitterUserTable, null, cv);
    }

    @Override
    public void removeFromDatabase(SQLiteDatabase db) {
        String twitterUserTable = DatabaseOpenHelper.TWITTER_USER;
        db.delete(twitterUserTable, DatabaseOpenHelper.TWITTER_USER_ID + " = " + getID(db), null);
    }

    @Override
    public String getID(SQLiteDatabase db) {
        String twitterUserTable = DatabaseOpenHelper.TWITTER_USER;
        String[] searchColumns = new String[1];
        searchColumns[0] = DatabaseOpenHelper.TWITTER_USER_ID;
        String selectionColumns = DatabaseOpenHelper.TWITTER_USER_USERNAME;

        Cursor cursor = db.query(twitterUserTable, searchColumns, selectionColumns + " = '" + this.username + "'", null, null, null, null, null);

        cursor.moveToFirst();

        String ID = cursor.getString(0);
        cursor.close();
        return ID;
    }

    public String getUsername() {
        return username;
    }
}
