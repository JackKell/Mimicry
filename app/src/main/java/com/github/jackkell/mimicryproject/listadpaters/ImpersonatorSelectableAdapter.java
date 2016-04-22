package com.github.jackkell.mimicryproject.listadpaters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.jackkell.mimicryproject.R;
import com.github.jackkell.mimicryproject.dao.ImpersonatorDao;
import com.github.jackkell.mimicryproject.dao.ImpersonatorPostDao;
import com.github.jackkell.mimicryproject.entity.Impersonator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//Used to auto-populate the custom Impersonator Selectable List View
public class ImpersonatorSelectableAdapter extends RecyclerView.Adapter<ImpersonatorSelectableAdapter.ImpersonatorViewHolder>{

    private List<Impersonator> impersonators;
    private ImpersonatorDao impersonatorDao;

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
        Button btnDeleteImpersonator;

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
            this.btnDeleteImpersonator = (Button)itemView.findViewById(R.id.btnDeleteImpersonator);

        }
    }

    public ImpersonatorSelectableAdapter(List<Impersonator> impersonators, ImpersonatorDao impersonatorDao){
        this.impersonators = impersonators;
        this.impersonatorDao = impersonatorDao;
    }

    @Override
    public ImpersonatorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.impersonator_card, parent, false);
        ImpersonatorViewHolder impersonatorViewHolder = new ImpersonatorViewHolder(view);
        return impersonatorViewHolder;
    }

    @Override
    public void onBindViewHolder(ImpersonatorViewHolder impersonatorViewHolder, final int position) {
        final Impersonator currentImpersonator = impersonators.get(position);
        impersonatorViewHolder.tvImpersonatorName.setText(currentImpersonator.getName());

        Date impersonatorCreationDate = currentImpersonator.getDateCreated();
        Calendar now = Calendar.getInstance();
        Calendar creationCal = Calendar.getInstance();
        creationCal.setTime(impersonatorCreationDate);
        if (creationCal.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)) {
            if (creationCal.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                impersonatorViewHolder.tvDateCreated.setText(timeFormat.format(impersonatorCreationDate));
            }
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
            impersonatorViewHolder.tvDateCreated.setText(dateFormat.format(impersonatorCreationDate));
        }

        String postCountText = Integer.toString(currentImpersonator.getPostCount());
        impersonatorViewHolder.tvPostCount.setText(postCountText);

        String tweetCountText = Integer.toString(currentImpersonator.getIsTweetedCount());
        impersonatorViewHolder.tvTweetCount.setText(tweetCountText);

        String favoriteCountText = Integer.toString(currentImpersonator.getIsFavoritedCount());
        impersonatorViewHolder.tvFavoriteCount.setText(favoriteCountText);

        impersonatorViewHolder.btnDeleteImpersonator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImpersonator(currentImpersonator);
            }
        });
    }

    @Override
    public int getItemCount() {
        return impersonators.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void deleteImpersonator(Impersonator impersonator){
        impersonators.remove(impersonator);
        impersonatorDao.delete(impersonator.getId());
        this.notifyDataSetChanged();
    }
}
