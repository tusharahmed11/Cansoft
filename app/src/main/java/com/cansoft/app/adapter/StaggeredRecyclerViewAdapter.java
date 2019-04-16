package com.cansoft.app.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cansoft.app.R;
import com.cansoft.app.fragments.ServicePageFragment;
import com.cansoft.app.model.Service;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by User on 1/17/2018.
 */

public class StaggeredRecyclerViewAdapter extends RecyclerView.Adapter<StaggeredRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "StaggeredRecyclerViewAd";
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";
    private List<Service> services;

    /*private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();*/
    private Context mContext;
    ConstraintSet mConstraintSet1 = new ConstraintSet();
    public StaggeredRecyclerViewAdapter(Context context, List<Service> services) {
        /*mNames = names;
        mImageUrls = imageUrls;*/
        this.services = services;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Service service = services.get(position);
        /*Picasso.get().load(service.getImage().getData().getFullUrl()).into(holder.image);*/
        holder.name.setText(service.getName());


        /*Glide.with(mContext)
                .load(mImageUrls.get(position))
                .apply(requestOptions)
                .into(holder.image);*/
        Glide.with(mContext)
                .load(service.getImage().getData().getFullUrl())
                .into(holder.image);
     /*   String ratio =String.format("%d:%d", service.getImage().getData(),service.height);
        set.clone(holder.mConstraintLayout)
        set.setDimensionRatio(holder.mImgPoster.id, ratio)
        set.applyTo(holder.mConstraintLayout)*/

        /*holder.name.setText(mNames.get(position));*/

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ServicePageFragment detailsFragment = new ServicePageFragment();
                Bundle bundle = new Bundle();
                bundle.putString("header",service.getName());
                bundle.putString("details",service.getServiceDetails());
                detailsFragment.setArguments(bundle);
                AppCompatActivity activity =(AppCompatActivity) view.getContext();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.frame,detailsFragment,"detail").addToBackStack("detail").commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return services.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.imageview_widget);
            this.name = itemView.findViewById(R.id.name_widget);
        }
    }
}