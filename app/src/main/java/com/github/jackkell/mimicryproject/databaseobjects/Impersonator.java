package com.github.jackkell.mimicryproject.databaseobjects;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Impersonator is an object used to generate posts based on the vernacular of its Twitter users
public class Impersonator extends SugarRecord<Impersonator> {

    //Every Impersonator needs a name for identification reasons
    private String name;
    //This holds all of the Twitter Users associated with the Impersonator
    private List<TwitterUser> twitterUsers;
    //This holds all of the posts that the Impersonator has created
    private List<ImpersonatorPost> posts;
    //This stores the date that the Impersonator was created
    private Date dateCreated;

    public Impersonator(){}

    //Creates an Impersonator base on the attributes passed in.
    public Impersonator(String name, List<TwitterUser> twitterUsers, List<ImpersonatorPost> posts, Date dateCreated){
        this.name = name;
        this.twitterUsers = twitterUsers;
        this.posts = posts;
        this.dateCreated = dateCreated;
    }

    //Sets the name of the Impersonator.  Will occur when editing Impersonators
    public void setName(String name) {
        this.name = name;
    }

    //Gets the name of the Impersonator.  Used when displaying Impersonators
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

    public List<ImpersonatorPost> getPosts(){
        return this.posts;
    }
}
