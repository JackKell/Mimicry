package com.github.jackkell.mimicryproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.jackkell.mimicryproject.entity.DatabaseOpenHelper;
import com.github.jackkell.mimicryproject.entity.TwitterUser;

public class TwitterUserDao implements Dao<TwitterUser> {

    private DatabaseOpenHelper dbHelper;

    public TwitterUserDao(Context context) {
        dbHelper = new DatabaseOpenHelper(context);
    }

    @Override
    public Long create(TwitterUser object) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TwitterUser.IMPERSONATOR_ID, object.getImpersonatorId());
        values.put(TwitterUser.USERNAME, object.getUsername());
        values.put(TwitterUser.LAST_TWEET_ID, object.getLastTweetId());

        long id = db.insert(TwitterUser.TABLE_NAME, "null", values);
        object.setId(id);

        return id;
    }

    @Override
    public TwitterUser get(Long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor twitterUserCursor = db.rawQuery("SELECT * FROM " + TwitterUser.TABLE_NAME + " where id = ? ", new String[]{String.valueOf(id)});

        Long impersonatorId = twitterUserCursor.getLong(
                twitterUserCursor.getColumnIndexOrThrow(TwitterUser.IMPERSONATOR_ID)
        );
        String username = twitterUserCursor.getString(
                twitterUserCursor.getColumnIndexOrThrow(TwitterUser.USERNAME)
        );
        Long lastTweetId = twitterUserCursor.getLong(
                twitterUserCursor.getColumnIndexOrThrow(TwitterUser.LAST_TWEET_ID)
        );

        twitterUserCursor.close();

        return new TwitterUser(impersonatorId, username, lastTweetId);
    }

    @Override
    public void update(TwitterUser object) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TwitterUser.LAST_TWEET_ID, object.getLastTweetId());

        // Which row to update, based on the ID
        String where = TwitterUser.ID + " = ?";
        String[] whereArgs = { String.valueOf(object.getId()) };

        db.update(TwitterUser.TABLE_NAME, values, where, whereArgs);
    }

    @Override
    public void delete(Long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String where = TwitterUser.ID + " = ?";
        String[] whereArgs = { String.valueOf(id) };

        db.delete(TwitterUser.TABLE_NAME, where, whereArgs);
    }
}
