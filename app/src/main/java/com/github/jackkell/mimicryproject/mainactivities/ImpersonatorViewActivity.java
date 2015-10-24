package com.github.jackkell.mimicryproject.mainactivities;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.github.jackkell.mimicryproject.databaseobjects.DatabaseOpenHelper;
import com.github.jackkell.mimicryproject.databaseobjects.Impersonator;
import com.github.jackkell.mimicryproject.databaseobjects.ImpersonatorPost;
import com.github.jackkell.mimicryproject.listadpaters.ImpersonatorPostAdapter;
import com.github.jackkell.mimicryproject.R;
import com.github.jackkell.mimicryproject.databaseobjects.TwitterUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Users see this screen when they tap on an Impersonator
//In this screen, the user can see an Impersonator's posts
public class ImpersonatorViewActivity extends Activity {

    //The currently loaded Impersonator
    private Impersonator impersonator;
    //Used for testing purposes
    int count;

    @Override
    //Runs when this activity is opened
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impersonator_view);
        impersonator = Impersonator.findById(Impersonator.class, getIntent().getLongExtra("impersonatorID", -1));
        final ListView impersonatorPostView = (ListView) findViewById(R.id.impersonatorPostView);

        if (impersonator.getPosts() != null) {
            impersonatorPostView.setAdapter(new ImpersonatorPostAdapter(this, impersonator.getPosts()));
        }
        FloatingActionButton addPostButton = (FloatingActionButton) findViewById(R.id.fabAddPost);

        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddPostButtonClick();
            }
        });
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
        count++;
        ListView impersonatorPostView = (ListView) findViewById(R.id.impersonatorPostView);

        List<ImpersonatorPost> showPost = new ArrayList<>();
        for (int i = 0; i < count; i++){
            showPost.add(new ImpersonatorPost("New POST", false, false, new Date()));
        }
        impersonatorPostView.setAdapter(new ImpersonatorPostAdapter(this, showPost));
        impersonatorPostView.smoothScrollToPosition(impersonatorPostView.getChildCount());
    }
}
