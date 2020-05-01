package com.lms.exam.activities;

import android.os.Bundle;
import android.widget.TabHost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.FirebaseDatabase;
import com.lms.exam.R;
import com.lms.exam.activities.lectures.view.LecturesViewHandler;
import com.lms.exam.networksync.CheckInternetConnection;


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
        new LecturesViewHandler(findViewById(R.id.my_recycler_view), database);
    }


}



