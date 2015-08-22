package com.github.jackkell.mimicryproject.tasks;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

//Checks if the user is connected to the Internet
public class CheckInternetConnectivityTask extends AsyncTask<Context, Void, Boolean>{
    @Override
    protected Boolean doInBackground(Context... contexts) {
        ConnectivityManager connectivityManager = (ConnectivityManager) contexts[0].getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null){
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null){
                for (int i = 0; i < info.length; i++){
                    if (info[i].getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
