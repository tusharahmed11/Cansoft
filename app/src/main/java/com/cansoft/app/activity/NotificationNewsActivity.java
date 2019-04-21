package com.cansoft.app.activity;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cansoft.app.R;
import com.cansoft.app.fragments.HomeFragment;

import org.sufficientlysecure.htmltextview.HtmlTextView;

public class NotificationNewsActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_news);
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

        progressBar.setVisibility(View.VISIBLE);
        coordinatorLayout.setVisibility(View.GONE);

        Bundle bundle = getIntent().getExtras();

        int id = bundle.getInt("id");

        updateView(id);
        showBackButtonStatus(true);
    }

    private void updateView(int id) {


    }
    private void showAlertDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
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
    private void showBackButtonStatus(Boolean status){
         getSupportActionBar().setDisplayHomeAsUpEnabled(status);
        getSupportActionBar().setDisplayShowHomeEnabled(status);
    }
}
