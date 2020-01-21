package com.gaurav.commerce.activities;

import android.os.Bundle;

import com.gaurav.commerce.activities.lectures.view.LecturesViewHandler;
import com.gaurav.commerce.activities.videoutils.AppPlayBackEventListner;
import com.gaurav.commerce.activities.videoutils.VideoUtils;
import com.gaurav.commerce.networksync.CheckInternetConnection;


import androidx.appcompat.widget.Toolbar;


import android.os.Handler;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurav.commerce.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.FirebaseDatabase;


public class VideoLanding extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, View.OnClickListener{


    public static final String VIDEO_ID = "-m3V8w_7vhk";
    private YouTubePlayer mPlayer;

    private View mPlayButtonLayout;
    private TextView mPlayTimeTextView;

    private Handler mHandler = null;
    private SeekBar mSeekBar;


    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_landing);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        new CheckInternetConnection(this).checkConnection();

        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.player);

        youTubePlayerView.initialize("AIzaSyBpXaw1BwRWfv4jYuSe8DWbpLV9bTLiU8w", this);

        //Add play button to explicitly play video in YouTubePlayerView
        mPlayButtonLayout = findViewById(R.id.video_control);
        findViewById(R.id.play_video).setOnClickListener(this);
        findViewById(R.id.pause_video).setOnClickListener(this);

        mPlayTimeTextView = (TextView) findViewById(R.id.play_time);
        mSeekBar = (SeekBar) findViewById(R.id.video_seekbar);
        mSeekBar.setOnSeekBarChangeListener(mVideoSeekBarChangeListener);
        mHandler = new Handler();



        TabHost tabs = (TabHost) findViewById(R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("tag1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Lectures");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Resources");
        tabs.addTab(spec);


       fetchLectures();

    }

    private void fetchLectures() {
        new LecturesViewHandler(findViewById(R.id.my_recycler_view),database);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Failed to initialize.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (null == player) return;
        mPlayer = player;

        VideoUtils.displayCurrentTime(mPlayer,mPlayTimeTextView);

        // Start buffering
        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
        }

        player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        mPlayButtonLayout.setVisibility(View.VISIBLE);

        // Add listeners to YouTubePlayer instance
        AppPlayBackEventListner appPlayBackEventListner=new AppPlayBackEventListner(mHandler,mPlayTimeTextView,mPlayer,mPlayButtonLayout);
        player.setPlayerStateChangeListener(appPlayBackEventListner);
        player.setPlaybackEventListener(appPlayBackEventListner);
    }


    SeekBar.OnSeekBarChangeListener mVideoSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            long lengthPlayed = (mPlayer.getDurationMillis() * progress) / 100;
            mPlayer.seekToMillis((int) lengthPlayed);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_video:
                if (null != mPlayer && !mPlayer.isPlaying())
                    mPlayer.play();
                break;
            case R.id.pause_video:
                if (null != mPlayer && mPlayer.isPlaying())
                    mPlayer.pause();
                break;
        }
    }

}



