package com.gaurav.commerce.activities.videoutils;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayer;

public class AppPlayBackEventListner implements YouTubePlayer.PlaybackEventListener, YouTubePlayer.PlayerStateChangeListener {

    private Handler mHandler;
    private TextView mPlayTimeTextView;
    private YouTubePlayer mPlayer;
    View mPlayButtonLayout;


    public AppPlayBackEventListner(Handler mHandler, TextView mPlayTimeTextView, YouTubePlayer mPlayer, View mPlayButtonLayout) {
        this.mHandler=mHandler;
        this.mPlayTimeTextView=mPlayTimeTextView;
        this.mPlayer=mPlayer;
        this.mPlayButtonLayout=mPlayButtonLayout;
    }

    @Override
    public void onBuffering(boolean arg0) {
    }

    @Override
    public void onPaused() {
        mHandler.removeCallbacks(runnable);
    }

    @Override
    public void onPlaying() {
        mHandler.postDelayed(runnable, 100);
        VideoUtils.displayCurrentTime(mPlayer,mPlayTimeTextView);
       // mPlayButtonLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSeekTo(int arg0) {
        mHandler.postDelayed(runnable, 100);
    }

    @Override
    public void onStopped() {
        mHandler.removeCallbacks(runnable);
    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            VideoUtils.displayCurrentTime(mPlayer,mPlayTimeTextView);
            mHandler.postDelayed(this, 100);
        }
    };

    @Override
    public void onAdStarted() {
    }

    @Override
    public void onError(YouTubePlayer.ErrorReason arg0) {
    }

    @Override
    public void onLoaded(String arg0) {
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void onVideoEnded() {
    }

    @Override
    public void onVideoStarted() {
        VideoUtils.displayCurrentTime(mPlayer,mPlayTimeTextView);
    }
}
