package com.cansoft.app.fragments;


import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cansoft.app.R;
import com.cansoft.app.activity.MainActivity;
import com.cansoft.app.adapter.ClientListAdapter;
import com.cansoft.app.adapter.HomeNewsAdapter;
import com.cansoft.app.model.About;
import com.cansoft.app.model.AboutD;
import com.cansoft.app.model.Client;
import com.cansoft.app.model.ClientD;
import com.cansoft.app.model.Post;
import com.cansoft.app.model.Video;
import com.cansoft.app.model.VideoD;
import com.cansoft.app.network.RestClient;
import com.cansoft.app.network.RestClient2;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "";
    private static final String API_KEY = "AIzaSyBzXkD1FD42ZqOwi_BMhhgN27j2WNYt9X4";
    RecyclerView recentRecycler, clientsRecycler;
    LinearLayoutManager manager;
    LinearLayoutManager clientManager;
    ImageButton youtubeBtn;
    ImageView youtubeImage;
    RelativeLayout testimoniaLayout;
    private TextView aboutUsView;

    String youtubeId;

    HomeNewsAdapter adapter;
    ClientListAdapter clientListAdapter;
    TextView newsTitle;
    ProgressBar progressBar;
    MainActivity activity;
    private ProgressBar homeProgress;
    private boolean loadStatus = true;
    private ProgressBar homeAboutProgress;

    public HomeFragment() {
        // Required empty public constructor
    }



    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        newsTitle = (TextView) view.findViewById(R.id.news_title);

        progressBar = (ProgressBar) view.findViewById(R.id.home_loadmore_progress);
        progressBar.setVisibility(View.VISIBLE);
        showBackButtonStatus(false);

        youtubeBtn=(ImageButton) view.findViewById(R.id.youtubeBtn);
        youtubeImage=(ImageView) view.findViewById(R.id.youtube_image);
        testimoniaLayout = (RelativeLayout) view.findViewById(R.id.testimonial_layout);
        aboutUsView = (TextView) view.findViewById(R.id.home_about_view);
     /*   homeProgress = (ProgressBar) view.findViewById(R.id.homeProgress);*/
        homeAboutProgress = (ProgressBar) view.findViewById(R.id.homeAboutProgress);
        homeAboutProgress.setVisibility(View.VISIBLE);

        makeTransperantStatusBar(false);
        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.app_bar);
        toolbar.setVisibility(View.VISIBLE);


        RestClient2.getInstance().callRetrofit(view.getContext()).getVideo().enqueue(new Callback<VideoD>() {
            @Override
            public void onResponse(Call<VideoD> call, Response<VideoD> response) {
                if (response.body() == null) {
                    showAlertDialog(view);
                } else {
                    List<Video> videos = response.body().getData();
                    loadStatus = false;
                    int length = videos.size();
                    Random rnd = new Random();
                    int n = rnd.nextInt(length);
                    String you = getYoutubeId(videos.get(n).getLink());
                    youtubeId = android.text.Html.fromHtml(you).toString();

                    Picasso.get().load("https://img.youtube.com/vi/" + youtubeId + "/mqdefault.jpg").into(youtubeImage);
                    Log.d(TAG, "onResponse: " + youtubeId);
                    testimoniaLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String videoId = youtubeId;
                            if (isAppInstalled("com.google.android.youtube")) {
                                watchYoutubeVideo(videoId);
                            } else {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + videoId)));
                            }
                        }
                    });

                    youtubeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String videoId = youtubeId;
                            if (isAppInstalled("com.google.android.youtube")) {
                                watchYoutubeVideo(videoId);
                            } else {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + videoId)));
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<VideoD> call, Throwable t) {

            }
        });

        RestClient2.getInstance().callRetrofit(view.getContext()).getAbout().enqueue(new Callback<AboutD>() {
            @Override
            public void onResponse(Call<AboutD> call, Response<AboutD> response) {
                if (response.body() == null) {
                    showAlertDialog(view);
                } else {
                    List<About> about = response.body().getData();
                    aboutUsView.setText(about.get(0).getExcerpt());
                    aboutUsView.setVisibility(View.VISIBLE);
                    homeAboutProgress.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<AboutD> call, Throwable t) {

            }
        });

        aboutUsView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AboutFragment aboutFragment = new AboutFragment();
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_up, R.anim.slide_down,R.anim.fade_in,R.anim.slide_down
                       )
                        .replace(R.id.frame,aboutFragment, "fragment").addToBackStack(null).commit();
            }
        });




        newsTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsFragment newsFragment = new NewsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, newsFragment);
                transaction.addToBackStack(null);
                transaction.commit();


            }
        });

        clientsRecycler = (RecyclerView) view.findViewById(R.id.clients_recycler);
        clientManager = new LinearLayoutManager(view.getContext());


        recentRecycler =(RecyclerView) view.findViewById(R.id.home_news_recycler);
        manager = new LinearLayoutManager(view.getContext());
        updateView(view);
       /* initializeYoutubePlayer();*/
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                view.getWindowVisibleDisplayFrame(r);
                int screenHeight = view.getRootView().getHeight();

                // r.bottom is the position above soft keypad or device button.
                // if keypad is shown, the r.bottom is smaller than that before.
                int keypadHeight = screenHeight - r.bottom;

                Log.d(TAG, "keypadHeight = " + keypadHeight);

                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    // keyboard is opened
                    Log.d(TAG, "onGlobalLayout: keyboard is on");
                }
                else {
                    // keyboard is closed
                }
            }
        });
        return view;
    }

    private void updateView(final View view){
        RestClient.getInstance().callRetrofit(view.getContext()).getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                Log.d(TAG, "onResponse: " + response.body());
                if (response.body() == null){
                    showAlertDialog(view);
                }else{
                List<Post> posts = response.body();
                adapter = new HomeNewsAdapter(view.getContext(),posts);
                manager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);
                recentRecycler.setLayoutManager(manager);
                recentRecycler.setItemAnimator(new DefaultItemAnimator());
                recentRecycler.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);}
                loadStatus = false;

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });

        RestClient2.getInstance().callRetrofit(view.getContext()).getClients().enqueue(new Callback<ClientD>() {
            @Override
            public void onResponse(Call<ClientD> call, Response<ClientD> response) {
                if (response.body() == null){
                    showAlertDialog(view);
                }else{
                    List<Client> clients = response.body().getData();
/*
                    Collections.shuffle(clients);
*/
                    clientListAdapter = new ClientListAdapter(view.getContext(),clients);
                    clientManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);
                    clientsRecycler.setLayoutManager(clientManager);
                    clientsRecycler.setItemAnimator(new DefaultItemAnimator());
                    clientsRecycler.setAdapter(clientListAdapter);}
                    loadStatus = false;
            }

            @Override
            public void onFailure(Call<ClientD> call, Throwable t) {

            }
        });





    }
    protected void makeTransperantStatusBar(boolean isTransperant) {
        if (isTransperant) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    private String getYoutubeId(String url){
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url); //url is youtube url for which you want to extract the id.
        if (matcher.find()) {
            return matcher.group();
        }
        return pattern;
    }

    public void watchYoutubeVideo(String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
    protected boolean isAppInstalled(String packageName) {
        Intent mIntent = getActivity().getPackageManager().getLaunchIntentForPackage(packageName);
        if (mIntent != null) {
            return true;
        }
        else {
            return false;
        }
    }
    private void showBackButtonStatus(Boolean status){
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(status);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(status);
    }
    @Override
    public void onResume() {
        super.onResume();

        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });
    }
    private void showAlertDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setCancelable(false);
        builder.setTitle("NO Internet!");
        builder.setMessage("Please connect to the internet for the first time");
        builder.setPositiveButton("BACK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().finish();
            }
        });

        builder.show();
    }



}
