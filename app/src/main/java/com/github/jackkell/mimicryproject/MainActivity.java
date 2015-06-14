package com.github.jackkell.mimicryproject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;


public class MainActivity extends Activity {

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("MIMICRY", "Starting the twitters");

        new SendTweetTask().execute("Justin is a nerd :D. This Twitter is mine now. Bwa ha ha.");
    }

    private boolean isAuthenticated() {
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(Config.COSUMER_KEY, Config.CONSUMER_KEY_SECRET);
        twitter.setOAuthAccessToken(new AccessToken(Config.ACCESS_TOKEN, Config.ACCESS_TOKEN_SECRET));

        try {
            twitter.getAccountSettings();
            return true;
        } catch (TwitterException e) {
            return false;
        }
    }

    private void printTimeline() {
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            twitter.setOAuthConsumer(Config.COSUMER_KEY, Config.CONSUMER_KEY_SECRET);
            twitter.setOAuthAccessToken(new AccessToken(Config.ACCESS_TOKEN, Config.ACCESS_TOKEN_SECRET));
            List<Status> statuses = twitter.getHomeTimeline();
            Log.i("MIMICRY", "Show home timeline.");
            for(Status status : statuses) {
                Log.i("MIMICRY", status.getUser().getName() + ":" + status.getText());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
