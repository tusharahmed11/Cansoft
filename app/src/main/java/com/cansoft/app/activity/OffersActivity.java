package com.cansoft.app.activity;


import android.annotation.SuppressLint;
import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import android.widget.TextView;

import com.cansoft.app.R;


public class OffersActivity extends AppCompatActivity {
    public static final String EXTRA_NOTIF1 = "key.EXTRA_NOTIF";
    private BottomNavigationView navigationView;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        ImageView imageView = (ImageView) toolbar.findViewById(R.id.app_bar_logo);
        imageView.setVisibility(View.GONE);
        TextView textView = (TextView) toolbar.findViewById(R.id.app_bar_title);
        textView.setText("Offers");
        textView.setVisibility(View.VISIBLE);


        navigationView = (BottomNavigationView) findViewById(R.id.navigation_view);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Intent i = new Intent(OffersActivity.this, MainActivity.class);
                        i.putExtra("fragmentNumber",0);
                        startActivity(i);

                        return true;
                    case R.id.action_cost:
                        Intent i1 = new Intent(OffersActivity.this, MainActivity.class);
                        i1.putExtra("fragmentNumber",1);
                        startActivity(i1);
                        return true;
                    case R.id.action_service:
                        Intent i2 = new Intent(OffersActivity.this, MainActivity.class);
                        i2.putExtra("fragmentNumber",2);
                        startActivity(i2);

                        return true;
                    case R.id.action_team:
                        Intent i3 = new Intent(OffersActivity.this, MainActivity.class);
                        i3.putExtra("fragmentNumber",3);
                        startActivity(i3);
                        return true;
                    case R.id.action_contact:
                        Intent i4 = new Intent(OffersActivity.this, MainActivity.class);
                        i4.putExtra("fragmentNumber",4);
                        startActivity(i4);
                        return true;
                    default:
                        return true;

                }


            }
        });

        WebView myWebView = (WebView) findViewById(R.id.offer_view);
        myWebView.loadUrl("https://cansoft.com/offers.html");
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);



        /*getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.frame);
                Log.d(TAG, "onBackStackChanged: " + currentFragment);
                if (currentFragment instanceof ContactFragment) {
                    Log.d(TAG, "onBackStackChanged: " + currentFragment);
                    contactfab.setVisibility(View.GONE);
                } else {
                    contactfab.setVisibility(View.VISIBLE);
                }
                if (currentFragment instanceof AssessmentFragment){
                    lyt_assessment.setVisibility(View.GONE);
                }else{
                    lyt_assessment.setVisibility(View.VISIBLE);

                }
                if (currentFragment instanceof SeoFragment){
                    lyt_seo.setVisibility(View.GONE);
                }else {
                    lyt_seo.setVisibility(View.VISIBLE);
                }


            }
        });*/


    }




    /*private void setupFab() {

        Fab fab = (Fab) findViewById(R.id.fab);
        View sheetView = findViewById(R.id.fab_sheet);
        View overlay = findViewById(R.id.overlay);
        int sheetColor = getResources().getColor(R.color.background_card);
        int fabColor = getResources().getColor(R.color.colorAccent);

        // Create material sheet FAB
        materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay, sheetColor, fabColor);

        // Set material sheet event listener
        materialSheetFab.setEventListener(new MaterialSheetFabEventListener() {
            @Override
            public void onShowSheet() {
                // Save current status bar color
                statusBarColor = getStatusBarColor();
                // Set darker status bar color to match the dim overlay
                setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            }

            @Override
            public void onHideSheet() {
                // Restore status bar color
                setStatusBarColor(statusBarColor);
            }
        });


        // Set material sheet item click listeners
        lyt_assessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssessmentFragment assessmentFragment = new AssessmentFragment();
                changeFragment(assessmentFragment);
            }
        });
        contactfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactFragment contactFragment = new ContactFragment();
                changeFragment(contactFragment);

            }
        });
        lyt_seo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeoFragment seoFragment = new SeoFragment();
                changeFragment(seoFragment);

            }
        });
     *//*   findViewById(R.id.fab_sheet_item_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Contact", Toast.LENGTH_SHORT).show();

            }
        });*//*



    }*/

    /*private int getStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getWindow().getStatusBarColor();
        }
        return 0;
    }

    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }
    public void hideFab(){
        if (materialSheetFab.isSheetVisible()) {
            materialSheetFab.hideSheet();
        }
    }
*//*
    public Fragment currentFragment(){
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.frame);
        return currentFragment;
        // Log.d(TAG, "currentFragment: "+currentFragment().getId());
    }*//*


    @Override public void onBackPressed() {
        if (materialSheetFab.isSheetVisible()) {
            materialSheetFab.hideSheet();
        }
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation_view);
        int seletedItemId = bottomNavigationView.getSelectedItemId();
        if (R.id.action_home != seletedItemId) {
            setHomeItem(OffersActivity.this);
        } else {
            super.onBackPressed();
        }
    }

    public static void setHomeItem(Activity activity) {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                activity.findViewById(R.id.navigation_view);
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }*/

}
