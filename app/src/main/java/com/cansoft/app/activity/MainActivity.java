package com.cansoft.app.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cansoft.app.adapter.ViewPagerAdapter;
import com.cansoft.app.R;
import com.cansoft.app.fragments.HomeFragment;
import com.cansoft.app.fragments.AssessmentFragment;
import com.cansoft.app.fragments.ContactFragment;
import com.cansoft.app.fragments.CostFragment;
import com.cansoft.app.fragments.SeoFragment;
import com.cansoft.app.fragments.ServiceFragment;
import com.cansoft.app.fragments.TeamFragment;
import com.cansoft.app.model.DeviceInfo;
import com.cansoft.app.model.NotStatus;
import com.cansoft.app.network.NotificationClient;
import com.cansoft.app.util.Tools;
import com.cansoft.app.widget.Fab;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shortbread.Shortbread;
import shortbread.Shortcut;

public class MainActivity extends AppCompatActivity  {
    private static final String TAG = "";
    private BottomNavigationView navigationView;
    private FragmentManager fragmentManager ;
    public Toolbar toolbar;

    private View parent_view;
    private View back_drop;
    private boolean rotate = false;
    private LinearLayout lyt_mic;
    private LinearLayout lyt_call;
    private LinearLayout lyt_assessment;
    private LinearLayout lyt_seo;
    private LinearLayout contactfab;
    private ViewPager viewPager;
    MenuItem prevMenuItem;





    Dialog seoDialog;
    Dialog assessmentDialog;

    private MaterialSheetFab materialSheetFab;
    private int statusBarColor;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(getIntent()!=null && getIntent().getExtras()!=null){
            Bundle bundle = getIntent().getExtras();

            String id = bundle.getString("id");
            String notnews = bundle.getString("news");

            if (notnews != null ){
                Intent intent = new Intent(this, NotificationNewsActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                finish();
            }
        }




        DeviceInfo deviceInfo = new DeviceInfo();

        deviceInfo.regid = FirebaseInstanceId.getInstance().getToken();;
        deviceInfo.device_name = getDeviceName();
        deviceInfo.serial = Build.SERIAL;
        deviceInfo.os_version = Tools.getAndroidVersion();

        NotificationClient.getInstance().callRetrofit(this).registerDevice(deviceInfo).enqueue(new Callback<NotStatus>() {
            @Override
            public void onResponse(Call<NotStatus> call, Response<NotStatus> response) {
                Log.d(TAG, "Notification: "+response.body());
            }

            @Override
            public void onFailure(Call<NotStatus> call, Throwable t) {

            }
        });

        seoDialog = new Dialog(this);
        assessmentDialog = new Dialog(this);
        contactfab = (LinearLayout) findViewById(R.id.fab_sheet_item_note);
        lyt_assessment = (LinearLayout) findViewById(R.id.fab_assessment_layout);
        lyt_seo = (LinearLayout) findViewById(R.id.fab_seo_layout);

        final FrameLayout frame = findViewById(R.id.frame);
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
        });




        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
        toolbar.setPadding(0, 0, 0, 0);
        fragmentManager = getSupportFragmentManager();
        Shortbread.create(this);



        setupFab();
        notificationSetup();

        ImageView titleImage = (ImageView) findViewById(R.id.app_bar_logo);
        titleImage.setVisibility(View.VISIBLE);

        
        HomeFragment homeFragment = new HomeFragment();
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction()
                .replace(R.id.frame, homeFragment, "homeFragment").disallowAddToBackStack().commit();


        navigationView = (BottomNavigationView) findViewById(R.id.navigation_view);




        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
        });




        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
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
        });


        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();

    }


    private void changeFragment(Fragment fragment) {
        /*fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);*/
        hideFab();


        fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,
                R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.frame,fragment, "fragment").addToBackStack(null).commit();
       /* fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .replace(R.id.frame,fragment, "fragment").addToBackStack(null).commit();*/


    }
