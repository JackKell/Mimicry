package com.github.jackkell.mimicryproject.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Jackkell on 12/4/2015.
 */
public class GetTimelineTask extends AsyncTask<String, Void, String> {
    private final String address="https://api.twitter.com/1.1/statuses/user_timeline.json";
    String LOG="GetTimelineTask";

    @Override
    protected String doInBackground(String... params) {
        try {
            HttpRequestTask httpRequest = new HttpRequestTask(HttpRequestTask.GET, address);
            httpRequest.withString("screen_name=FerniferdGully&count=25");
            String response = httpRequest.send();
            Log.d(LOG,response);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }
}
