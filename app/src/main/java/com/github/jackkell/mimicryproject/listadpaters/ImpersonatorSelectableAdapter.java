package com.github.jackkell.mimicryproject.listadpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.jackkell.mimicryproject.R;
import com.github.jackkell.mimicryproject.databaseobjects.Impersonator;

import java.util.List;

//Used to auto-populate the custom Impersonator Selectable List View
public class ImpersonatorSelectableAdapter extends BaseAdapter{

    //A list of all of the Impersonators in the database
    private List<Impersonator> impersonators;
    //pumps up a list with content
    private static LayoutInflater inflater = null;

    //Creates an Adapter based on the passed attributes
    public ImpersonatorSelectableAdapter(Context context, List<Impersonator> impersonators) {
        this.impersonators = impersonators;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //GETTERS AND SETTERS
    @Override
    public int getCount() {
        return impersonators.size();
    }

    @Override
    public Object getItem(int position) {
        return impersonators.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.util_impersonator_selectable, null);
        }
        TextView impersonatorName = (TextView) view.findViewById(R.id.tvImpersonatorName);
        TextView postCount = (TextView) view.findViewById(R.id.tvPostCount);
        TextView favoriteCount = (TextView) view.findViewById(R.id.tvFavoriteCount);
        TextView tweetCount = (TextView) view.findViewById(R.id.tvTweetCount);
        TextView dateCreated = (TextView) view.findViewById(R.id.tvDateCreated);

        final Impersonator impersonator = impersonators.get(position);

        impersonatorName.setText(impersonator.getName());
        postCount.setText(Integer.toString(impersonator.getPostCount()));
        favoriteCount.setText(Integer.toString(impersonator.getIsFavoritedPostCount()));
        tweetCount.setText(Integer.toString(impersonator.getIsTweetedPostCount()));
        dateCreated.setText(impersonator.getDateCreated());

        return view;
    }
}
