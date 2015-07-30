package com.github.jackkell.mimicryproject.mainactivities;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.jackkell.mimicryproject.Config;
import com.github.jackkell.mimicryproject.R;
import com.github.jackkell.mimicryproject.tasks.CheckInternetConnectivityTask;

import java.util.concurrent.ExecutionException;

import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.Twitter;
import twitter4j.auth.RequestToken;

public class LoginActivity extends Activity {

    String TAG = "LoginActivity";

    private EditText usernameEditText;
    private EditText passwordEditText;

    private Button signInButton;

    private AccessToken userAccessToken;

    static final String TWITTER_CALLBACK_URL = "oauth://mimicryProjectOAuth";

    private void onSignInButtonClick() {/*
        TwitterLoginTask twitterLoginTask = new TwitterLoginTask();
        twitterLoginTask.execute();*/
        TestFunction();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEditText = (EditText) findViewById(R.id.usernameTextField);
        passwordEditText = (EditText) findViewById(R.id.passwordTextField);
        signInButton = (Button) findViewById(R.id.signInButton);

        usernameEditText.setText("FerniferdGully");
        passwordEditText.setText("247479707");

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignInButtonClick();
            }
        });

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

    public void TestFunction(){
        CheckInternetConnectivityTask checkInternetConnectivityTask = new CheckInternetConnectivityTask();
        Boolean b = null;
        try {
            b = checkInternetConnectivityTask.execute(this).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "TestFunction() b value: " + b);

        Uri uri = getIntent().getData();

        Log.d(TAG, "TestFunction() TWITTER_CALLBACK_URL " + TWITTER_CALLBACK_URL);
        Log.d(TAG, "TestFunction() getIntent.toString() " + getIntent().toString());
        Log.d(TAG, "TestFunction() getIntent.getData().toString " + getIntent().getDataString());
        Log.d(TAG, "TestFunction() uri.toString() " + uri.toString());

        if (uri != null && uri.toString().startsWith(TWITTER_CALLBACK_URL)){
            String verifier = uri.getQueryParameter("oauth_verifier");
            TwitterFactory twitterFactory = new TwitterFactory();
            Twitter twitter = twitterFactory.getInstance();
            twitter.setOAuthConsumer(Config.CONSUMER_KEY, Config.CONSUMER_KEY_SECRET);
            RequestToken requestToken = null;
            try {
                requestToken = twitter.getOAuthRequestToken(TWITTER_CALLBACK_URL);
            } catch (TwitterException e) {
                Log.d(TAG, "TestFunction(): " + e.getErrorMessage());
                e.printStackTrace();
            }

            try {
                AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
                Log.d(TAG, "TestFunction() access token: " + accessToken.getToken());
                Log.d(TAG, "TestFunction() access token: " + accessToken.getTokenSecret());
            } catch (TwitterException e) {
                Log.d(TAG, "TestFunction(): " + e.getErrorMessage());
                e.printStackTrace();
            }
        }
    }
}
