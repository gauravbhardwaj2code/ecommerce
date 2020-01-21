package com.gaurav.commerce.activities.videoutils;

import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayer;

public class VideoUtils {



    public static void displayCurrentTime(YouTubePlayer mPlayer, TextView mPlayTimeTextView) {
        if (null == mPlayer) return;
        String formattedTime = formatTime(mPlayer.getDurationMillis() - mPlayer.getCurrentTimeMillis());
        mPlayTimeTextView.setText(formattedTime);
    }

    private static String formatTime(int millis) {
        int seconds = millis / 1000;
        int minutes = seconds / 60;
        int hours = minutes / 60;

        return (hours == 0 ? "--:" : hours + ":") + String.format("%02d:%02d", minutes % 60, seconds % 60);
    }

}
