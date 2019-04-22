package com.cansoft.app.activity;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cansoft.app.R;
import com.cansoft.app.fragments.HomeFragment;
import com.cansoft.app.model.Post;
import com.cansoft.app.network.RestClient;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class NotificationNewsActivity extends AppCompatActivity {

    private static final String TAG = "";
    private ImageView newsImage;
    private TextView newsTitle;
    private TextView newsDate;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;
    private WebView detailsweb;
    private CollapsingToolbarLayout collapsing_toolbar;
    private HtmlTextView htmlVIew;
    private Toolbar detailsToolbar;
    private FragmentManager fragmentManager ;
    public static final String EXTRA_NOTIF = "key.EXTRA_NOTIF";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_news);
/*
        if (getIntent().getExtras() != null) {

            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);

                if (key.equals("AnotherActivity") && value.equals("True")) {
                    Intent intent = new Intent(this, NotificationNewsActivity.class);
                    intent.putExtra("value", value);
                    startActivity(intent);
                    finish();
                }

            }
        }*/


        fragmentManager = getSupportFragmentManager();

        newsImage = (ImageView) findViewById(R.id.news_details_image);

        detailsweb = (WebView) findViewById(R.id.detailsweb);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.cordinate_view);
        collapsing_toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        detailsToolbar = (Toolbar) findViewById(R.id.toolbar);
        detailsToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
        detailsToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.details_loadmore_progress);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        int id = bundle.getInt("id");
        updateView(id);

        Log.d(TAG, "onCreate: " +id);

        progressBar.setVisibility(View.VISIBLE);
        coordinatorLayout.setVisibility(View.GONE);


    }

    private void updateView(int id) {

        RestClient.getInstance().callRetrofit(this).getPostDetails(id).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Log.d(TAG, "onResponse: " +response);
                if (response.body()==null){
                    showAlertDialog();
                }else {
                    coordinatorLayout.setVisibility(View.VISIBLE);
                    Post post = response.body();
                    if (!(post.getEmbedded().getWpFeaturedmedia() == null)){
                        Picasso.get().load("https://cansoft.com" +post.getEmbedded().getWpFeaturedmedia().get(0).getSourceUrl()).into(newsImage);
                    }
                    String date = post.getDate().substring(0, post.getDate().indexOf("T"));
                    Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
                    toolbar1.setTitle(post.getTitle().getRendered());
                    toolbar1.setTitleTextAppearance(getApplicationContext(),R.style.TextAppearance_AppCompat_Small);
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
    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("NO Internet!");
        builder.setMessage("Please connect to the internet");
        builder.setPositiveButton("BACK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                HomeFragment fragment = new HomeFragment();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,
                        R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.frame,fragment, "fragment").addToBackStack(null).commit();
            }
        });

        builder.show();
    }

}
