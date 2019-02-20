package com.cansoft.cansoft.cansoft.fragments;


import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cansoft.cansoft.cansoft.R;
import com.cansoft.cansoft.cansoft.activity.MainActivity;
import com.cansoft.cansoft.cansoft.model.Post;
import com.cansoft.cansoft.cansoft.network.RestClient;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlAssetsImageGetter;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsDetailsFragment extends Fragment  {

    private ImageView newsImage;
    private TextView newsTitle;
    private TextView newsDate;
    /*private TextView newsDescription;*/
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;
    private WebView detailsweb;
    private CollapsingToolbarLayout collapsing_toolbar;
    private HtmlTextView htmlVIew;
    private Toolbar detailsToolbar;

/*
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.6f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;*/




    public NewsDetailsFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_details, container, false);
      /*  mTitle          = (TextView) view.findViewById(R.id.main_textview_title);
        mTitleContainer = (LinearLayout) view.findViewById(R.id.main_linearlayout_title);
        mAppBarLayout   = (AppBarLayout) view.findViewById(R.id.app_bar_layout);*/
        newsImage = (ImageView) view.findViewById(R.id.news_details_image);

        detailsweb = (WebView) view.findViewById(R.id.detailsweb);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.cordinate_view);
        collapsing_toolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        detailsToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        detailsToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
        detailsToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        progressBar = (ProgressBar) view.findViewById(R.id.details_loadmore_progress);

        progressBar.setVisibility(View.VISIBLE);
        coordinatorLayout.setVisibility(View.GONE);


        /*makeTransperantStatusBar(true);*/


        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.app_bar);
        toolbar.setVisibility(View.GONE);



        Bundle bundle = getArguments();

        int id = bundle.getInt("id");
        
        updateView(id,view);
        showBackButtonStatus(true);

        return view;
    }

    private void updateView(int id, final View view) {
       RestClient.getInstance().callRetrofit(view.getContext()).getPostDetails(id).enqueue(new Callback<Post>() {
           @Override
           public void onResponse(Call<Post> call, Response<Post> response) {
               Log.d(TAG, "onResponse: " +response);
               if (response.body()==null){
                   showAlertDialog(view);
               }else {
                   coordinatorLayout.setVisibility(View.VISIBLE);
                   Post post = response.body();
                   if (!(post.getEmbedded().getWpFeaturedmedia() == null)){
                       Picasso.get().load("https://cansoft.com" +post.getEmbedded().getWpFeaturedmedia().get(0).getSourceUrl()).into(newsImage);
                   }
                   String date = post.getDate().substring(0, post.getDate().indexOf("T"));
                   Toolbar toolbar1 = (Toolbar) view.findViewById(R.id.toolbar);
                   toolbar1.setTitle(post.getTitle().getRendered());
                   toolbar1.setTitleTextAppearance(view.getContext(),R.style.TextAppearance_AppCompat_Small);
/*
               newsTitle.setText(post.getTitle().getRendered());
*/
                   /*newsDate.setText(date);*/
                   String html = post.getContent().getRendered();
                   String mime = "text/html";
                   String encoding = "utf-8";
                   detailsweb.getSettings().setJavaScriptEnabled(true);
                   detailsweb.loadData(html,mime,encoding);
                   detailsToolbar.setTitle(post.getTitle().getRendered());
          /*     htmlVIew.setHtml(html,
                       new HtmlHttpImageGetter(htmlVIew));*/
/*
               Spanned sp = Html.fromHtml(post.getContent().getRendered());
*/
/*
               newsDescription.setText(Html.fromHtml(post.getContent().getRendered()));
*/
                   progressBar.setVisibility(View.GONE);
                   coordinatorLayout.setVisibility(View.VISIBLE);
               }

           }

           @Override
           public void onFailure(Call<Post> call, Throwable t) {

           }
       });
    }


   /* @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }*/

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void makeTransperantStatusBar(boolean isTransperant) {
        if (isTransperant) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    private void showAlertDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setCancelable(false);
        builder.setTitle("NO Internet!");
        builder.setMessage("Please connect to the internet");
        builder.setPositiveButton("BACK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                HomeFragment homeFragment = new HomeFragment();
                ft.replace(R.id.frame, homeFragment).addToBackStack(null);
                ft.commit();
            }
        });

        builder.show();
    }
    private void showBackButtonStatus(Boolean status){
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(status);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(status);
    }

}
