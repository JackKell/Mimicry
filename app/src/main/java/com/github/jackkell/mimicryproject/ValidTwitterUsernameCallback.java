package com.github.jackkell.mimicryproject;

import com.github.jackkell.mimicryproject.databaseobjects.Impersonator;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackkell on 1/24/2016.
 */
public class ValidTwitterUsernameCallback extends Callback<List<Tweet>> {

    private Impersonator impersonator;
    private String username;

    public ValidTwitterUsernameCallback(Impersonator impersonator, String username) {
        this.impersonator = impersonator;
        this.username = username;
    }

    @Override
    public void success(Result<List<Tweet>> result) {
        List<String> tweets = new ArrayList<String>();
        for (Tweet t : result.data){
            tweets.add(t.text);
        }
        impersonator.addTwitterUser(username, tweets);
    }

    @Override
    public void failure(TwitterException e) {
        android.util.Log.d("twittercommunity", "exception " + e);
    }
}
