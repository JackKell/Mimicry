package com.github.jackkell.mimicryproject.listadpaters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.jackkell.mimicryproject.R;
import com.github.jackkell.mimicryproject.databaseobjects.Impersonator;

import java.util.List;

//Used to auto-populate the custom Impersonator Selectable List View
public class ImpersonatorSelectableAdapter extends RecyclerView.Adapter<ImpersonatorSelectableAdapter.ImpersonatorViewHolder>{

    private List<Impersonator> impersonators;

    public static class ImpersonatorViewHolder extends RecyclerView.ViewHolder {
        CardView cvImpersonator;
        TextView tvImpersonatorName;
        TextView tvDateCreated;
        TextView tvPostLabel;
        TextView tvPostCount;
        TextView tvTweetLabel;
        TextView tvTweetCount;
        TextView tvFavoriteLabel;
        TextView tvFavoriteCount;

        public ImpersonatorViewHolder(View itemView) {
            super(itemView);
            this.cvImpersonator = (CardView)itemView.findViewById(R.id.cvImpersonator);
            this.tvImpersonatorName = (TextView)itemView.findViewById(R.id.tvImpersonatorName);
            this.tvDateCreated = (TextView)itemView.findViewById(R.id.tvDateCreated);
            this.tvPostLabel = (TextView)itemView.findViewById(R.id.tvPostLabel);
            this.tvPostCount = (TextView)itemView.findViewById(R.id.tvPostCount);
            this.tvFavoriteLabel = (TextView)itemView.findViewById(R.id.tvFavoriteLabel);
            this.tvFavoriteCount = (TextView)itemView.findViewById(R.id.tvFavoriteCount);
            this.tvTweetLabel = (TextView)itemView.findViewById(R.id.tvTweetLabel);
            this.tvTweetCount = (TextView)itemView.findViewById(R.id.tvTweetCount);
        }
    }

    public ImpersonatorSelectableAdapter(List<Impersonator> impersonators){
        this.impersonators = impersonators;
    }

    @Override
    public ImpersonatorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.impersonator_card, parent, false);
        ImpersonatorViewHolder impersonatorViewHolder = new ImpersonatorViewHolder(view);
        return impersonatorViewHolder;
    }

    @Override
    public void onBindViewHolder(ImpersonatorViewHolder impersonatorViewHolder, int position) {
        impersonatorViewHolder.tvImpersonatorName.setText(impersonators.get(position).getName());
        impersonatorViewHolder.tvDateCreated.setText(impersonators.get(position).getDateCreated());
        impersonatorViewHolder.tvPostCount.setText(Integer.toString(impersonators.get(position).getPostCount()));
        impersonatorViewHolder.tvTweetCount.setText(Integer.toString(impersonators.get(position).getTweetCount()));
        impersonatorViewHolder.tvFavoriteCount.setText(Integer.toString(impersonators.get(position).getTweetCount()));
    }

    @Override
    public int getItemCount() {
        return impersonators.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void addImpersonator(Impersonator impersonator){
        impersonators.add(impersonator);
        this.notifyDataSetChanged();
    }
}
