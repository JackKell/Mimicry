package com.github.jackkell.mimicryproject.testactivities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.github.jackkell.mimicryproject.DatabaseOpenHelper;
import com.github.jackkell.mimicryproject.R;


public class SendTweetTestActivity extends Activity {

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("MIMICRY", "Starting the twitters");
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


    /*
    TODO: Remove this when the document when this is no longer needed
    Unused functions for reference

    private boolean isAuthenticated() {
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(Config.CONSUMER_KEY, Config.CONSUMER_KEY_SECRET);
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
            twitter.setOAuthConsumer(Config.CONSUMER_KEY, Config.CONSUMER_KEY_SECRET);
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
     */
}
