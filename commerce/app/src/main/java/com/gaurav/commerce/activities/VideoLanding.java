package com.gaurav.commerce.activities;

import android.content.res.Configuration;
import android.os.Bundle;

import com.gaurav.commerce.activities.lectures.view.LecturesViewHandler;
import com.gaurav.commerce.activities.videoutils.AppPlayBackEventListner;
import com.gaurav.commerce.activities.videoutils.VideoUtils;
import com.gaurav.commerce.networksync.CheckInternetConnection;


import androidx.appcompat.app.AppCompatActivity;
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



public class VideoLanding extends AppCompatActivity {


    FirebaseDatabase database = FirebaseDatabase.getInstance();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_landing);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        new CheckInternetConnection(this).checkConnection();



        /*FullscreenVideoView fullscreenVideoView = findViewById(R.id.fullscreenVideoView);
        String videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
        fullscreenVideoView.videoUrl(videoUrl).enableAutoStart();
        //fullscreenVideoView*/


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

        expandableLectures(this);

    }

    private void expandableLectures(VideoLanding videoLanding) {

        new ExpandableLectures(videoLanding);

    }


    private void fetchLectures() {
        new LecturesViewHandler(findViewById(R.id.my_recycler_view),database);
    }



}



