package com.github.jackkell.mimicryproject.databaseobjects;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Impersonator implements DatabaseStorable {

    private String name;
    private List<TwitterUser> twitterUsers;
    private List<ImpersonatorPost> posts;
    private Date dateCreated;

    public Impersonator(String name, List<TwitterUser> twitterUsers, List<ImpersonatorPost> posts, Date dateCreated){
        this.name = name;
        this.twitterUsers = twitterUsers;
        this.posts = posts;
        this.dateCreated = dateCreated;
    }

    /*public Impersonator(String name, List<TwitterUser> twitterUsers, Date dateCreated){
        this(name, twitterUsers, new ArrayList<ImpersonatorPost>(0), dateCreated);
    }*/

    @Override
    public void addToDatabase(SQLiteDatabase db) {
        addImpersonatorToDatabase(db);
        addTwitterUsersToDatabase(db);
        addValuesToImpersonatorTwitterUserTable(db);
    }

    @Override
    public void removeFromDatabase(SQLiteDatabase db) {
        String impersonatorTable = DatabaseOpenHelper.IMPERSONATOR;
        String postTable = DatabaseOpenHelper.POST;
        String impersonatorTwitterUserTable = DatabaseOpenHelper.IMPERSONATOR_TWITTER_USER;
        String twitterUserTable = DatabaseOpenHelper.TWITTER_USER;

        String impersonatorID = getID(db);

        db.delete(postTable, DatabaseOpenHelper.POST_IMPERSONATOR_ID + " = " + impersonatorID, null);
        db.delete(impersonatorTwitterUserTable,
                DatabaseOpenHelper.IMPERSONATOR_TWITTER_USER_IMPERSONATOR_ID + " = " + impersonatorID, null);
    }

    @Override
    public String getID(SQLiteDatabase db) {
        Cursor c = db.rawQuery("SELECT ID, NAME FROM IMPERSONATOR", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            Log.d("TAG", c.getString(1) + " " + this.getName());
            String testString = c.getString(1);
            testString = testString.substring(1, testString.length() - 1);
            if (testString.equals(this.name))
                return c.getString(0);
            c.moveToNext();
        }
        return "0";
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public int getIsFavoritedPostCount() {
        int favoritedCount = 0;
        for(int index = 0; index < posts.size(); index++) {
            favoritedCount += posts.get(index).isFavorited() ? 1 : 0;
        }
        return favoritedCount;
    }

    public int getIsTweetedPostCount() {
        int tweetedCount = 0;
        for(int index = 0; index < posts.size(); index++) {
            tweetedCount += posts.get(index).isTweeted() ? 1 : 0;
        }
        return tweetedCount;
    }

    public int getPostCount(){
        return posts.size();
    }

    public String getDateCreated(){
        return DatabaseOpenHelper.DAY_FORMAT.format(dateCreated);
    }

    public List<TwitterUser> getTwitterUsers() {
        return this.twitterUsers;
    }

    private void addImpersonatorToDatabase(SQLiteDatabase db) throws NullPointerException{
        String tableName = DatabaseOpenHelper.IMPERSONATOR;
        ContentValues newImpersonator = new ContentValues();
        newImpersonator.put(DatabaseOpenHelper.IMPERSONATOR_NAME, "'" + this.name + "'");
        Log.d("Impersonator", this.name);
        newImpersonator.put(DatabaseOpenHelper.IMPERSONATOR_DATE_CREATED, "'" + this.dateCreated.toString() + "'");
        db.insert(tableName, null, newImpersonator);
    }

    private void addTwitterUsersToDatabase(SQLiteDatabase db){
        for (TwitterUser twitterUser : twitterUsers){
            twitterUser.addToDatabase(db);
        }
    }

    private void addValuesToImpersonatorTwitterUserTable(SQLiteDatabase db){
        List<String> twitterUserIDs = new ArrayList<>();
        String impersonatorID = getID(db);
        String twitterUserTable = DatabaseOpenHelper.IMPERSONATOR_TWITTER_USER;

        Cursor cursor = db.rawQuery("SELECT * FROM " + twitterUserTable, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            String currentTwitterUserName = cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.TWITTER_USER_USERNAME));
            for (TwitterUser twitterUser : this.twitterUsers){
                if (twitterUser.getUsername().equals(currentTwitterUserName)) {
                    String id = cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.TWITTER_USER_ID));
                    twitterUserIDs.add(id);
                }
                cursor.moveToNext();
            }
        }

        String impersonatorTwitterUserTable = DatabaseOpenHelper.IMPERSONATOR_TWITTER_USER;
        for (String twitterUserID : twitterUserIDs) {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseOpenHelper.IMPERSONATOR_TWITTER_USER_IMPERSONATOR_ID, impersonatorID);
            cv.put(DatabaseOpenHelper.IMPERSONATOR_TWITTER_USER_TWITTER_USER_ID, twitterUserID);
            db.insert(impersonatorTwitterUserTable, null, cv);
        }
    }

    public List<ImpersonatorPost> getPosts(){
        return this.posts;
    }
}
