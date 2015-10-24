package com.github.jackkell.mimicryproject.mainactivities;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.jackkell.mimicryproject.Config;
import com.github.jackkell.mimicryproject.databaseobjects.DatabaseOpenHelper;
import com.github.jackkell.mimicryproject.databaseobjects.Impersonator;
import com.github.jackkell.mimicryproject.databaseobjects.ImpersonatorPost;
import com.github.jackkell.mimicryproject.R;
import com.github.jackkell.mimicryproject.databaseobjects.TwitterUser;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.fabric.sdk.android.Fabric;

//The logic that helps create an Impersonator Creation Activity
public class ImpersonatorCreationActivity extends Activity {

    //A tag used for debugging purposes
    String TAG = "ImpersonatorCreationActivity";

    //The EditText field that allows the user to type in the Impersonators name.
    EditText etImpersonatorName;
    //The EditText field that allows the user to type in the Impersonator's first associated Twitter username
    EditText et1;
    //The EditText field that allows the user to type in the Impersonator's second associated Twitter username
    EditText et2;

    @Override
    //This runs when the activity is opened
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impersonator_creation);
        etImpersonatorName = (EditText) findViewById(R.id.impersonatorNameEditText);
        et1 = (EditText) findViewById(R.id.EditText1);
        et2  = (EditText) findViewById(R.id.EditText2);
        Button createImpersonatorButton = (Button) findViewById(R.id.createButton);

        createImpersonatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateImpersonatorButtonClick();
            }
        });
    }

    @Override
    //An automatically generated function
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_impersonator_creation, menu);
        return true;
    }

    @Override
    //An automatically generated function
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

    //This is what occurs when the Create Impersonator button is tapped
    private void onCreateImpersonatorButtonClick(){
        Log.d(TAG, "onCreateImpersonatorButtonClick() Opening");
        if (etImpersonatorName.getText().length() != 0 && et1.getText().length() != 0 && et2.getText().length() != 0){
            TwitterAuthConfig authConfig = new TwitterAuthConfig(Config.CONSUMER_KEY, Config.CONSUMER_KEY_SECRET);
            Fabric.with(this, new Twitter(authConfig));
            TwitterSession session = Twitter.getSessionManager().getActiveSession();
            final List<TwitterUser> twitterUsers = new ArrayList<>();
            List<String> usernames = new ArrayList<>();
            usernames.add(et1.getText().toString());
            usernames.add(et2.getText().toString());

            for (final String username : usernames) {
                TwitterCore.getInstance().getApiClient(session).getStatusesService()
                        .userTimeline(null,
                                username,
                                10, //the number of tweets we want to fetch,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                        new Callback<List<Tweet>>() {
                            @Override
                            public void success(Result<List<Tweet>> result) {
                                List<String> tweets = new ArrayList<String>();
                                for (Tweet t : result.data) {
                                    tweets.add(t.text);
                                }
                                TwitterUser twitterUser = new TwitterUser(username, tweets);
                                twitterUsers.add(twitterUser);
                            }

                            @Override
                            public void failure(TwitterException exception) {
                                android.util.Log.d("twittercommunity", "exception " + exception);
                            }
                        });
            }
            Impersonator impersonator = new Impersonator(etImpersonatorName.getText().toString(), twitterUsers, new ArrayList<ImpersonatorPost>(), new Date());
            impersonator.save();

            Intent impersonatorSelection = new Intent(getApplicationContext(), ImpersonatorSelectionActivity.class);
            startActivity(impersonatorSelection);
            finish();
        } else {
            Toast.makeText(this, "Please fill in required fields.", Toast.LENGTH_LONG).show();
        }
    }
}
