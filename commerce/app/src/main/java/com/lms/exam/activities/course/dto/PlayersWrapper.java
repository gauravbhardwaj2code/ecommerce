package com.lms.exam.activities.course.dto;

import com.google.android.youtube.player.YouTubePlayer;

public interface PlayersWrapper {

    void initialise(YouTubePlayer mPlayer);

    void play(String s);
}
