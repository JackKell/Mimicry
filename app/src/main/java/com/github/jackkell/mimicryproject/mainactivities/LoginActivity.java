package com.github.jackkell.mimicryproject.mainactivities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.github.jackkell.mimicryproject.Config;
import com.github.jackkell.mimicryproject.R;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

import android.content.Intent;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

//This is the first screen a user will see
//It allows the user to log in to Twitter
public class LoginActivity extends Activity {

    //The TwitterLoginButton displayed on screen.  Clicking that will lead the user to a Login to Twitter screen
    private TwitterLoginButton loginButton;

    //TAGs are used for debugging purposes
    String TAG = "LoginActivity";

    @Override
    //This runs everytime the activity is opened
    protected void onCreate(Bundle savedInstanceState) {
        //getBaseContext().deleteDatabase("mimicry.db");
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(Config.CONSUMER_KEY, Config.CONSUMER_KEY_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_login);

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.d(TAG, "Login Success!");
                Intent impersonatorSelection = new Intent(getApplicationContext(), ImpersonatorSelectionActivity.class);
                startActivity(impersonatorSelection);
                finish();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d(TAG, "Login Fail!");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
