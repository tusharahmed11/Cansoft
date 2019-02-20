package com.cansoft.cansoft.cansoft.fragments;


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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cansoft.cansoft.cansoft.R;
import com.cansoft.cansoft.cansoft.activity.MainActivity;
import com.cansoft.cansoft.cansoft.activity.YouActivity;
import com.cansoft.cansoft.cansoft.adapter.ClientListAdapter;
import com.cansoft.cansoft.cansoft.adapter.HomeNewsAdapter;
import com.cansoft.cansoft.cansoft.model.Client;
import com.cansoft.cansoft.cansoft.model.Post;
import com.cansoft.cansoft.cansoft.model.Video;
import com.cansoft.cansoft.cansoft.network.RestClient;
import com.cansoft.cansoft.cansoft.util.IOnBackPressed;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.squareup.picasso.Picasso;

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

        makeTransperantStatusBar(false);
        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.app_bar);
        toolbar.setVisibility(View.VISIBLE);

        RestClient.getInstance().callRetrofit(view.getContext()).getVideo().enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                if (response.body() == null){
                    showAlertDialog(view);
                }else{
                    List<Video> videos = response.body();
                    int length = videos.size();
                    Random rnd = new Random();
                    int n = rnd.nextInt(length);
                    String you = getYoutubeId(videos.get(n).getExcerpt().getRendered());
                    youtubeId = android.text.Html.fromHtml(you).toString();

                    Picasso.get().load("https://img.youtube.com/vi/"+youtubeId+"/mqdefault.jpg" ).into(youtubeImage);
                    Log.d(TAG, "onResponse: "+youtubeId);
                    testimoniaLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String videoId = youtubeId;
                            if (isAppInstalled("com.google.android.youtube")){
                                watchYoutubeVideo(videoId);
                            }else {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + videoId)));
                            }
                        }
                    });

                    youtubeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String videoId = youtubeId;
                            if (isAppInstalled("com.google.android.youtube")){
                                watchYoutubeVideo(videoId);
                            }else {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + videoId)));
                            }
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {

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



/*
        final YouTubePlayerFragment youtubeFragment = (YouTubePlayerFragment)
                getActivity().getFragmentManager().findFragmentById(R.id.youtubeFragment);
        youtubeFragment.initialize(API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        final YouTubePlayer youTubePlayer, boolean b) {
                        // do any work here to cue video, play video, etc.
                        youTubePlayer.cueVideo(youtubeId);
                        youTubePlayer.setFullscreenControlFlags(0);

                        youTubePlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                            @Override
                            public void onFullscreen(boolean b) {
                                if (b){
                                    String videoId = "Fee5vbFLYM4";
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+videoId));
                                    intent.putExtra("VIDEO_ID", videoId);
                                    startActivity(intent);

                                    *//*Intent intent = YouTubeStandalonePlayer.createVideoIntent(getActivity(), "AIzaSyBzXkD1FD42ZqOwi_BMhhgN27j2WNYt9X4", youtubeId,0,false,true);
                                    startActivity(intent);*//*
                                   *//* Intent intent = new Intent(getActivity(),YouActivity.class);
                                    startActivity(intent);*//*
                                }


                            }
                        });
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });*/

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
                List<Post> posts = response.body();
                adapter = new HomeNewsAdapter(view.getContext(),posts);
                manager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);
                recentRecycler.setLayoutManager(manager);
                recentRecycler.setItemAnimator(new DefaultItemAnimator());
                recentRecycler.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });

        RestClient.getInstance().callRetrofit(view.getContext()).getClients().enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                List<Client> clients = response.body();
                clientListAdapter = new ClientListAdapter(view.getContext(),clients);
                clientManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);
                clientsRecycler.setLayoutManager(clientManager);
                clientsRecycler.setItemAnimator(new DefaultItemAnimator());
                clientsRecycler.setAdapter(clientListAdapter);
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {

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
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                HomeFragment homeFragment = new HomeFragment();
                ft.replace(R.id.frame, homeFragment).addToBackStack(null);
                ft.commit();
            }
        });

        builder.show();
    }

}
