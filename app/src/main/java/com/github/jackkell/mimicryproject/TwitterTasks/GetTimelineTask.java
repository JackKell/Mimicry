package com.github.jackkell.mimicryproject.TwitterTasks;

import android.os.AsyncTask;

import com.github.jackkell.mimicryproject.Config.Config;

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

        List<twitter4j.Status> statuses = new ArrayList();
        String userName = strings[0];
        int size = statuses.size();
        try {
            for (int currentPage = 1; statuses.size() == size; currentPage++) {
                size = statuses.size();
                Paging page = new Paging(currentPage, 100);
                currentPage++;
                statuses.addAll(twitter.getUserTimeline(userName, page));
            }
            return statuses;
        } catch (TwitterException e) {
            e.printStackTrace();
            return null;
        }
    }
}