/*


    public void showAssessmentDialog(){
        TextView assesclose;
        final TextInputLayout domainInput;
        Spinner domainSpiner;
        Spinner haveDesign;
        Spinner googleSearch;
        Spinner needHosting;
        Button submitBtn;
        final AppCompatEditText assessnmentName;
        final AppCompatEditText assessnmentPhone;
        final AppCompatEditText assessnmentEmail;
        final AppCompatEditText assessnmentPages;
        final AppCompatEditText assessnmentExistingUrl;
        final AppCompatEditText assessnmentDomainUrl;
        final String[] design = new String[1];
        final String[] interestRank = new String[1];
        final String[] domain = new String[1];
        final String[] hosting = new String[1];

        assessmentDialog.setContentView(R.layout.free_estimation);
        assesclose = (TextView) assessmentDialog.findViewById(R.id.seo_assessment_close);
        domainInput = (TextInputLayout)  assessmentDialog.findViewById(R.id.domain_layout);
        haveDesign = (Spinner) assessmentDialog.findViewById(R.id.design_mind_spinner);
        googleSearch = (Spinner) assessmentDialog.findViewById(R.id.google_search_spinner);
        needHosting = (Spinner) assessmentDialog.findViewById(R.id.hosting_spinner);
        domainSpiner = (Spinner) assessmentDialog.findViewById(R.id.hav_domain_spinner);

        assessnmentName = (AppCompatEditText) assessmentDialog.findViewById(R.id.seo_assessment_name);
        assessnmentPhone = (AppCompatEditText) assessmentDialog.findViewById(R.id.seo_assessment_phone);
        assessnmentEmail = (AppCompatEditText) assessmentDialog.findViewById(R.id.seo_assessment_email);
        assessnmentPages = (AppCompatEditText) assessmentDialog.findViewById(R.id.seo_assessment_pages);
        assessnmentExistingUrl = (AppCompatEditText) assessmentDialog.findViewById(R.id.seo_assessment_existing_url);
        assessnmentDomainUrl = (AppCompatEditText) assessmentDialog.findViewById(R.id.seo_assessment_domain_url);
        submitBtn = (Button) assessmentDialog.findViewById(R.id.seo_assessment_btn);

        domainInput.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> designAdapter = ArrayAdapter.createFromResource(this,
                R.array.design_array, R.layout.spinner_item);
        designAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        haveDesign.setAdapter(designAdapter);
        haveDesign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                design[0] =  parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> googleSearchAdapter = ArrayAdapter.createFromResource(this,
                R.array.rank_array, R.layout.spinner_item);
        googleSearchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        googleSearch.setAdapter(googleSearchAdapter);
        googleSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                interestRank[0] =  parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> needHostingAdapter = ArrayAdapter.createFromResource(this,
                R.array.hosting_array, R.layout.spinner_item);
        needHostingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        needHosting.setAdapter(needHostingAdapter);
        needHosting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hosting[0] =  parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.domain_array, R.layout.spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        domainSpiner.setAdapter(adapter);
        domainSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String p =  parent.getItemAtPosition(position).toString();
                domain[0] = parent.getItemAtPosition(position).toString();
                if (p.equals("Yes")){

                    domainInput.setVisibility(View.VISIBLE);
                }
                if (p.equals("No")){
                    domainInput.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = assessnmentName.getText().toString();
                String phone = assessnmentPhone.getText().toString();
                String email = assessnmentEmail.getText().toString();
                String pages = assessnmentPages.getText().toString();
                String url = assessnmentExistingUrl.getText().toString();
                String domainUrl = assessnmentDomainUrl.getText().toString();


                AssessmentMail assessmentMail = new AssessmentMail(assessmentDialog.getContext(),name,phone,email,pages,design[0],interestRank[0],url,domain[0],hosting[0],domainUrl);
                assessmentMail.execute();
            }
        });


        assesclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assessmentDialog.dismiss();
            }
        });
        assessmentDialog.show();

    }*/


    private void setupFab() {

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
     /*   findViewById(R.id.fab_sheet_item_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Contact", Toast.LENGTH_SHORT).show();

            }
        });*/



    }

    private int getStatusBarColor() {
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

 /*   @Override
    public void onBackPressed() {
        if (materialSheetFab.isSheetVisible()) {
            materialSheetFab.hideSheet();
        } else  {
            super.onBackPressed();
        }


    }*/
    public void hideFab(){
        if (materialSheetFab.isSheetVisible()) {
            materialSheetFab.hideSheet();
        }
    }

    public Fragment currentFragment(){
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.frame);
        return currentFragment;
        // Log.d(TAG, "currentFragment: "+currentFragment().getId());
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        HomeFragment homeFragment = new HomeFragment();
        ServiceFragment serviceFragment = new ServiceFragment();
        TeamFragment teamFragment = new TeamFragment();
        CostFragment costFragment = new CostFragment();
        ContactFragment contactFragment = new ContactFragment();
        adapter.addFragment(homeFragment);
        adapter.addFragment(serviceFragment);
        adapter.addFragment(teamFragment);
        adapter.addFragment(costFragment);
        adapter.addFragment(contactFragment);
        viewPager.setAdapter(adapter);
    }
    @Shortcut(id = "favorite_books", icon = R.mipmap.ic_ln_mail, shortLabel = "Contact Us" ,rank = 1)
    public void showFavoriteBooks() {
        ContactFragment contactFragment = new ContactFragment();
        changeFragment(contactFragment);
    }
    @Shortcut(id = "favorite_seo", icon = R.mipmap.ic_ln_spider, shortLabel = "Free Seo Audit",rank = 2)
    public void showFavoriteSeo() {
        SeoFragment seoFragment = new SeoFragment();
        changeFragment(seoFragment);
    }
    @Shortcut(id = "favorite_assessment", icon = R.mipmap.ic_ln_star, shortLabel = "Free Assessment",rank = 3)
    public void showFavoriteAssessment() {
        AssessmentFragment assessmentFragment = new AssessmentFragment();
        changeFragment(assessmentFragment);
    }
    @Override public void onBackPressed() {
        if (materialSheetFab.isSheetVisible()) {
            materialSheetFab.hideSheet();
        }
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation_view);
        int seletedItemId = bottomNavigationView.getSelectedItemId();
        if (R.id.action_home != seletedItemId) {
            setHomeItem(MainActivity.this);
        } else {
            super.onBackPressed();
        }
    }

    public static void setHomeItem(Activity activity) {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                activity.findViewById(R.id.navigation_view);
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }

    private void notificationSetup(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("999","Default", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel channel1 = new NotificationChannel("111","News", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel channel2 = new NotificationChannel("222","Others", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Successful";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }

                        /*Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();*/
                    }
                });
    }

   /* public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
        else return false;
        } else
        return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }*/

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }
    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }



}