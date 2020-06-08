package com.lms.exam.activities.course.dto;

import com.google.android.youtube.player.YouTubePlayer;

public class AllPlayersWrapper implements PlayersWrapper {

    private YouTubePlayer mPlayer;

    private String currentVideoId;

    public AllPlayersWrapper() {

    }

    @Override
    public void initialise(YouTubePlayer mPlayer) {
        this.mPlayer = mPlayer;
    }

    @Override
    public void play(String s) {
        // mPlayer.release();
        this.currentVideoId=s;
        mPlayer.loadVideo(s);
    }

    public String getCurrentVideoId() {
        return currentVideoId;
    }

}
