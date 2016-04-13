package com.github.jackkell.mimicryproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.github.jackkell.mimicryproject.MarkovChain;
import com.github.jackkell.mimicryproject.databaseobjects.DatabaseOpenHelper;
import com.github.jackkell.mimicryproject.entity.Impersonator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImpersonatorDao implements Dao<Impersonator> {

    private static final String[] GET_COLUMNS = {
            Impersonator.ID,
            Impersonator.NAME,
            Impersonator.DATE_CREATED,
            Impersonator.MARKOV_CHAIN
    };
    private static final String SORT_ORDER = Impersonator.NAME + " DESC";

    private DatabaseOpenHelper dbHelper;

    public ImpersonatorDao(Context context) {
        dbHelper = new DatabaseOpenHelper(context);
    }

    @Override
    public Long create(Impersonator object) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // The values to insert
        ContentValues values = new ContentValues();
        values.put(Impersonator.NAME, object.getName());
        values.put(Impersonator.DATE_CREATED, object.getDateCreated());
        values.put(Impersonator.MARKOV_CHAIN, object.getMarkovChain().toString());

        // Insert into the database, returning the new record id
        long id = db.insert(Impersonator.TABLE_NAME, "null", values);
        object.setId(id);

        return id;
    }

    @Override
    public Impersonator get(Long id) {
        Impersonator emptyImpersonator = new Impersonator();

        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            // Select the impersonator data
            Cursor impersonatorCursor = db.rawQuery("SELECT * FROM "+ Impersonator.TABLE_NAME + " where id = ?", new String[]{String.valueOf(id)});
            impersonatorCursor.moveToFirst();
            String name = impersonatorCursor.getString(
                    impersonatorCursor.getColumnIndexOrThrow(Impersonator.NAME)
            );
            Date dateCreated = new Date(impersonatorCursor.getLong(
                    impersonatorCursor.getColumnIndexOrThrow(Impersonator.DATE_CREATED)
            ));
            JSONObject json = new JSONObject(impersonatorCursor.getString(
                    impersonatorCursor.getColumnIndexOrThrow(Impersonator.MARKOV_CHAIN)
            ));
            MarkovChain chain = new MarkovChain(json);

            // Select the twitter users
            JSONArray twitterUsers = new JSONArray();
            Cursor twitterUserCursor = db.rawQuery("SELECT * FROM TWITTER_USER WHERE IMPERSONATOR_ID = ?", new String[]{String.valueOf(id)});
            twitterUserCursor.moveToFirst();
            while(!twitterUserCursor.isAfterLast()){
                Long twitterTweetId = twitterUserCursor.getLong(twitterUserCursor.getColumnIndexOrThrow("TWEET_ID"));
                String twitterUsername = twitterUserCursor.getString(twitterUserCursor.getColumnIndexOrThrow("USERNAME"));
                JSONObject obj = new JSONObject();
                obj.put("tweetId", twitterTweetId);
                obj.put("username", twitterUsername);
                twitterUsers.put(obj);
            }

            return new Impersonator(name, dateCreated, new JSONObject().put("twitterUsers", twitterUsers),chain);

        } catch(JSONException e) {
            Log.e("ImpersonatorDao", "There's a problem retrieving the markov chain json data.", e);
        }

        return emptyImpersonator;
    }

    @Override
    public void update(Impersonator object) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // The values to update
        ContentValues values = new ContentValues();
        values.put(Impersonator.NAME, object.getName());
        values.put(Impersonator.MARKOV_CHAIN, object.getMarkovChain().toString());

        // Which row to update, based on the ID
        String where = Impersonator.ID + " = ?";
        String[] whereArgs = { String.valueOf(object.getId()) };

        db.update(Impersonator.TABLE_NAME, values, where, whereArgs);
    }

    @Override
    public void delete(Long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String where = Impersonator.ID + " = ?";
        String[] whereArgs = { String.valueOf(id) };

        db.delete(Impersonator.TABLE_NAME, where, whereArgs);
    }

    public List<Impersonator> list() {
        List<Impersonator> impersonators = new ArrayList<>();

        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "SELECT * FROM " + Impersonator.TABLE_NAME + " ASC";
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();

            while(!cursor.isAfterLast()) {
                Long id = cursor.getLong(
                        cursor.getColumnIndexOrThrow(Impersonator.ID)
                );

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(Impersonator.NAME)
                );
                Date dateCreated = new Date(cursor.getLong(
                        cursor.getColumnIndexOrThrow(Impersonator.DATE_CREATED)
                ));
                JSONObject json = new JSONObject(cursor.getString(
                        cursor.getColumnIndexOrThrow(Impersonator.MARKOV_CHAIN)
                ));
                MarkovChain chain = new MarkovChain(json);

                Impersonator impersonator = new Impersonator(name, dateCreated, chain);
                impersonator.setId(id);

                impersonators.add(impersonator);
            }

        } catch(JSONException e) {
            Log.e("ImpersonatorDao", "There's a problem retrieving the markov chain json data.", e);
        }

        return impersonators;
    }
}
