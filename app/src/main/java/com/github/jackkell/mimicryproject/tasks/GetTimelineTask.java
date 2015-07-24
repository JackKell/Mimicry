package com.github.jackkell.mimicryproject.tasks;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import android.database.sqlite.SQLiteDatabase;

import com.github.jackkell.mimicryproject.Config;
import com.github.jackkell.mimicryproject.DatabaseOpenHelper;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class GetTimelineTask extends AsyncTask<String, Void, List<Status>> {

    @Override
    protected List<twitter4j.Status> doInBackground(String... strings) {
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(Config.CONSUMER_KEY, Config.CONSUMER_KEY_SECRET);
        twitter.setOAuthAccessToken(new AccessToken(Config.ACCESS_TOKEN, Config.ACCESS_TOKEN_SECRET));

        List<twitter4j.Status> tweets = new ArrayList<>();
        String userName = strings[0];

        /*
        Query query = new Query("#peace");
        int numberOfStatues = 512;
        long lastID = Long.MAX_VALUE;
        while (tweets.size() < numberOfStatues) {
            if (numberOfStatues - tweets.size() > 100) {
                query.setCount(100);
            } else {
                query.setCount(numberOfStatues - tweets.size());
            }
            try {
                QueryResult result = twitter.search(query);
                tweets.addAll(result.getTweets());
                for (twitter4j.Status tweet: tweets) {
                    if(tweet.getId() < lastID) {
                        lastID = tweet.getId();
                    }
                }
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            query.setMaxId(lastID - 1);
        }
        return tweets;

        http://stackoverflow.com/questions/7675555/where-can-i-find-detailed-info-regarding-twitter4j-query-strings
        */

        int size = tweets.size();
        try {
            for (int currentPage = 1; tweets.size() == size; currentPage++) {
                size = tweets.size();
                Paging page = new Paging(currentPage, 150);
                currentPage++;
                tweets.addAll(twitter.getUserTimeline(userName, page));
            }
            return tweets;
        } catch (TwitterException e) {
            e.printStackTrace();
            return tweets;
        }
    }
}


