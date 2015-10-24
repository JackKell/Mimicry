package com.github.jackkell.mimicryproject.databaseobjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.orm.SugarRecord;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

//A Twitter account associated with individual Impersonators
public class TwitterUser extends SugarRecord<TwitterUser> {
    //The Twitter username
    private String username;
    //The list of tweets made by the Twitter user
    // TODO: fix this list of tweets so that tweets is its own class and has a id to the twitter user
    public List<String> tweets;
    // The id of the impersonator that this twitter user belongs to
    private Impersonator impersonator;

    public TwitterUser(){}

    //Creates a Twitter user based on passed attributes
    public TwitterUser(String username, List<String> tweets) {
        this.username = username;
        this.tweets = tweets;
    }

    //GETTER
    public String getUsername() {
        return username;
    }

    public void setImpersonator(Impersonator impersonator){
        this.impersonator = impersonator;
    }
}
