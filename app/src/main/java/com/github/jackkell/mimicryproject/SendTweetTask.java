package com.github.jackkell.mimicryproject;

import android.os.AsyncTask;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;


public class SendTweetTask extends AsyncTask<String, Void, Boolean> {

    @Override
    protected Boolean doInBackground(String... strings) {
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(Config.COSUMER_KEY, Config.CONSUMER_KEY_SECRET);
        twitter.setOAuthAccessToken(new AccessToken(Config.ACCESS_TOKEN, Config.ACCESS_TOKEN_SECRET));

        try {
            twitter.updateStatus(strings[0]);
            return true;
        } catch (TwitterException e) {
            e.printStackTrace();
            return false;
        }
    }
}
