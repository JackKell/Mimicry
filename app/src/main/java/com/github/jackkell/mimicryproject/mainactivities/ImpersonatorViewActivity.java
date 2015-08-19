package com.github.jackkell.mimicryproject.mainactivities;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.github.jackkell.mimicryproject.DatabaseOpenHelper;
import com.github.jackkell.mimicryproject.Impersonator;
import com.github.jackkell.mimicryproject.ImpersonatorPost;
import com.github.jackkell.mimicryproject.ImpersonatorPostAdapter;
import com.github.jackkell.mimicryproject.R;
import com.github.jackkell.mimicryproject.TwitterUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImpersonatorViewActivity extends Activity {

    Impersonator impersonator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impersonator_view);
        impersonator = getImpersonator();

        ListView impersonatorPostView = (ListView) findViewById(R.id.impersonatorPostView);
        List<ImpersonatorPost> impersonatorPosts = impersonator.getPosts();

        impersonatorPostView.setAdapter(new ImpersonatorPostAdapter(this, impersonatorPosts));
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

    private Impersonator getImpersonator(){
        DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(this);
        SQLiteDatabase db = databaseOpenHelper.getDatabase(this);
        String name;
        List<TwitterUser> twitterUserList = new ArrayList<>();
        List<ImpersonatorPost> impersonatorPostList = new ArrayList<>();
        String impersonatorID = getIntent().getStringExtra("impersonatorID");

        // Get Impersonator name
        String[] impersonatorsearchColumns = new String[1];
        impersonatorsearchColumns[0] = DatabaseOpenHelper.IMPERSONATOR_NAME;
        Cursor nameCursor = db.query(DatabaseOpenHelper.IMPERSONATOR, impersonatorsearchColumns, DatabaseOpenHelper.IMPERSONATOR_ID + " = '" + impersonatorID + "'", null, null, null, null);
        nameCursor.moveToFirst();
        name = nameCursor.getString(0);
        name = name.substring(1, name.length() - 1);
        nameCursor.close();

        // Get Twitter users
        String[] impersonatorTwitterUserTwitterUserIDSearchColumns = new String[1];
        impersonatorTwitterUserTwitterUserIDSearchColumns[0] = DatabaseOpenHelper.IMPERSONATOR_TWITTER_USER_TWITTER_USER_ID;
        Cursor twitterUserIDscursor = db.query(DatabaseOpenHelper.IMPERSONATOR_TWITTER_USER, impersonatorTwitterUserTwitterUserIDSearchColumns,
                DatabaseOpenHelper.IMPERSONATOR_TWITTER_USER_IMPERSONATOR_ID + " = " + impersonatorID, null, null, null, null);
        twitterUserIDscursor.moveToFirst();
        List<String> twitterUserIDs = new ArrayList<>();
        while (!twitterUserIDscursor.isAfterLast()){
            twitterUserIDs.add(twitterUserIDscursor.getString(0));
        }
        twitterUserIDscursor.close();

        String[] twitterUserNameSearchColumns = new String[1];
        for (String twitterUserID : twitterUserIDs){
            twitterUserNameSearchColumns[0] = DatabaseOpenHelper.TWITTER_USER_USERNAME;
            Cursor twitterUserListCursor = db.query(DatabaseOpenHelper.TWITTER_USER, twitterUserNameSearchColumns, DatabaseOpenHelper.TWITTER_USER_ID + " = " + twitterUserID, null, null, null, null);
            twitterUserListCursor.moveToFirst();
            twitterUserList.add(new TwitterUser(twitterUserListCursor.getString(0)));
            twitterUserListCursor.close();
        }

        // Get Impersonator posts
        String[] impersonatorPostSearchColumns = new String[3];
        impersonatorPostSearchColumns[0] = DatabaseOpenHelper.POST_BODY;
        impersonatorPostSearchColumns[1] = DatabaseOpenHelper.POST_IS_TWEETED;
        impersonatorPostSearchColumns[2] = DatabaseOpenHelper.POST_IS_FAVORITED;
        //impersonatorPostSearchColumns[3] = DatabaseOpenHelper.POST_DATE_CREATED;
        Cursor impersonatorPostCursor = db.query(DatabaseOpenHelper.POST, impersonatorPostSearchColumns, DatabaseOpenHelper.POST_IMPERSONATOR_ID + " = " + impersonatorID, null, null, null, null);
        impersonatorPostCursor.moveToFirst();

        while (!impersonatorPostCursor.isAfterLast()){
            ImpersonatorPost post = new ImpersonatorPost(
                    Integer.parseInt(impersonatorID),
                    impersonatorPostCursor.getString(0),
                    impersonatorPostCursor.getString(1) == "True" ? true : false,
                    impersonatorPostCursor.getString(2)== "True" ? true : false,
                    new Date()
            );

            impersonatorPostList.add(post);
        }
        impersonatorPostCursor.close();
        db.close();
        databaseOpenHelper.close();

        return new Impersonator(name,
                twitterUserList,
                impersonatorPostList,
                new Date());
    }

    private void onAddPostButtonClick(){

    }
}
