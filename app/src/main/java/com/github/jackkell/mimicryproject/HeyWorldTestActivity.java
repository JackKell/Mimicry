package com.github.jackkell.mimicryproject;

import android.app.Activity;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.jackkell.mimicryproject.tasks.SendTweetTask;

import java.util.ArrayList;
import java.util.List;

import twitter4j.*;


public class HeyWorldTestActivity extends Activity {

    int clickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hey_world);

        new SendTweetTask().execute("Hello, world.");

        Button clickmeButton = (Button)findViewById(R.id.clickmeButton);
        clickmeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickMeButtonClick();
            }
        });
    }

    private void OnClickMeButtonClick() {
        TextView textView = (TextView)findViewById(R.id.heyworldText);
        ++clickCount;
        textView.setText("Count: " + clickCount);
        tweetAway();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hey_world, menu);
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

    public void tweetAway() {
        EditText tweetText = (EditText)findViewById(R.id.tweetText);
        String toBeTweeted = tweetText.getText().toString();
        new SendTweetTask().execute(toBeTweeted);
    }
}
