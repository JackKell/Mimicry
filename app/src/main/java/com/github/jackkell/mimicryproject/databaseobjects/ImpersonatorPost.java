package com.github.jackkell.mimicryproject.databaseobjects;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

//A post created by the Impersonator constisting of a body and several other attributes
public class ImpersonatorPost implements DatabaseStorable {
    //The body of the post.  THe main text
    private String body;
    //Signifies whether the post is favorited or not
    private boolean isFavorited;
    //Signifies whether the post is Tweeted or not
    private boolean isTweeted;
    //Tells when the post was created
    private Date dateCreated;
    //Tells which Impersonator the post belongs to.
    private int impersonatorID;

    //Creates the ImpersonatorPost based on the passed attributes
    public ImpersonatorPost(int impersonatorID, String body, boolean isFavorited, boolean isTweeted, Date dateCreated) {
        this.impersonatorID = impersonatorID;
        this.body = body;
        this.isFavorited = isFavorited;
        this.isTweeted = isTweeted;
        this.dateCreated = dateCreated;
    }

    @Override
    public void addToDatabase(SQLiteDatabase db) {
        String postTable = DatabaseOpenHelper.POST;
        ContentValues cv = new ContentValues();

        cv.put(DatabaseOpenHelper.POST_IMPERSONATOR_ID, Integer.toString(impersonatorID));
        cv.put(DatabaseOpenHelper.POST_BODY, body);
        cv.put(DatabaseOpenHelper.POST_IS_FAVORITED, Integer.toString(isFavorited ? 1 : 0));
        cv.put(DatabaseOpenHelper.POST_IS_TWEETED, Integer.toString(isTweeted ? 1 : 0));
        String formattedDateCreated = DatabaseOpenHelper.DATE_TIME_FORMAT.format(dateCreated);
        cv.put(DatabaseOpenHelper.POST_DATE_CREATED, formattedDateCreated);
        db.insert(postTable, null, cv);
    }

    @Override
    public void removeFromDatabase(SQLiteDatabase db) {
        String impersonatorPostTable = DatabaseOpenHelper.POST;
        db.delete(impersonatorPostTable, DatabaseOpenHelper.POST_ID + " = " + getID(db), null);
    }

    @Override
    public String getID(SQLiteDatabase db) {
        String twitterUserTable = DatabaseOpenHelper.POST;
        String[] searchColumns = new String[1];
        searchColumns[0] = DatabaseOpenHelper.POST_ID;
        String formattedDateCreated = DatabaseOpenHelper.DATE_TIME_FORMAT.format(dateCreated);

        String[] selectionColumns = new String[2] ;
        selectionColumns[0] = DatabaseOpenHelper.POST_IMPERSONATOR_ID;
        selectionColumns[1] = DatabaseOpenHelper.POST_DATE_CREATED;

        Cursor cursor = db.query(twitterUserTable, searchColumns,
                selectionColumns[0] + " = " + this.impersonatorID + " AND " + selectionColumns[1] + " = '" + formattedDateCreated + "'",
                null, null, null, null, null);

        cursor.moveToFirst();

        String ID = cursor.getString(0);
        cursor.close();
        return ID;
    }

    //GETTERS AND SETTERS
    public String getBody() {
        return body;
    }

    public boolean isFavorited() {
        return isFavorited;
    }

    public boolean isTweeted() {
        return isTweeted;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void addPost(){

    }
}
