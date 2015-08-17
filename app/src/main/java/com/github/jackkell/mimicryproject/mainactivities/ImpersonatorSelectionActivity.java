package com.github.jackkell.mimicryproject.mainactivities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.github.jackkell.mimicryproject.DatabaseOpenHelper;
import com.github.jackkell.mimicryproject.Impersonator;
import com.github.jackkell.mimicryproject.ImpersonatorPost;
import com.github.jackkell.mimicryproject.ImpersonatorSelecableAdapter;
import com.github.jackkell.mimicryproject.R;
import com.github.jackkell.mimicryproject.TwitterUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImpersonatorSelectionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impersonator_selection);

        ListView impersonatorSelectionListView = (ListView) findViewById(R.id.impersonatorSelectionListView);

        List<Impersonator> impersonators = new ArrayList<>();
        impersonators.add(
                new Impersonator("Steve Gates",
                new ArrayList<TwitterUser>(),
                new ArrayList<ImpersonatorPost>(),
                new Date()));

        impersonators.add(
                new Impersonator("Jayden Bieber",
                new ArrayList<TwitterUser>(),
                new ArrayList<ImpersonatorPost>(),
                new Date()));

        impersonators.add(
                new Impersonator("Justin Olson",
                new ArrayList<TwitterUser>(),
                new ArrayList<ImpersonatorPost>(),
                new Date()));

        impersonators.add(
                new Impersonator("Kyle O'nell",
                new ArrayList<TwitterUser>(),
                new ArrayList<ImpersonatorPost>(),
                new Date()));

        impersonators.add(
                new Impersonator("Steve Gates",
                new ArrayList<TwitterUser>(),
                new ArrayList<ImpersonatorPost>(),
                new Date()));

        impersonators.add(
                new Impersonator("Jaydin Biber",
                new ArrayList<TwitterUser>(),
                new ArrayList<ImpersonatorPost>(),
                new Date()));

        impersonators.add(
                new Impersonator("Justin Olson",
                new ArrayList<TwitterUser>(),
                new ArrayList<ImpersonatorPost>(),
                new Date()));

        impersonators.add(
                new Impersonator("Kyle O'nell",
                new ArrayList<TwitterUser>(),
                new ArrayList<ImpersonatorPost>(),
                new Date()));

        impersonatorSelectionListView.setAdapter(new ImpersonatorSelecableAdapter(this, impersonators));

        FloatingActionButton createImpersonatorButton = (FloatingActionButton) findViewById(R.id.fabCreateImpersonator);
        createImpersonatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateImpersonatorButtonClick();
            }
        });
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


    private List<Impersonator> getStoredImpersonators(SQLiteDatabase db){
        List<Impersonator> impersonatorList = new ArrayList<>();
        List<String> impersonatorIDs = new ArrayList<>();
        String[] searchColumns = new String[1];
        searchColumns[0] = DatabaseOpenHelper.IMPERSONATOR_ID;

        Cursor impersonatorIDsCursor = db.query(DatabaseOpenHelper.IMPERSONATOR, searchColumns, null, null, null, null, null);
        impersonatorIDsCursor.moveToFirst();

        while (!impersonatorIDsCursor.isAfterLast()){
            impersonatorIDs.add(impersonatorIDsCursor.getString(0));
        }
        impersonatorIDsCursor.close();

        for (String impersonatorID : impersonatorIDs){
            String name;
            List<TwitterUser> twitterUserList = new ArrayList<>();
            List<ImpersonatorPost> impersonatorPostList = new ArrayList<>();
            Date dateCreated;

            // Get Impersonator name
            searchColumns[0] = DatabaseOpenHelper.IMPERSONATOR_NAME;
            Cursor cursor = db.query(DatabaseOpenHelper.IMPERSONATOR, searchColumns, DatabaseOpenHelper.IMPERSONATOR_ID + " = " + impersonatorID, null, null, null, null);
            cursor.moveToFirst();
            name = cursor.getString(0);

            // Get Twitter users
            searchColumns[0] = DatabaseOpenHelper.IMPERSONATOR_TWITTER_USER_TWITTER_USER_ID;
            cursor = db.query(DatabaseOpenHelper.IMPERSONATOR_TWITTER_USER, searchColumns, DatabaseOpenHelper.IMPERSONATOR_TWITTER_USER_IMPERSONATOR_ID + " = " + impersonatorID, null, null, null, null);
            cursor.moveToFirst();
            List<String> twitterUserIDs = new ArrayList<>();
            while (!cursor.isAfterLast()){
                twitterUserIDs.add(cursor.getString(0));
            }

            for (String twitterUserID : twitterUserIDs){
                searchColumns[0] = DatabaseOpenHelper.TWITTER_USER_USERNAME;
                cursor = db.query(DatabaseOpenHelper.TWITTER_USER, searchColumns, DatabaseOpenHelper.TWITTER_USER_ID + " = " + twitterUserID, null, null, null, null);
                cursor.moveToFirst();
                twitterUserList.add(new TwitterUser(cursor.getString(0)));
            }

            // Get Impersonator posts
            searchColumns[0] = DatabaseOpenHelper.POST_BODY;
            searchColumns[1] = DatabaseOpenHelper.POST_IS_TWEETED;
            searchColumns[2] = DatabaseOpenHelper.POST_IS_FAVORITED;
            searchColumns[3] = DatabaseOpenHelper.POST_DATE_CREATED;
            cursor = db.query(DatabaseOpenHelper.POST, searchColumns, DatabaseOpenHelper.IMPERSONATOR_ID + " = " + impersonatorID, null, null, null, null);
            cursor.moveToFirst();
            /*
            while (!cursor.isAfterLast()){
                ImpersonatorPost post = new ImpersonatorPost(
                        impersonatorID,
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                );
            }
            */
        }

        return impersonatorList;
    }
}
