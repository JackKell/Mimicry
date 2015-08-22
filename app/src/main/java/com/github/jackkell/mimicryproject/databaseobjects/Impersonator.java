package com.github.jackkell.mimicryproject.databaseobjects;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Impersonator is an object used to generate posts based on the vernacular of its Twitter users
public class Impersonator implements DatabaseStorable {

    //Every Impersonator needs a name for identification reasons
    private String name;
    //This holds all of the Twitter Users associated with the Impersonator
    private List<TwitterUser> twitterUsers;
    //This holds all of the posts that the Impersonator has created
    private List<ImpersonatorPost> posts;
    //This stores the date that the Impersonator was created
    private Date dateCreated;

    //Creates an Impersonator base on the attributes passed in.
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
        addToImpersonatorTable(db);
        addToTwitterUsersTable(db);
        addToImpersonatorTwitterUserTable(db);
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


    //Sets the name of the Impersonator.  Will occur when editing Impersonators
    public void setName(String name) {
        this.name = name;
    }

    //Gets the name of the Impersonaotr.  Used when displaying Impersonators
    public String getName(){
        return this.name;
    }

    //Helps display the amount of favorited posts for the Impersonator
    public int getIsFavoritedPostCount() {
        int favoritedCount = 0;
        for(int index = 0; index < posts.size(); index++) {
            favoritedCount += posts.get(index).isFavorited() ? 1 : 0;
        }
        return favoritedCount;
    }

    //Helps display the amount of Tweeted posts for the Impersonator
    public int getIsTweetedPostCount() {
        int tweetedCount = 0;
        for(int index = 0; index < posts.size(); index++) {
            tweetedCount += posts.get(index).isTweeted() ? 1 : 0;
        }
        return tweetedCount;
    }

    //Helps display the amount of posts created by the Impersonator
    public int getPostCount(){
        return posts.size();
    }

    //Helps display the date the Impersonator was created
    public String getDateCreated(){
        return DatabaseOpenHelper.DAY_FORMAT.format(dateCreated);
    }

    //Grabs the Twitter users associated with the Impersonator
    public List<TwitterUser> getTwitterUsers() {
        return this.twitterUsers;
    }

    //Helps add the Impersonator to the Impersonator Table
    private void addToImpersonatorTable(SQLiteDatabase db) throws NullPointerException{
        String tableName = DatabaseOpenHelper.IMPERSONATOR;
        ContentValues newImpersonator = new ContentValues();
        newImpersonator.put(DatabaseOpenHelper.IMPERSONATOR_NAME, "'" + this.name + "'");
        Log.d("Impersonator", this.name);
        newImpersonator.put(DatabaseOpenHelper.IMPERSONATOR_DATE_CREATED, "'" + this.dateCreated.toString() + "'");
        db.insert(tableName, null, newImpersonator);
    }

    //Helps add the Twitter Users to the Twitter Users Table
    private void addToTwitterUsersTable(SQLiteDatabase db){
        for (TwitterUser twitterUser : twitterUsers){
            twitterUser.addToDatabase(db);
        }
    }

    //Helps build the Impersonator Twitter User bridge table
    private void addToImpersonatorTwitterUserTable(SQLiteDatabase db){
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
