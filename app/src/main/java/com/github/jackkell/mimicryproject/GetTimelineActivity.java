package com.github.jackkell.mimicryproject;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.jackkell.mimicryproject.tasks.GetTimelineTask;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import twitter4j.Status;


public class GetTimelineActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_timeline);

        Button pullTimelineButton = (Button)findViewById(R.id.pullTimelineButton);

        pullTimelineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onPullTimelineButtonClick();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_timeline, menu);
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

    private void onPullTimelineButtonClick() throws ExecutionException, InterruptedException {
        List<Status> statuses  = new GetTimelineTask().execute("FerniferdGully").get();
        TextView textViewStatusCount = (TextView)findViewById(R.id.textViewStatusCount);
        TextView textViewFirstTweet = (TextView)findViewById(R.id.textViewFirstTweet);
        int statusCount = statuses.size();
        textViewStatusCount.setText(statusCount + " statuses pulled");
        textViewFirstTweet.setText(statuses.get(1).getText());
    }
}
