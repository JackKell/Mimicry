package com.github.jackkell.mimicryproject;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

public class ValidTwitterUsernameCallback extends Callback<List<Tweet>> {

    public List<String> tweets;

    public ValidTwitterUsernameCallback() {
        this.tweets = new ArrayList<>();
    }

    @Override
    public void success(Result<List<Tweet>> result) {
        for (Tweet t : result.data){
            tweets.add(t.text);
        }
    }

    @Override
    public void failure(TwitterException e) {
        android.util.Log.d("twittercommunity", "exception " + e);
    }
}
