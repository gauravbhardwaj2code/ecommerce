package com.gaurav.commerce.activities.course.dto;

import com.google.android.youtube.player.YouTubePlayer;

public class AllPlayersWrapper implements  PlayersWrapper{

    private YouTubePlayer mPlayer;


    public AllPlayersWrapper(){

    }

    @Override
    public void initialise(YouTubePlayer mPlayer) {
        this.mPlayer=mPlayer;
    }

    @Override
    public void play(String s) {
       // mPlayer.release();
        mPlayer.loadVideo(s);
    }
}
