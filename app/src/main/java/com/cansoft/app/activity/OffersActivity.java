package com.cansoft.app.activity;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cansoft.app.R;
import com.cansoft.app.fragments.AssessmentFragment;
import com.cansoft.app.fragments.ContactFragment;
import com.cansoft.app.fragments.CostFragment;
import com.cansoft.app.fragments.HomeFragment;
import com.cansoft.app.fragments.SeoFragment;
import com.cansoft.app.fragments.ServiceFragment;
import com.cansoft.app.fragments.TeamFragment;
import com.cansoft.app.widget.Fab;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;

import shortbread.Shortbread;
import shortbread.Shortcut;

public class OffersActivity extends AppCompatActivity {
    public static final String EXTRA_NOTIF1 = "key.EXTRA_NOTIF";



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


        /*final FrameLayout frame = findViewById(R.id.frame);
        frame.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                frame.getWindowVisibleDisplayFrame(r);
                int screenHeight = frame.getRootView().getHeight();

                // r.bottom is the position above soft keypad or device button.
                // if keypad is shown, the r.bottom is smaller than that before.
                int keypadHeight = screenHeight - r.bottom;

                Log.d(TAG, "keypadHeight = " + keypadHeight);

                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    // keyboard is opened
                    Log.d(TAG, "onGlobalLayout: keyboard is on");
                    materialSheetFab.hideSheetThenFab();

                }
                else {
                    materialSheetFab.showFab();

                }
            }
        });*/



/*
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setPadding(0, 0, 0, 0);
        toolbar.setBackgroundColor(getResources().getColor(R.color.grey_900));*/



        /*navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        if (!(currentFragment() instanceof HomeFragment)){
                            HomeFragment home = new HomeFragment();
                            changeFragment(home);
                        }


                        return true;
                    case R.id.action_cost:
                        if (!(currentFragment() instanceof CostFragment)){
                            CostFragment cost = new CostFragment();
                            changeFragment(cost);
                        }
                        return true;
                    case R.id.action_service:
                        if (!(currentFragment() instanceof ServiceFragment)){
                            ServiceFragment service = new ServiceFragment();
                            changeFragment(service);
                        }

                        return true;
                    case R.id.action_team:
                        if(!(currentFragment() instanceof TeamFragment)){
                            TeamFragment team = new TeamFragment();
                            changeFragment(team);
                        }
                        return true;
                    case R.id.action_contact:
                        if(!(currentFragment() instanceof ContactFragment)){
                            ContactFragment contact = new ContactFragment();
                            changeFragment(contact);
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            Fragment currentFragment = fragmentManager.findFragmentById(R.id.frame);
                            if (currentFragment == contact) {
                                contactfab.setVisibility(View.GONE);
                            }
                        }
                        return true;
                    default:
                        return true;

                }


            }
        });*/




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
