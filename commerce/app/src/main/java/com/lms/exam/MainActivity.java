package com.lms.exam;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.lms.exam.activities.HomePageWithBottomNavigation;
import com.lms.exam.activities.VideoLanding;
import com.lms.exam.networksync.CheckInternetConnection;
import com.lms.exam.prodcutscategory.Bags;
import com.lms.exam.prodcutscategory.Cards;
import com.lms.exam.prodcutscategory.Tshirts;
import com.lms.exam.usersession.UserSession;
import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private SliderLayout sliderShow;
    private SliderLayout sliderShow1;
    private Drawer result;


    //to get user session data
    private UserSession session;
    private HashMap<String, String> user;
    private String name, email, photo, mobile;
    private String first_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface typeface = ResourcesCompat.getFont(this, R.font.blacklist);
        TextView appname = findViewById(R.id.appname);
        appname.setTypeface(typeface);

        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        //retrieve session values and display on listviews
        getValues();

        //Navigation Drawer with toolbar

        //ImageSLider
        inflateImageSlider();

        MediaController vidControl = new MediaController(this);
        VideoView vidView = (VideoView) findViewById(R.id.myVideo);
        String vidAddress = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
        Uri vidUri = Uri.parse(vidAddress);
        vidView.setVideoURI(vidUri);
        vidControl.setAnchorView(vidView);
        vidView.setMediaController(vidControl);
        vidView.start();

        if (session.getFirstTime()) {
            //tap target view
            session.setFirstTime(false);
        }
    }


    private void getValues() {

        //create new session object by passing application context
        session = new UserSession(getApplicationContext());

        //validating session
        session.isLoggedIn();

        //get User details if logged in
        user = session.getUserDetails();

        name = user.get(UserSession.KEY_NAME);
        email = user.get(UserSession.KEY_EMAIL);
        mobile = user.get(UserSession.KEY_MOBiLE);
        photo = user.get(UserSession.KEY_PHOTO);
    }


    private void inflateImageSlider() {

        // Using Image Slider -----------------------------------------------------------------------
        sliderShow = findViewById(R.id.slider);

        //populating Image slider
        ArrayList<String> sliderImages = new ArrayList<>();
        sliderImages.add("https://www.examonline.org/estatic/homeimages/cma-final-b.webp");
        sliderImages.add("https://www.examonline.org/estatic/homeimages/cfr.webp");
        sliderImages.add("https://www.examonline.org/estatic/homeimages/mcq.webp");
        sliderImages.add("https://www.examonline.org/estatic/homeimages/blue-cma-inter.webp");

        for (String s : sliderImages) {
            DefaultSliderView sliderView = new DefaultSliderView(this);
            sliderView.image(s);
            sliderShow.addSlider(sliderView);
        }

        sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);


        sliderShow1 = findViewById(R.id.slider2);

        for (String s : sliderImages) {
            DefaultSliderView sliderView = new DefaultSliderView(this);
            sliderView.image(s);
            sliderShow1.addSlider(sliderView);
        }

        sliderShow1.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);

    }


    @Override
    protected void onStop() {
        sliderShow.stopAutoCycle();
        sliderShow1.startAutoCycle();
        super.onStop();

    }

    @Override
    public void onBackPressed() {
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    public void viewProfile(View view) {
        startActivity(new Intent(MainActivity.this, Profile.class));
    }

    public void viewCart(View view) {
        startActivity(new Intent(MainActivity.this, Cart.class));
    }

    @Override
    protected void onResume() {

        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();
        sliderShow.startAutoCycle();
        sliderShow1.startAutoCycle();
        super.onResume();
    }

    public void Notifications(View view) {
        startActivity(new Intent(MainActivity.this, NotificationActivity.class));
    }

    public void cardsActivity(View view) {
        startActivity(new Intent(MainActivity.this, Cards.class));
    }

    public void tshirtActivity(View view) {
        startActivity(new Intent(MainActivity.this, Tshirts.class));
    }


    public void bagsActivity(View view) {

        startActivity(new Intent(MainActivity.this, Bags.class));
    }

    public void stationaryAcitivity(View view) {

        startActivity(new Intent(MainActivity.this, VideoLanding.class));
    }

    public void calendarsActivity(View view) {

        startActivity(new Intent(MainActivity.this, VideoLanding.class));
    }

    public void keychainsActivity(View view) {

        startActivity(new Intent(MainActivity.this, HomePageWithBottomNavigation.class));
    }
}
