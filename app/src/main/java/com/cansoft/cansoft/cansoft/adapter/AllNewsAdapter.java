package com.cansoft.cansoft.cansoft.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cansoft.cansoft.cansoft.R;
import com.cansoft.cansoft.cansoft.model.Content;
import com.cansoft.cansoft.cansoft.model.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

/*import butterknife.BindView;
import butterknife.ButterKnife;*/

public class AllNewsAdapter extends RecyclerView.Adapter<AllNewsAdapter.MyViewHolder>{
    private Context context;
    private List<Post> posts;

    public AllNewsAdapter(Context mcontext, List<Post> posts) {
        this.context = mcontext;
        this.posts = posts;
    }

    @NonNull
    @Override
    public AllNewsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.all_news_list,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllNewsAdapter.MyViewHolder holder, int position) {
        Post post = posts.get(position);
        if (!(post.getEmbedded().getWpFeaturedmedia() == null)){
            Picasso.get().load("https://cansoft.com" +post.getEmbedded().getWpFeaturedmedia().get(0).getSourceUrl()).into(holder.image);
        }
        holder.title.setText(post.getTitle().getRendered());
        holder.date.setText(post.getDate());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        /*@BindView(R.id.all_news_image) ImageView image;
        @BindView(R.id.all_news_title) TextView title;
        @BindView(R.id.all_news_date) TextView date;
        @BindView(R.id.all_news_more) TextView more;*/
        ImageView image;
        TextView title;
        TextView date;
        TextView more;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.all_news_image);
            title = (TextView) itemView.findViewById(R.id.all_news_title);
            date = (TextView) itemView.findViewById(R.id.all_news_date);
            more = (TextView) itemView.findViewById(R.id.all_news_more);

/*
            ButterKnife.bind(this.itemView);
*/
        }
    }
}
