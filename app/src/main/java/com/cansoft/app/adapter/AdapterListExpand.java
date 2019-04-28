package com.cansoft.app.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.cansoft.app.R;
import com.cansoft.app.model.Member;
import com.cansoft.app.util.Tools;
import com.cansoft.app.util.ViewAnimation;
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

        private ImageView fullImage;
        private TextView name;
        private TextView designation;
        private TextView phone;
        private TextView emailAddress;
        private TextView teamTwitter;
        private TextView teamFacebook;
        private ImageButton bt_expand;
        private View lyt_expand;
        private View lyt_parent;
        private RelativeLayout teamTwitterL;
        private RelativeLayout teamFacebookL;
        private RelativeLayout teamPhoneL;
        private RelativeLayout teamEmailL;

        private ProgressBar teamListProgressbar;

        public OriginalViewHolder(View v) {
            super(v);

            fullImage = (ImageView) v.findViewById(R.id.profile_image);
            name = (TextView) v.findViewById(R.id.name);
            phone = (TextView) v.findViewById(R.id.team_phone_number);
            emailAddress = (TextView) v.findViewById(R.id.team_email_address);
            designation = (TextView) v.findViewById(R.id.designation);
            bt_expand = (ImageButton) v.findViewById(R.id.bt_expand);
            lyt_expand = (View) v.findViewById(R.id.lyt_expand);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
            teamTwitter = (TextView) v.findViewById(R.id.team_twitter);
            teamTwitterL= (RelativeLayout) v.findViewById(R.id.twitter_layout);
            teamFacebookL= (RelativeLayout) v.findViewById(R.id.facebook_layout);
            teamPhoneL= (RelativeLayout) v.findViewById(R.id.team_phone_layout);
            teamEmailL= (RelativeLayout) v.findViewById(R.id.team_email_Layout);
            teamFacebook = (TextView) v.findViewById(R.id.team_facebook);
            teamFacebook = (TextView) v.findViewById(R.id.team_facebook);

            /*teamListProgressbar = (ProgressBar) v.findViewById(R.id.teamListProgressbar);*/
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
            if (p.getTwitter().isEmpty()){
                view.teamTwitterL.setVisibility(View.GONE);
            }
            if (p.getFacebook().isEmpty()){
                view.teamFacebookL.setVisibility(View.GONE);
            }
            if (p.getEmail().isEmpty()){
                view.teamEmailL.setVisibility(View.GONE);
            }
            if (p.getPhoneNumber().isEmpty()){
                view.teamPhoneL.setVisibility(View.GONE);
            }

            view.name.setText(p.getName());
            view.designation.setText(p.getDesignation());
            view.phone.setText(p.getPhoneNumber());
            view.phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+p.getPhoneNumber()));
                    ctx.startActivity(intent);

                }
            });
            view.emailAddress.setText(p.getEmail());
            view.emailAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Intent.ACTION_SEND);
                    String[] recipients={p.getEmail()};
                    intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                    intent.setType("text/html");
                    intent.setPackage("com.google.android.gm");
                    ctx.startActivity(Intent.createChooser(intent, "Send mail"));
                }
            });
            view.teamTwitter.setText(p.getTwitter());
            view.teamTwitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String remove = "@";
                    ctx.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://twitter.com/"+p.getTwitter().replace(remove,""))));
                }
            });
            view.teamFacebook.setText(p.getFacebook());
            view.teamFacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ctx.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://"+p.getFacebook())));
                }
            });

           /* RequestOptions options = new RequestOptions()
                    .error(R.drawable.ic_broken_image)
                    .priority(Priority.HIGH);

            new GlideImageLoader(view.fullImage,
                    view.teamListProgressbar).load(p.getPhoto().getData().getFullUrl(),options);*/
            RequestOptions requestOption = new RequestOptions()
                    .placeholder(R.drawable.ic_team_place).centerCrop();
            Glide.with(ctx)
                    .load(p.getPhoto().getData().getFullUrl())   //passing your url to load image.
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(requestOption)
                    .into(view.fullImage);

            /*Glide.with(ctx)
                    .load(p.getPhoto().getData().getFullUrl())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            view.teamListProgressbar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            view.teamListProgressbar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(view.fullImage);*/

            /*Picasso.get().load(p.getPhoto().getData().getFullUrl()).into(view.fullImage, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    if (view.teamListProgressbar != null) {
                        view.teamListProgressbar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onError(Exception e) {

                }


            });*/



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
