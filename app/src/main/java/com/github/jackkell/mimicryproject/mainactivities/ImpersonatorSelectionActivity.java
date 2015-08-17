package com.github.jackkell.mimicryproject.mainactivities;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

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
}
