package com.cansoft.app.activity;

import android.content.Intent;
import android.os.Bundle;

import com.cansoft.app.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeStandalonePlayer;


public class YouActivity extends YouTubeBaseActivity {
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you);
        Intent intent = YouTubeStandalonePlayer.createVideoIntent(this, "AIzaSyBzXkD1FD42ZqOwi_BMhhgN27j2WNYt9X4", "5xVh-7ywKpE",0,false,true);
        startActivity(intent);

/*
      youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.player);

        youTubePlayerView.initialize("AIzaSyBzXkD1FD42ZqOwi_BMhhgN27j2WNYt9X4",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        // do any work here to cue video, play video, etc.
                        youTubePlayer.cueVideo("5xVh-7ywKpE");
                        youTubePlayer.setFullscreen(true);


                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                    }
                });
        youTubePlayerView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {

            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}
