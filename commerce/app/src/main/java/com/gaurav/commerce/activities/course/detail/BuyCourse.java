package com.gaurav.commerce.activities.course.detail;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;

import com.gaurav.commerce.activities.course.dto.AllPlayersWrapper;
import com.gaurav.commerce.activities.course.dto.DtoSubjectInfo;
import com.gaurav.commerce.fragments.AbountCourse;
import com.gaurav.commerce.fragments.Curriculam;
import com.gaurav.commerce.fragments.LectureList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.TextView;

import com.gaurav.commerce.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX;

import java.util.ArrayList;

import bg.devlabs.fullscreenvideoview.FullscreenVideoView;
import bg.devlabs.fullscreenvideoview.playbackspeed.PlaybackSpeedOptions;

public class BuyCourse extends AppCompatActivity implements TabLayout.OnTabSelectedListener,Curriculam.OnFragmentInteractionListener, AbountCourse.OnFragmentInteractionListener{

    private TabLayout tabLayout;

    private ViewPager viewPager;

    private YouTubePlayerSupportFragmentX youTubePlayerFragment;

    private AllPlayersWrapper playersWrapper=new AllPlayersWrapper();
    //youtube player to play video when new video selected
    private YouTubePlayer youTubePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_course);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent=getIntent();
        DtoSubjectInfo subjectInfo= (DtoSubjectInfo) intent.getExtras().get("sId");

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        TextView textView=findViewById(R.id.subjectName);
        textView.setText(subjectInfo.getName());

        textView=findViewById(R.id.teacher_name);
        textView.setText(subjectInfo.getFacultyId()+"");

        textView=findViewById(R.id.cost_price);
        textView.setText("₹"+subjectInfo.getCostPrice()+"");
        textView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        textView=findViewById(R.id.selling_price);
        textView.setText("₹"+subjectInfo.getSellingPrice()+"");
        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Lectures"),true);
        tabLayout.addTab(tabLayout.newTab().setText("More"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
        BuyCoursePager adapter = new BuyCoursePager(getSupportFragmentManager(), tabLayout.getTabCount(),viewPager,subjectInfo,
                playersWrapper);

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        tabLayout.setOnTabSelectedListener(this);

        initializeYoutubePlayer();

    }

    private void initializeYoutubePlayer() {

        youTubePlayerFragment = (YouTubePlayerSupportFragmentX) getSupportFragmentManager()
                .findFragmentById(R.id.youtube_player_fragment);



        if (youTubePlayerFragment == null)
            return;

        youTubePlayerFragment.initialize("AIzaSyBpXaw1BwRWfv4jYuSe8DWbpLV9bTLiU8w", new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                                boolean wasRestored) {
                if (!wasRestored) {
                    youTubePlayer = player;

                    //set the player style default
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);

                    //cue the 1st video by default
                    youTubePlayer.cueVideo("zGNQQfEjCQY");
                    playersWrapper.initialise(youTubePlayer);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {

                //print or show error if initialization failed
            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}

class BuyCoursePager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;
    ViewPager viewPager;

    private DtoSubjectInfo subjectInfo;
    private AllPlayersWrapper youTubePlayer;
    //Constructor to the class


    public BuyCoursePager(FragmentManager fm, int tabCount, ViewPager viewPager,DtoSubjectInfo subjectInfo,AllPlayersWrapper youTubePlayer) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
        this.viewPager=viewPager;
        this.subjectInfo=subjectInfo;
        this.youTubePlayer=youTubePlayer;

    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                //viewPager.setCurrentItem(0);
            return  Curriculam.newInstance(subjectInfo,false,null,youTubePlayer);
            case 1:
                //viewPager.setCurrentItem(1);
                return AbountCourse.newInstance(subjectInfo,"");
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
