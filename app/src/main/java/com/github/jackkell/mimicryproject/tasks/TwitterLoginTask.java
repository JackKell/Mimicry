package com.github.jackkell.mimicryproject.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.github.jackkell.mimicryproject.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;


public class TwitterLoginTask extends AsyncTask<Void, Void, Boolean> {

    String TAG = "TwitterLoginTask";

    @Override
    protected Boolean doInBackground(Void... params) {
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(Config.CONSUMER_KEY, Config.CONSUMER_KEY_SECRET);

        RequestToken requestToken = null;
        try {
            requestToken = twitter.getOAuthRequestToken();
        } catch (TwitterException e) {
            e.printStackTrace();
            Log.d(TAG, e.getMessage());
            return false;
        }

        Log.d(TAG, "Got request token.");
        Log.d(TAG, "Request token: " + requestToken.getToken());
        Log.d(TAG, "Request token secret: " + requestToken.getTokenSecret());

        AccessToken accessToken = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (accessToken == null){
            String pin = "";
            Log.d(TAG, "1st Try block");
            try {
                pin = br.readLine();
                Log.d(TAG, "Pin: " + pin);
            } catch (IOException e) {
                Log.d(TAG, "Failed 1st Try Block");
                e.printStackTrace();
            }

            Log.d(TAG, "2nd Try Block");
            try {
                if (pin != null) {
                    if (pin.length() > 0)
                        accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                    else
                        accessToken = twitter.getOAuthAccessToken(requestToken);
                } else {
                    accessToken = twitter.getOAuthAccessToken(requestToken);
                }
            } catch (TwitterException e) {
                Log.d(TAG, "Failed 2nd Try Block!");
                if (e.getStatusCode() == 401)
                    Log.d(TAG, "Unable to get the access token.");
                else
                    e.printStackTrace();
            } catch (IllegalStateException ie){
                Log.d(TAG, "Failed 2nd Try Block.");
                if (!twitter.getAuthorization().isEnabled())
                    Log.d(TAG, "OAuth consumer key/secret is not set.");
            }
        }/*
        Log.d(TAG, "accessToken: " + accessToken.getToken());
        Log.d(TAG, "accessToken secret: " + accessToken.getTokenSecret());
*/        return null;
    }
}
