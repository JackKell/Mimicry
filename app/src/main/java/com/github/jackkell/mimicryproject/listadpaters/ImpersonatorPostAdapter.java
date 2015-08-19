package com.github.jackkell.mimicryproject.listadpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.jackkell.mimicryproject.R;
import com.github.jackkell.mimicryproject.databaseobjects.ImpersonatorPost;

import java.util.List;

public class ImpersonatorPostAdapter extends BaseAdapter {
    private Context context;
    private List<ImpersonatorPost> posts;
    private static LayoutInflater inflater = null;

    public ImpersonatorPostAdapter(Context context, List<ImpersonatorPost> posts) {
        this.context = context;
        this.posts = posts;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.util_post, null);
        }
        TextView postBody = (TextView) view.findViewById(R.id.postBody);
        postBody.setText(posts.get(position).getBody());

        return view;
    }
}
