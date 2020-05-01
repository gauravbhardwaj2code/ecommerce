package com.lms.exam.routehandler.security;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.lms.exam.LoginActivity;
import com.lms.exam.activities.HomePageWithBottomNavigation;
import com.lms.exam.activities.MobileNoWithOtp;
import com.lms.exam.usersession.UserSession;

import static com.lms.exam.usersession.UserSession.KEY_MOBiLE;

public class RouteHandler {


    public static void launchHomeScreen(AppCompatActivity compatActivity) {
        UserSession userSession = new UserSession(compatActivity);
        userSession.setFirstTimeLaunch(false);
        if (userSession.isLoggedIn() && userSession.getUserDetails().get(KEY_MOBiLE) == null) {
            Intent i = new Intent(compatActivity, MobileNoWithOtp.class);
            compatActivity.startActivity(i);
            return;
        } else if (userSession.isLoggedIn()) {
            Intent i = new Intent(compatActivity, HomePageWithBottomNavigation.class);
            compatActivity.startActivity(i);
        } else {
            Intent i = new Intent(compatActivity, LoginActivity.class);
            compatActivity.startActivity(i);
        }
        compatActivity.finish();
    }

    public void sendToMainActivity(UserSession session) {

    }
}
