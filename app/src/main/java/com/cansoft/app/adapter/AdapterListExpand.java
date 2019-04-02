package com.cansoft.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cansoft.app.R;
import com.cansoft.app.model.Member;
import com.cansoft.app.model.Social;
import com.cansoft.app.util.Tools;
import com.cansoft.app.util.ViewAnimation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterListExpand extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Member> items = new ArrayList<>();


    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Member obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterListExpand(Context context, List<Member> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private ImageView fullImage;
        private TextView name;
        private TextView designation;
        private TextView phone;
        private TextView emailAddress;
        private ImageButton bt_expand;
        private View lyt_expand;
        private View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            fullImage = (ImageView) v.findViewById(R.id.profile_image);
            name = (TextView) v.findViewById(R.id.name);
            phone = (TextView) v.findViewById(R.id.team_phone_number);
            emailAddress = (TextView) v.findViewById(R.id.team_email_address);
            designation = (TextView) v.findViewById(R.id.designation);
            bt_expand = (ImageButton) v.findViewById(R.id.bt_expand);
            lyt_expand = (View) v.findViewById(R.id.lyt_expand);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_list_item, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            final OriginalViewHolder view = (OriginalViewHolder) holder;

            final Member p = items.get(position);
            view.name.setText(p.getName());
            view.designation.setText(p.getDesignation());
            view.phone.setText(p.getPhoneNumber());
            view.emailAddress.setText(p.getEmail());
            Picasso.get().load(p.getPhoto().getData().getFullUrl()).into(view.fullImage);
            /*Tools.displayImageOriginal(ctx, view.image, p.getPhoto().getFullUrl());
            Tools.displayImageOriginal(ctx, view.fullImage, p.getPhoto().getFullUrl());*/
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });

            view.bt_expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean show = toggleLayoutExpand(!p.expanded, v, view.lyt_expand);
                    items.get(position).expanded = show;
                }
            });


            // void recycling view
            if(p.expanded){
                view.lyt_expand.setVisibility(View.VISIBLE);
            } else {
                view.lyt_expand.setVisibility(View.GONE);
            }
            Tools.toggleArrow(p.expanded, view.bt_expand, false);

        }
    }

    private boolean toggleLayoutExpand(boolean show, View view, View lyt_expand) {
        Tools.toggleArrow(show, view);
        if (show) {
            ViewAnimation.expand(lyt_expand);
        } else {
            ViewAnimation.collapse(lyt_expand);
        }
        return show;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
