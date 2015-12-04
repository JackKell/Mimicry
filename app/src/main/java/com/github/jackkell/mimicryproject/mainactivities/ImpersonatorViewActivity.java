package com.github.jackkell.mimicryproject.mainactivities;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.github.jackkell.mimicryproject.MarkovChain;
import com.github.jackkell.mimicryproject.databaseobjects.DatabaseOpenHelper;
import com.github.jackkell.mimicryproject.databaseobjects.Impersonator;
import com.github.jackkell.mimicryproject.databaseobjects.ImpersonatorPost;
import com.github.jackkell.mimicryproject.databaseobjects.MimicryTweet;
import com.github.jackkell.mimicryproject.listadpaters.ImpersonatorPostAdapter;
import com.github.jackkell.mimicryproject.R;
import com.github.jackkell.mimicryproject.databaseobjects.TwitterUser;
import com.github.jackkell.mimicryproject.tasks.GetTimelineTask;
import com.github.jackkell.mimicryproject.tasks.HttpRequestTask;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.Timeline;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Users see this screen when they tap on an Impersonator
//In this screen, the user can see an Impersonator's posts
public class ImpersonatorViewActivity extends Activity {

    //The currently loaded Impersonator
    private Impersonator impersonator;
    private RecyclerView impersonatorPostListView;
    private ImpersonatorPostAdapter impersonatorPostAdapter;
    private MarkovChain markovChain;


    @Override
    //Runs when this activity is opened
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impersonator_view);
        impersonator = Impersonator.findById(Impersonator.class, getIntent().getLongExtra("impersonatorID", -1));

        impersonatorPostListView = (RecyclerView) findViewById(R.id.rvImpersonatorPost);
        markovChain = new MarkovChain();

        List<MimicryTweet> tweets = new ArrayList<>();
        List<TwitterUser> twitterUsers = impersonator.getTwitterUsers();

        for (TwitterUser twitterUser: twitterUsers){
            List<MimicryTweet> tempTweets = new ArrayList<>();
            tempTweets = MimicryTweet.findWithQuery(MimicryTweet.class, "Select MimicryTweet where TwitterUser = " + twitterUser.getId());

            for (MimicryTweet tweet: tempTweets) {
                tweets.add(tweet);
            }
        }

        for (MimicryTweet tweet: tweets){
            markovChain.addPhrase(tweet.getBody());
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        impersonatorPostListView.setLayoutManager(linearLayoutManager);
        impersonatorPostAdapter = new ImpersonatorPostAdapter(impersonator.getPosts());
        impersonatorPostListView.setAdapter(impersonatorPostAdapter);

        FloatingActionButton addPostButton = (FloatingActionButton) findViewById(R.id.fabAddPost);

        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddPostButtonClick();
            }
        });

        Toast.makeText(this, impersonator.getName(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_impersonator_view, menu);
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

    //The logic flow behind the onClick for the Floating Action Button
    private void onAddPostButtonClick(){
        impersonator.addPost(markovChain.generatePhrase());
        impersonatorPostAdapter.addPost(impersonator.getPosts().get(impersonator.getPosts().size() - 1));
        impersonatorPostListView.smoothScrollToPosition(impersonator.getPosts().size()-1);
    }
}
