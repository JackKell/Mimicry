package com.github.jackkell.mimicryproject.mainactivities;

import android.app.Activity;
import android.content.Intent;
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
import com.github.jackkell.mimicryproject.listadpaters.ImpersonatorSelectableAdapter;
import com.github.jackkell.mimicryproject.R;
import com.github.jackkell.mimicryproject.databaseobjects.TwitterUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImpersonatorSelectionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impersonator_selection);

        ListView impersonatorSelectionListView = (ListView) findViewById(R.id.impersonatorSelectionListView);
        final List<Impersonator> impersonators = getStoredImpersonators();

        impersonatorSelectionListView.setAdapter(new ImpersonatorSelectableAdapter(this, impersonators));

        FloatingActionButton createImpersonatorButton = (FloatingActionButton) findViewById(R.id.fabCreateImpersonator);
        createImpersonatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateImpersonatorButtonClick();
            }
        });

        impersonatorSelectionListView.post(new Runnable() {
            @Override
            public void run() {
                setScrollViewChildrenOnClick();
            }
        });

        //onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_impersonator_selection, menu);
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

    private void onCreateImpersonatorButtonClick() {
        Intent impersonatorCreation = new Intent(getApplicationContext(), ImpersonatorCreationActivity.class);
        startActivity(impersonatorCreation);
        finish();
    }

    private void setScrollViewChildrenOnClick(){
        final List<Impersonator> impersonators = getStoredImpersonators();
        ListView lv = (ListView) findViewById(R.id.impersonatorSelectionListView);
        Log.d("ImpersonatoreSelection", lv.getChildCount() + "");
        for (int i = 0; i < lv.getChildCount(); i++) {
            Log.d("ImpersonatoreSelection", "TESTEST");
            final int index = i;

            lv.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(getBaseContext());
                    SQLiteDatabase db = databaseOpenHelper.getDatabase(getBaseContext());
                    String ID = impersonators.get(index).getID(db);

                    Intent impersonatorCreation = new Intent(getBaseContext(), ImpersonatorViewActivity.class);
                    impersonatorCreation.putExtra("impersonatorID", ID);
                    impersonatorCreation.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getBaseContext().startActivity(impersonatorCreation);
                }
            });
        }
    }

    private List<Impersonator> getStoredImpersonators() {
        DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(this);
        SQLiteDatabase db = databaseOpenHelper.getDatabase(this);
        List<Impersonator> impersonatorList = new ArrayList<>();
        List<String> impersonatorIDs = new ArrayList<>();
        String[] impersonatorIDssearchColumns = new String[1];
        impersonatorIDssearchColumns[0] = DatabaseOpenHelper.IMPERSONATOR_ID;

        Cursor impersonatorIDsCursor = db.query(DatabaseOpenHelper.IMPERSONATOR, impersonatorIDssearchColumns, null, null, null, null, null);
        impersonatorIDsCursor.moveToFirst();

        while (!impersonatorIDsCursor.isAfterLast()){
            impersonatorIDs.add(impersonatorIDsCursor.getString(0));
            impersonatorIDsCursor.moveToNext();
        }
        impersonatorIDsCursor.close();

        for (String impersonatorID : impersonatorIDs){
            String name;
            List<TwitterUser> twitterUserList = new ArrayList<>();
            List<ImpersonatorPost> impersonatorPostList = new ArrayList<>();
            Date dateCreated;

            // Get Impersonator name
            String[] impersonatorsearchColumns = new String[1];
            impersonatorsearchColumns[0] = DatabaseOpenHelper.IMPERSONATOR_NAME;
            Cursor nameCursor = db.query(DatabaseOpenHelper.IMPERSONATOR, impersonatorsearchColumns, DatabaseOpenHelper.IMPERSONATOR_ID + " = " + impersonatorID, null, null, null, null);
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
            twitterUserNameSearchColumns[0] = DatabaseOpenHelper.TWEET_BODY;
            for (String twitterUserID : twitterUserIDs){
                twitterUserNameSearchColumns[0] = DatabaseOpenHelper.TWITTER_USER_USERNAME;
                Cursor twitterUserListCursor = db.query(DatabaseOpenHelper.TWITTER_USER, twitterUserNameSearchColumns, DatabaseOpenHelper.TWITTER_USER_ID + " = " + twitterUserID, null, null, null, null);
                twitterUserListCursor.moveToFirst();

                List<String> twitterUserTweets = new ArrayList<>();
                Cursor tweetsCursor = db.query(DatabaseOpenHelper.TWEET, twitterUserNameSearchColumns, DatabaseOpenHelper.TWEET_TWITTER_USER_ID + " = " + twitterUserID, null, null, null, null);
                while ((!tweetsCursor.isAfterLast())) {
                    twitterUserTweets.add(tweetsCursor.getString(0));
                    tweetsCursor.moveToNext();
                }

                twitterUserList.add(new TwitterUser(twitterUserListCursor.getString(0), twitterUserTweets));
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

            Impersonator impersonator = new Impersonator(name,
                    twitterUserList,
                    impersonatorPostList,
                    new Date());

            impersonatorList.add(impersonator);

        }
        databaseOpenHelper.close();
        db.close();

        return impersonatorList;
    }
}
