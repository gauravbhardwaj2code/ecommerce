package com.gaurav.commerce.routehandler.security;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.gaurav.commerce.LoginActivity;
import com.gaurav.commerce.MainActivity;
import com.gaurav.commerce.WelcomeActivity;
import com.gaurav.commerce.activities.HomePageWithBottomNavigation;
import com.gaurav.commerce.activities.MobileNoWithOtp;
import com.gaurav.commerce.usersession.UserSession;

import static com.gaurav.commerce.usersession.UserSession.KEY_MOBiLE;

public class RouteHandler {


    public void sendToMainActivity(UserSession session){

    }

    public static void launchHomeScreen(AppCompatActivity compatActivity) {
        UserSession userSession=new UserSession(compatActivity);
        userSession.setFirstTimeLaunch(false);
        if(userSession.isLoggedIn() && userSession.getUserDetails().get(KEY_MOBiLE)==null){
            Intent i = new Intent(compatActivity, MobileNoWithOtp.class);
            compatActivity.startActivity(i);
            return;
        }
        else if(userSession.isLoggedIn()) {
            Intent i = new Intent(compatActivity, HomePageWithBottomNavigation.class);
            compatActivity.startActivity(i);
        }else{
            Intent i = new Intent(compatActivity, LoginActivity.class);
            compatActivity.startActivity(i);
        }
        compatActivity.finish();
    }
}
