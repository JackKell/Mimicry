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

//Used to auto-populate the custom Impersontor Post List View
public class ImpersonatorPostAdapter extends BaseAdapter {

    //The individual posts used to populate the listview
    private List<ImpersonatorPost> posts;
    //pumps up a list with content
    private static LayoutInflater inflater = null;

    //Creates an Adapter based on the passed attributes
    public ImpersonatorPostAdapter(Context context, List<ImpersonatorPost> posts) {
        this.posts = posts;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //GETTERS AND SETTERS
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
            view = inflater.inflate(R.layout.post_card, null);
        }
        //ImpersonatorPost currentPost = posts.get(position);

        TextView postBody = (TextView) view.findViewById(R.id.postBody);
        postBody.setText(posts.get(position).getBody());
        TextView impersonatorName = (TextView) view.findViewById(R.id.tvImpersonatorName);
        impersonatorName.setText(posts.get(position).getImpersonatorName());
        TextView postCreationDate = (TextView) view.findViewById(R.id.tvPostCreationDate);
        // TODO: format date to fit card
        postCreationDate.setText("mmDDyyyy");


        return view;
    }
}
