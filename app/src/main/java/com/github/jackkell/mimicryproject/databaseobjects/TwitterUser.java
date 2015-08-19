package com.github.jackkell.mimicryproject.databaseobjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TwitterUser implements DatabaseStorable {
    private String username;
    public List<String> tweets;

    public TwitterUser(String username, List<String> tweets) {
        this.username = username;
        this.tweets = tweets;
    }

    @Override
    public void addToDatabase(SQLiteDatabase db) {
        String twitterUserTable = DatabaseOpenHelper.TWITTER_USER;
        ContentValues cv = new ContentValues();
        cv.put(DatabaseOpenHelper.TWITTER_USER_USERNAME, username);
        db.insert(twitterUserTable, null, cv);
        for (String tweet : tweets) {
            ContentValues newTweetRecord = new ContentValues();
            newTweetRecord.put(DatabaseOpenHelper.TWEET_TWITTER_USER_ID, getID(db));
            newTweetRecord.put(DatabaseOpenHelper.TWEET_BODY, tweet);
            db.insert(DatabaseOpenHelper.TWEET, null, newTweetRecord);
        }
    }

    @Override
    public void removeFromDatabase(SQLiteDatabase db) {
        String twitterUserTable = DatabaseOpenHelper.TWITTER_USER;
        String twitterUserID = getID(db);
        db.delete(DatabaseOpenHelper.TWEET, DatabaseOpenHelper.TWEET_TWITTER_USER_ID + " = " + twitterUserID, null);
        db.delete(twitterUserTable, DatabaseOpenHelper.TWITTER_USER_ID + " = " + twitterUserID, null);
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
