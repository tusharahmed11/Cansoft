package com.cansoft.app.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cansoft.app.R;
import com.cansoft.app.fragments.NewsDetailsFragment;
import com.cansoft.app.model.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeNewsAdapter extends RecyclerView.Adapter<HomeNewsAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private Context mContext;
    private List<Post> posts;

    public HomeNewsAdapter( Context context, List<Post> posts) {
        mContext = context;
        this.posts = posts;
        inflater = LayoutInflater.from(mContext);
    }


    @NonNull
    @Override
    public HomeNewsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.home_news_list,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeNewsAdapter.MyViewHolder holder, final int position) {
        if (!(posts.get(position).getEmbedded().getWpFeaturedmedia() == null)){
        Picasso.get().load("https://cansoft.com" +posts.get(position).getEmbedded().getWpFeaturedmedia().get(0).getSourceUrl()).into(holder.postImage);}
        else{

        }
        holder.postTitle.setText(posts.get(position).getTitle().getRendered());
        holder.postTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsDetailsFragment detailsFragment = new NewsDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", posts.get(position).getId());
                detailsFragment.setArguments(bundle);
                AppCompatActivity activity =(AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame,detailsFragment,"").addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView postImage;
        private TextView postTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            postImage = (ImageView) itemView.findViewById(R.id.home_news_image);
            postTitle = (TextView) itemView.findViewById(R.id.home_news_title);
        }
    }
}
