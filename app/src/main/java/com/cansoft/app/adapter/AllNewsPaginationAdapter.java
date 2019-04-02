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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cansoft.app.R;
import com.cansoft.app.fragments.NewsDetailsFragment;
import com.cansoft.app.model.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AllNewsPaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private List<Post> posts;
    private Context context;
    private boolean isLoadingAdded = false;

    public AllNewsPaginationAdapter(Context context) {
        this.context = context;
        posts = new ArrayList<>();
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }
    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.all_news_list, parent, false);
        viewHolder = new MyViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Post result = posts.get(position); //

        switch (getItemViewType(position)) {
            case ITEM:
                final MyViewHolder myVH = (MyViewHolder) holder;
                if (!(result.getEmbedded().getWpFeaturedmedia() == null)){
                    Picasso.get().load("https://cansoft.com" +result.getEmbedded().getWpFeaturedmedia().get(0).getSourceUrl()).into(myVH.image);
                }
                String date = result.getDate().substring(0, result.getDate().indexOf("T"));

                myVH.date.setText(date);
                myVH.title.setText(result.getTitle().getRendered());
                myVH.more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NewsDetailsFragment detailsFragment = new NewsDetailsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", result.getId());
                        detailsFragment.setArguments(bundle);
                        AppCompatActivity activity =(AppCompatActivity) v.getContext();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame,detailsFragment,"").addToBackStack(null).commit();
                    }
                });
                myVH.newsLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NewsDetailsFragment detailsFragment = new NewsDetailsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", result.getId());
                        detailsFragment.setArguments(bundle);
                        AppCompatActivity activity =(AppCompatActivity) v.getContext();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame,detailsFragment,"").addToBackStack(null).commit();
                    }
                });
                break;

            case LOADING:
//                Do nothing
                break;
        }

    }


    @Override
    public int getItemCount() {
        return posts == null ? 0 : posts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == posts.size() -1 && isLoadingAdded) ? LOADING : ITEM;
    }
    public void add(Post r){
        posts.add(r);
        notifyItemInserted(posts.size() -1);
    }
    public void addAll(List<Post> posts){
        for (Post results : posts){
            add(results);
        }
    }
    public void remove(Post r){
        int position = posts.indexOf(r);
        if (position > -1){
            posts.remove(position);
            notifyItemRemoved(position);
        }
    }
    public void clear(){
        isLoadingAdded = false;
        while (getItemCount() > 0){
            remove(getItem(0));
        }
    }
    public boolean isEmpty(){
        return getItemCount() == 0;
    }
    public void addLoadingFooter(){
        isLoadingAdded = true;
        add(new Post());
    }
    public void removeLoadingFooter(){
        isLoadingAdded = false;
        int position = posts.size() -1;
        Post result = getItem(position);
        if (result != null){
            posts.remove(position);
            notifyItemRemoved(position);
        }
    }
    public Post getItem(int position){
        return posts.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView date;
        TextView more;
        private LinearLayout newsLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.all_news_image);
            title = (TextView) itemView.findViewById(R.id.all_news_title);
            date = (TextView) itemView.findViewById(R.id.all_news_date);
            more = (TextView) itemView.findViewById(R.id.all_news_more);
            newsLayout = (LinearLayout) itemView.findViewById(R.id.all_news_layout);
        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
    private String getDate(String url) {
        String pattern = "/T.*/gm";

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url); //url is youtube url for which you want to extract the id.
        if (matcher.find()) {
            return matcher.group();
        }
        return pattern;
    }
}
