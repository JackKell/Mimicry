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

// This screen is used to load all of the Impersonators from the database and display them on screen in a List View
// It displays each Impersonator and gives information about their posts
public class ImpersonatorSelectionActivity extends Activity {

    // List of impersonators in the database
    private List<Impersonator> impersonators;

    @Override
    // This runs when the activity is opened
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impersonator_selection);

        ListView impersonatorSelectionListView = (ListView) findViewById(R.id.impersonatorSelectionListView);
        impersonators = Impersonator.listAll(Impersonator.class);

        impersonatorSelectionListView.setAdapter(new ImpersonatorSelectableAdapter(this, impersonators));

        FloatingActionButton createImpersonatorButton = (FloatingActionButton) findViewById(R.id.fabCreateImpersonator);
        createImpersonatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent impersonatorCreation = new Intent(getApplicationContext(), ImpersonatorCreationActivity.class);
                startActivity(impersonatorCreation);
                finish();
            }
        });

        impersonatorSelectionListView.post(new Runnable() {
            @Override
            public void run() {
                setScrollViewChildrenOnClick();
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

    // Set the list's children's onClick handler
    private void setScrollViewChildrenOnClick(){
        ListView listView = (ListView) findViewById(R.id.impersonatorSelectionListView);
        Log.d("ImpersonatoreSelection", listView.getChildCount() + "");
        for (int i = 0; i < listView.getChildCount(); i++) {
            final int index = i;
            listView.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long ID = impersonators.get(index).getId();
                    Intent impersonatorCreation = new Intent(getBaseContext(), ImpersonatorViewActivity.class);
                    impersonatorCreation.putExtra("impersonatorID", ID);
                    impersonatorCreation.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getBaseContext().startActivity(impersonatorCreation);
                }
            });
        }
    }
}
