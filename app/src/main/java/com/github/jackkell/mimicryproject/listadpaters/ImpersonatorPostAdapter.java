package com.github.jackkell.mimicryproject.listadpaters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.jackkell.mimicryproject.R;
import com.github.jackkell.mimicryproject.entity.Impersonator;
import com.github.jackkell.mimicryproject.entity.ImpersonatorPost;

import java.util.List;

//Used to auto-populate the custom Impersontor Post List View
public class ImpersonatorPostAdapter extends RecyclerView.Adapter<ImpersonatorPostAdapter.ImpersonatorPostViewHolder> {

    private Impersonator impersonator;
    private List<ImpersonatorPost> posts;

    public static class ImpersonatorPostViewHolder extends RecyclerView.ViewHolder {
        CardView postCardView;
        RelativeLayout headerInformation;
        TextView impersonatorName;
        TextView postBody;
        TextView postCreationDate;
        LinearLayout footerInformation;
        TextView tweetButton;
        TextView favoriteButton;
        Button deleteButton;

        public ImpersonatorPostViewHolder(View itemView) {
            super(itemView);
            this.postCardView = (CardView)itemView.findViewById(R.id.cvPost);
            this.headerInformation = (RelativeLayout)itemView.findViewById(R.id.rlHeaderInformation);
            this.impersonatorName = (TextView)itemView.findViewById(R.id.tvImpersonatorName);
            this.postBody = (TextView)itemView.findViewById(R.id.tvPostBody);
            this.postCreationDate = (TextView)itemView.findViewById(R.id.tvPostCreationDate);
            this.footerInformation = (LinearLayout)itemView.findViewById(R.id.llFooterInformation);
            this.tweetButton = (TextView)itemView.findViewById(R.id.btnTweetContent);
            this.favoriteButton = (TextView)itemView.findViewById(R.id.btnFavoriteContent);
            this.deleteButton = (Button)itemView.findViewById(R.id.btnDeleteContent);
        }
    }

    //Creates an Adapter based on the passed attributes
    public ImpersonatorPostAdapter(Impersonator impersonator) {
        this.impersonator = impersonator;
        this.posts = impersonator.getImpersonatorPosts();
    }

    @Override
    public ImpersonatorPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_card, parent, false);
        ImpersonatorPostViewHolder impersonatorPostViewHolder = new ImpersonatorPostViewHolder(view);
        return  impersonatorPostViewHolder;
    }

    @Override
    public void onBindViewHolder(ImpersonatorPostViewHolder impersonatorPostViewHolder, final int position) {
        ImpersonatorPost currentPost = posts.get(position);
        impersonatorPostViewHolder.impersonatorName.setText(impersonator.getName());
        impersonatorPostViewHolder.postBody.setText(currentPost.getBody());


        impersonatorPostViewHolder.postCreationDate.setText(currentPost.getDateCreated().toString());

        impersonatorPostViewHolder.tweetButton.setText(Boolean.toString(currentPost.isTweeted()));
        impersonatorPostViewHolder.favoriteButton.setText(Boolean.toString(currentPost.isFavorited()));
        impersonatorPostViewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePost(posts.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void addPost(ImpersonatorPost post) {
        posts.add(post);
        this.notifyDataSetChanged();
    }

    public void deletePost(ImpersonatorPost post) {
        posts.remove(post);
        this.notifyDataSetChanged();
    }
}
