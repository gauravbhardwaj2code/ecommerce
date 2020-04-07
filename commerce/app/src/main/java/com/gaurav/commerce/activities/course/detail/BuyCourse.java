package com.gaurav.commerce.activities.course.detail;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;

import com.gaurav.commerce.Wishlist;
import com.gaurav.commerce.activities.course.dto.AllPlayersWrapper;
import com.gaurav.commerce.activities.course.dto.DtoCart;
import com.gaurav.commerce.activities.course.dto.DtoSubjectInfo;
import com.gaurav.commerce.database.util.MockDatabaseUtil;
import com.gaurav.commerce.fragments.AbountCourse;
import com.gaurav.commerce.fragments.Curriculam;
import com.gaurav.commerce.fragments.LectureList;
import com.gaurav.commerce.fragments.ProductDetail;
import com.gaurav.commerce.usersession.UserSession;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurav.commerce.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import bg.devlabs.fullscreenvideoview.FullscreenVideoView;
import bg.devlabs.fullscreenvideoview.playbackspeed.PlaybackSpeedOptions;
import es.dmoral.toasty.Toasty;

import static com.gaurav.commerce.database.constants.DatabaseConstants.CART_ITEMS_KEY;
import static com.gaurav.commerce.database.constants.DatabaseConstants.WISHLIST_ITEMS_KEY;

public class BuyCourse extends AppCompatActivity implements TabLayout.OnTabSelectedListener,
        Curriculam.OnFragmentInteractionListener,
        AbountCourse.OnFragmentInteractionListener,
        ProductDetail.OnFragmentInteractionListener{

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

//        textView=findViewById(R.id.cost_price);
//        textView.setText("₹"+subjectInfo.getCostPrice()+"");
  //      textView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    //    textView.setVisibility(View.INVISIBLE);

        textView=findViewById(R.id.selling_price);
        textView.setText("₹"+subjectInfo.getCostPrice()+"");
        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Info"),true);
        tabLayout.addTab(tabLayout.newTab().setText("Course Structure"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
        BuyCoursePager adapter = new BuyCoursePager(getSupportFragmentManager(), tabLayout.getTabCount(),viewPager,subjectInfo,
                playersWrapper,textView);

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        tabLayout.setOnTabSelectedListener(this);

        initializeYoutubePlayer(subjectInfo);

        initCartAndWishlist(subjectInfo);

    }

    private void initCartAndWishlist(DtoSubjectInfo subjectInfo) {
        UserSession userSession=new UserSession(this);

        TextView wishList=findViewById(R.id.add_to_wishlist);
        wishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subjectInfo.getDtoCart()==null){
                    Toasty.error(getBaseContext(),"Please Select Date And Mode!",Toast.LENGTH_LONG).show();
                }else{
                    Set<DtoCart> cartList=userSession.getCartWishlistItems(WISHLIST_ITEMS_KEY);
                    cartList.add(subjectInfo.getDtoCart());
                    userSession.setCartWishlistItems(WISHLIST_ITEMS_KEY,cartList);
                    Toasty.success(getBaseContext(),"Item Added To wish LIst",Toast.LENGTH_LONG).show();
                }


            }
        });

        TextView cart=findViewById(R.id.add_to_cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subjectInfo.getDtoCart()==null){
                    Toasty.error(getBaseContext(),"Please Select Date And Mode!",Toast.LENGTH_LONG).show();
                }else {
                    Set<DtoCart> cartList=userSession.getCartWishlistItems(CART_ITEMS_KEY);
                    cartList.add(subjectInfo.getDtoCart());
                    userSession.setCartWishlistItems(CART_ITEMS_KEY,cartList);
                    Toasty.success(getBaseContext(), "Item Added To Cart ", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    private void initializeYoutubePlayer(DtoSubjectInfo subjectInfo) {

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
                    if(subjectInfo.getDemoVideoUrl()!=null &&
                            subjectInfo.getDemoVideoUrl().size()>0){
                        youTubePlayer.cueVideo(subjectInfo.getDemoVideoUrl().get(0));
                    }

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
    private TextView priceView;
    //Constructor to the class


    public BuyCoursePager(FragmentManager fm, int tabCount, ViewPager viewPager, DtoSubjectInfo subjectInfo,
                          AllPlayersWrapper youTubePlayer, TextView priceView) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
        this.viewPager=viewPager;
        this.subjectInfo=subjectInfo;
        this.youTubePlayer=youTubePlayer;
        this.priceView=priceView;

    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                return ProductDetail.newInstance(subjectInfo,priceView);
            case 1:
                //viewPager.setCurrentItem(0);
            return  Curriculam.newInstance(subjectInfo,false,null,youTubePlayer);

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
