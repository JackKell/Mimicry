package com.github.jackkell.mimicryproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.github.jackkell.mimicryproject.MarkovChain;
import com.github.jackkell.mimicryproject.entity.DatabaseOpenHelper;
import com.github.jackkell.mimicryproject.entity.Impersonator;
import com.github.jackkell.mimicryproject.entity.ImpersonatorPost;
import com.github.jackkell.mimicryproject.entity.TwitterUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImpersonatorDao implements Dao<Impersonator> {

    private DatabaseOpenHelper dbHelper;
    private Context context;

    public ImpersonatorDao(Context context) {
        this.context = context;
        dbHelper = new DatabaseOpenHelper(context);
    }

    @Override
    public Long create(Impersonator object) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // The values to insert
        ContentValues values = new ContentValues();
        values.put(Impersonator.NAME, object.getName());
        values.put(Impersonator.DATE_CREATED, object.getDateCreated().getTime());
        values.put(Impersonator.MARKOV_CHAIN, String.valueOf(object.getMarkovChain().toJson()));

        // Insert into the database, returning the new record id
        Long id = db.insert(Impersonator.TABLE_NAME, "null", values);

        ImpersonatorPostDao postDao = new ImpersonatorPostDao(context);
        for (ImpersonatorPost post : object.getImpersonatorPosts()) {
            post.setImpersonatorId(id);
            postDao.create(post);
        }

        TwitterUserDao twitterUserDao = new TwitterUserDao(context);
        for (TwitterUser twitterUser : object.getTwitterUsers()) {
            twitterUser.setImpersonatorId(id);
            twitterUserDao.create(twitterUser);
        }
        
        object.setId(id);

        return id;
    }

    @Override
    public Impersonator get(Long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Select the impersonator data
        String impersonatorQuery = "SELECT * FROM "+ Impersonator.TABLE_NAME + " WHERE " + Impersonator.ID + " = ? ";
        String[] impersontorQueryArgs = new String[]{String.valueOf(id)};
        Cursor impersonatorCursor = db.rawQuery(impersonatorQuery, impersontorQueryArgs);

        impersonatorCursor.moveToFirst();
        String name = impersonatorCursor.getString(
                impersonatorCursor.getColumnIndexOrThrow(Impersonator.NAME)
        );
        Date dateCreated = new Date(impersonatorCursor.getLong(
                impersonatorCursor.getColumnIndexOrThrow(Impersonator.DATE_CREATED)
        ));
        JSONObject json = null;
        try {
            String variable = impersonatorCursor.getString(
                    impersonatorCursor.getColumnIndexOrThrow(Impersonator.MARKOV_CHAIN));
            json = new JSONObject(variable);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MarkovChain chain = new MarkovChain(json);
        impersonatorCursor.close();

        List<TwitterUser> twitterUsers = new ArrayList<>();
        TwitterUserDao twitterUserDao = new TwitterUserDao(context);

        String twitterUserQuery = "SELECT * FROM " + TwitterUser.TABLE_NAME + " WHERE " + TwitterUser.IMPERSONATOR_ID + " = ? ";
        String[] twitterUserQueryArgs = new String[] {String.valueOf(id)};
        Cursor twitterUserCursor = db.rawQuery(twitterUserQuery, twitterUserQueryArgs);

        if (twitterUserCursor.moveToFirst()) {
            do {
                Long twitterUserId = twitterUserCursor.getLong(
                        twitterUserCursor.getColumnIndexOrThrow(TwitterUser.IMPERSONATOR_ID)
                );
                twitterUsers.add(twitterUserDao.get(twitterUserId));
            } while (twitterUserCursor.moveToNext());
        }

        twitterUserCursor.close();

        List<ImpersonatorPost> posts = new ArrayList<>();
        ImpersonatorPostDao impersonatorPostDao = new ImpersonatorPostDao(context);

        String postQuery = "SELECT " + ImpersonatorPost.ID + " FROM " + ImpersonatorPost.TABLE_NAME + " WHERE " + ImpersonatorPost.IMPERSONATOR_ID + " = ? ";

        String[] postQueryArgs = new String[] {String.valueOf(id)};
        Cursor postQueryCursor = db.rawQuery(postQuery, postQueryArgs);

        if (postQueryCursor.moveToFirst()) {
            do {
                Long postId = postQueryCursor.getLong(
                        postQueryCursor.getColumnIndexOrThrow(TwitterUser.IMPERSONATOR_ID)
                );
                posts.add(impersonatorPostDao.get(postId));
            } while (postQueryCursor.moveToNext());
        }

        postQueryCursor.close();

        return new Impersonator(id, name, dateCreated, chain, twitterUsers, posts);
    }

    @Override
    public void update(Impersonator object) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // The values to update
        ContentValues values = new ContentValues();
        values.put(Impersonator.NAME, object.getName());
        values.put(Impersonator.MARKOV_CHAIN, object.getMarkovChain().toString());

        ImpersonatorPostDao impersonatorPostDao = new ImpersonatorPostDao(context);

        for (ImpersonatorPost post: object.getImpersonatorPosts()) {
            impersonatorPostDao.update(post);
        }

        TwitterUserDao twitterUserDao = new TwitterUserDao(context);

        for (TwitterUser twitterUser: object.getTwitterUsers()) {
            twitterUserDao.update(twitterUser);
        }

        // Which row to update, based on the ID
        String impersonatorQuery = Impersonator.ID + " LIKE ?";
        String[] impersonatorQueryArgs = { String.valueOf(object.getId()) };

        db.update(Impersonator.TABLE_NAME, values, impersonatorQuery, impersonatorQueryArgs);
    }

    @Override
    public void delete(Long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String postQuery = "SELECT " + ImpersonatorPost.ID + " FROM " + ImpersonatorPost.TABLE_NAME + " WHERE " + ImpersonatorPost.IMPERSONATOR_ID + " = ?";
        String[] postQueryArgs = { String.valueOf(id) };

        Cursor postQueryCursor = db.rawQuery(postQuery, postQueryArgs);

        ImpersonatorPostDao impersonatorPostDao = new ImpersonatorPostDao(context);
        if (postQueryCursor.moveToFirst()) {
            do {
                Long postId = postQueryCursor.getLong(
                        postQueryCursor.getColumnIndexOrThrow(ImpersonatorPost.ID)
                );
                impersonatorPostDao.delete(postId);
            } while (postQueryCursor.moveToNext());
        }
        postQueryCursor.close();

        String twitterUserQuery = "SELECT " + TwitterUser.ID + " FROM " + TwitterUser.TABLE_NAME + " WHERE " + TwitterUser.IMPERSONATOR_ID + " = ?";
        String[] twitterUserQueryArgs = { String.valueOf(id) };

        Cursor twitterUserCursor = db.rawQuery(twitterUserQuery, twitterUserQueryArgs);

        TwitterUserDao twitterUserDao = new TwitterUserDao(context);
        if (twitterUserCursor.moveToFirst()) {
            do {
                Long twitterId = twitterUserCursor.getLong(
                        twitterUserCursor.getColumnIndexOrThrow(TwitterUser.ID)
                );
                twitterUserDao.delete(twitterId);
            } while (twitterUserCursor.moveToNext());
        }

        twitterUserCursor.close();


        String impersonatorQuery = Impersonator.ID + " LIKE ?";
        String[] impersonatorQueryArgs = { String.valueOf(id) };
        db.delete(Impersonator.TABLE_NAME, impersonatorQuery, impersonatorQueryArgs);
    }

    public List<Impersonator> list() {
        List<Impersonator> impersonators = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT " + Impersonator.ID + " FROM " + Impersonator.TABLE_NAME + " ORDER BY " + Impersonator.DATE_CREATED + " DESC ";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                 Long impersonatorId = cursor.getLong(
                         cursor.getColumnIndexOrThrow(Impersonator.ID)
                 );
                impersonators.add(get(impersonatorId));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return impersonators;
    }
}
