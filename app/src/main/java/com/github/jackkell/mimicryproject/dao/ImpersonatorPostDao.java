package com.github.jackkell.mimicryproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.jackkell.mimicryproject.entity.DatabaseOpenHelper;
import com.github.jackkell.mimicryproject.entity.Impersonator;
import com.github.jackkell.mimicryproject.entity.ImpersonatorPost;

import java.util.Date;

public class ImpersonatorPostDao implements Dao<ImpersonatorPost> {

    private DatabaseOpenHelper dbHelper;

    public ImpersonatorPostDao(Context context) {
        dbHelper = new DatabaseOpenHelper(context);
    }

    @Override
    public Long create(ImpersonatorPost object) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ImpersonatorPost.IMPERSONATOR_ID, object.getImpersonatorId());
        values.put(ImpersonatorPost.BODY, object.getBody());
        values.put(ImpersonatorPost.IS_FAVORITED, object.isFavorited());
        values.put(ImpersonatorPost.IS_TWEETED, object.isTweeted());
        values.put(ImpersonatorPost.DATE_CREATED, object.getDateCreated().getTime());

        long id = db.insert(ImpersonatorPost.TABLE_NAME, "null", values);
        object.setId(id);

        return id;
    }

    @Override
    public ImpersonatorPost get(Long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor postCursor = db.rawQuery("SELECT * FROM " + ImpersonatorPost.TABLE_NAME + " WHERE " + Impersonator.ID + " = ? ", new String[]{String.valueOf(id)});

        postCursor.moveToFirst();

        Long impersonatorID = postCursor.getLong(
                postCursor.getColumnIndexOrThrow(ImpersonatorPost.IMPERSONATOR_ID)
        );
        String body = postCursor.getString(
                postCursor.getColumnIndexOrThrow(ImpersonatorPost.BODY)
        );
        Boolean isFavorited =  postCursor.getInt(
                postCursor.getColumnIndexOrThrow(ImpersonatorPost.IS_FAVORITED)
        ) >= 1;
        Boolean isTweeted =  postCursor.getInt(
                postCursor.getColumnIndexOrThrow(ImpersonatorPost.IS_TWEETED)
        ) >= 1;
        Date dateCreated = new Date(postCursor.getLong(
                postCursor.getColumnIndexOrThrow(ImpersonatorPost.DATE_CREATED)
        ));

        postCursor.close();

        return new ImpersonatorPost(id, impersonatorID, body, isFavorited, isTweeted, dateCreated);
    }

    @Override
    public void update(ImpersonatorPost object) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ImpersonatorPost.IS_FAVORITED, object.isFavorited());
        values.put(ImpersonatorPost.IS_TWEETED, object.isTweeted());

        // Which row to update, based on the ID
        String where = ImpersonatorPost.ID + " = ?";
        String[] whereArgs = { String.valueOf(object.getId()) };

        db.update(ImpersonatorPost.TABLE_NAME, values, where, whereArgs);
    }

    @Override
    public void delete(Long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String postQuery = Impersonator.ID + " LIKE ?";
        String[] postQueryArgs = { String.valueOf(id) };

        db.delete(ImpersonatorPost.TABLE_NAME, postQuery, postQueryArgs);
    }
}
