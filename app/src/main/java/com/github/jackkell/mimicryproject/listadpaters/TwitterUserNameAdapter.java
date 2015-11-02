package com.github.jackkell.mimicryproject.listadpaters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.github.jackkell.mimicryproject.R;

import java.util.ArrayList;
import java.util.List;

public class TwitterUserNameAdapter extends RecyclerView.Adapter<TwitterUserNameAdapter.TwitterUserNameViewHolder>{

    private List<String> twitterUserNames;

    public static class TwitterUserNameViewHolder extends RecyclerView.ViewHolder {
        EditText etTwitterUserName;

        public TwitterUserNameViewHolder(View itemView) {
            super(itemView);
            this.etTwitterUserName = (EditText) itemView.findViewById(R.id.etTwitterUserName);
        }
    }

    public TwitterUserNameAdapter(List<String> twitterUsernames){
        this.twitterUserNames = twitterUsernames;
    }

    @Override
    public TwitterUserNameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.twitter_user_input_field, parent, false);
        TwitterUserNameViewHolder twitterUserNameViewHolder = new TwitterUserNameViewHolder(view);
        return twitterUserNameViewHolder;
    }

    @Override
    public void onBindViewHolder(TwitterUserNameViewHolder twitterUserNameViewHolder, int position) {
        twitterUserNameViewHolder.etTwitterUserName.setText(twitterUserNames.get(position));
    }

    @Override
    public int getItemCount() {
        return twitterUserNames.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void addTwitterUserName(String twitterUserName){
        twitterUserNames.add(twitterUserName);
        this.notifyDataSetChanged();
    }

    public void updatePosition(String newTwitterUserName, int position){
        twitterUserNames.set(position, newTwitterUserName);
        this.notifyDataSetChanged();
    }

    public void update(List<String> twitterUserNames){
        this.twitterUserNames = twitterUserNames;
        this.notifyDataSetChanged();
    }
}
