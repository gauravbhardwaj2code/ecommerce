package com.gaurav.commerce.activities.course.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.gaurav.commerce.activities.course.dto.AllPlayersWrapper;
import com.gaurav.commerce.activities.course.dto.DtoLectureContents;
import com.gaurav.commerce.activities.course.dto.DtoSubjectInfo;
import com.gaurav.commerce.activities.videoutils.AppPlayBackEventListner;
import com.gaurav.commerce.activities.videoutils.VideoUtils;
import com.gaurav.commerce.fragments.Curriculam;
import com.gaurav.commerce.fragments.CurriculamRecycleViewHandler;
import com.gaurav.commerce.fragments.LectureList;
import com.gaurav.commerce.usersession.UserSession;
import com.gaurav.commerce.usersession.UserSubjectInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurav.commerce.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


import bg.devlabs.fullscreenvideoview.FullscreenVideoView;
import bg.devlabs.fullscreenvideoview.playbackspeed.PlaybackSpeedOptions;

public class CourseDetail extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, View.OnClickListener, TabLayout.OnTabSelectedListener,  Curriculam.OnFragmentInteractionListener,LectureList.OnFragmentInteractionListener {


    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    private FullscreenVideoView fullscreenVideoView;


    private YouTubePlayer mPlayer;

    private View mPlayButtonLayout;
    private TextView mPlayTimeTextView;

    private Handler mHandler = null;
    private SeekBar mSeekBar;
    private UserSession userSession;

    private DtoSubjectInfo subjectInfo;

    private AllPlayersWrapper playersWrapper=new AllPlayersWrapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        this.userSession=new UserSession(getBaseContext());
       // setSupportActionBar(toolbar);

        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }

        Intent intent=getIntent();
         this.subjectInfo= (DtoSubjectInfo) intent.getExtras().get("sId");

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        TextView textView=findViewById(R.id.subjectName);
        textView.setText(subjectInfo.getName());

        textView=findViewById(R.id.teacher_name);
        textView.setText(subjectInfo.getFacultyId()+"");

       /* tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Lectures"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);
*/

       /* this.fullscreenVideoView = findViewById(R.id.fullscreenVideoView);
        String videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
        ArrayList<Float> speeds=new ArrayList();
        speeds.add(1.25f);
        speeds.add(1.5f);
        speeds.add(1.75f);
        speeds.add(2.00f);
        speeds.add(1f);
        PlaybackSpeedOptions playbackOptions = new PlaybackSpeedOptions().addSpeeds(speeds);
        //Creating our pager adapter
       // Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount(),viewPager,subjectInfo,fullscreenVideoView);

        //Adding adapter to pager
        //viewPager.setAdapter(adapter);

        tabLayout.setOnTabSelectedListener(this);



        fullscreenVideoView.videoUrl(videoUrl)
                .addSeekBackwardButton()
                .addPlaybackSpeedButton()
                .addSeekForwardButton()
                .fastForwardSeconds(5)
                .rewindSeconds(5)
                .enableAutoStart()
                .thumbnail(R.drawable.code_img)
                .playbackSpeedOptions(playbackOptions);
*/

        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.player);

        youTubePlayerView.initialize("AIzaSyBpXaw1BwRWfv4jYuSe8DWbpLV9bTLiU8w", this);

        //Add play button to explicitly play video in YouTubePlayerView
        mPlayButtonLayout = findViewById(R.id.video_control);
        findViewById(R.id.play_video).setOnClickListener(this);
        findViewById(R.id.pause_video).setOnClickListener(this);
        findViewById(R.id.fullscreen_button).setOnClickListener(this);

        mPlayTimeTextView = (TextView) findViewById(R.id.play_time);
        mSeekBar = (SeekBar) findViewById(R.id.video_seekbar);
        mSeekBar.setOnSeekBarChangeListener(mVideoSeekBarChangeListener);
        mHandler = new Handler();

        LayoutInflater li = LayoutInflater.from(getBaseContext());

        new CurriculamRecycleViewHandler(findViewById(R.id.lectures_list),R.layout.recycleview_curriculam,
                subjectInfo.getLectures(),li,true,fullscreenVideoView,playersWrapper,subjectInfo.getId());

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
            case R.id.fullscreen_button:
                if (null != mPlayer)
                    mPlayer.setFullscreen(true);
                break;

        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {

        if (null == player) return;
        mPlayer = player;
        playersWrapper.initialise(mPlayer);


        VideoUtils.displayCurrentTime(mPlayer,mPlayTimeTextView);

        // Start buffering
        if (!wasRestored) {
            player.cueVideo(subjectInfo.getLectures().get(0).getLectureContents().get(0).getUrl());
            /*if(subjectInfo!=null && subjectInfo.getLectures()!=null
            && subjectInfo.getLectures().size()>0
            && subjectInfo.getLectures().get(0).getLectureContents()!=null && subjectInfo.getLectures().get(0).getLectureContents().size()>0){
                UserSubjectInfo userSubjectInfo=userSession.getUserSubjectInfo();
                if(userSubjectInfo!=null &&
                        userSubjectInfo.getSubjectById(subjectInfo.getId())!=null
                && userSubjectInfo.getSubjectById(subjectInfo.getId()).getCurrentLectureContent()!=null){
                    for(DtoLectureContents lectureContents: subjectInfo.getLectures().get(subjectInfo.getId()).getLectureContents()){
                        if(lectureContents.getTitle().equalsIgnoreCase(userSubjectInfo.getSubjectById(subjectInfo.getId()).getCurrentLectureContent())){
                            player.cueVideo(lectureContents.getUrl());
                        }
                    }
                }else{
                    player.cueVideo(subjectInfo.getLectures().get(0).getLectureContents().get(0).getUrl());
                }


            }*/

        }

        player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
        mPlayButtonLayout.setVisibility(View.VISIBLE);

        // Add listeners to CustomYouTubePlayer instance
        AppPlayBackEventListner appPlayBackEventListner=new AppPlayBackEventListner(mHandler,mPlayTimeTextView,mPlayer,mPlayButtonLayout);
        player.setPlayerStateChangeListener(appPlayBackEventListner);
        player.setPlaybackEventListener(appPlayBackEventListner);

    }

    SeekBar.OnSeekBarChangeListener mVideoSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            long lengthPlayed = ((mPlayer.getDurationMillis() -15000) * progress) / 100;
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
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Failed to initialize.", Toast.LENGTH_LONG).show();

    }
}

class Pager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;
    ViewPager viewPager;
    DtoSubjectInfo subjectInfo;
    private FullscreenVideoView fullscreenVideoView;

    //Constructor to the class


    public Pager(FragmentManager fm, int tabCount, ViewPager viewPager,DtoSubjectInfo subjectInfo,FullscreenVideoView fullscreenVideoView) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
        this.viewPager=viewPager;
        this.subjectInfo=subjectInfo;
        this.fullscreenVideoView=fullscreenVideoView;

    }


    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                viewPager.setCurrentItem(0);
                return  Curriculam.newInstance(subjectInfo,true,fullscreenVideoView);
                //return new LectureList();
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
