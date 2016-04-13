package com.github.jackkell.mimicryproject.mainactivities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.jackkell.mimicryproject.Config;
import com.github.jackkell.mimicryproject.entity.Impersonator;
import com.github.jackkell.mimicryproject.listadpaters.ImpersonatorPostAdapter;
import com.github.jackkell.mimicryproject.R;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

//Users see this screen when they tap on an Impersonator
//In this screen, the user can see an Impersonator's posts
public class ImpersonatorViewActivity extends Activity {

    //The currently loaded Impersonator
    private Impersonator impersonator;
    private RecyclerView impersonatorPostListView;
    private ImpersonatorPostAdapter impersonatorPostAdapter;
    String LOG = "ImpersonatorViewActivity";


    @Override
    //Runs when this activity is opened
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impersonator_view);
        //impersonator = Impersonator.findById(Impersonator.class, getIntent().getLongExtra("impersonatorID", -1));
        impersonatorPostListView = (RecyclerView) findViewById(R.id.rvImpersonatorPost);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(Config.CONSUMER_KEY, Config.CONSUMER_KEY_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        impersonatorPostListView.setLayoutManager(linearLayoutManager);
        //impersonatorPostAdapter = new ImpersonatorPostAdapter(impersonator.getPosts());
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
        impersonator.addPost();
        //impersonatorPostAdapter.addPost(impersonator.getPosts().get(impersonator.getPosts().size() - 1));
        //impersonatorPostListView.smoothScrollToPosition(impersonator.getPosts().size()-1);
    }
}
