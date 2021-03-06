package com.lms.exam.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lms.exam.Cart;
import com.lms.exam.HelpCenter;
import com.lms.exam.Profile;
import com.lms.exam.R;
import com.lms.exam.Wishlist;
import com.lms.exam.database.util.MockDatabaseUtil;
import com.lms.exam.routehandler.security.GoogleSecurity;
import com.lms.exam.usersession.UserSession;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.webianks.easy_feedback.EasyFeedback;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class HomePageWithBottomNavigation extends AppCompatActivity {

    private Drawer result;
    //to get user session data
    private UserSession session;
    private HashMap<String, String> user;
    private String name, email, photo, mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_with_bottom_navigation);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        MockDatabaseUtil.initDatabase();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_my_courses, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        getValues();

        inflateNavDrawer();


    }


    public void viewCart(View view) {
        startActivity(new Intent(HomePageWithBottomNavigation.this, Cart.class));
        // finish();
    }

    public void viewProfile(View view) {
        startActivity(new Intent(HomePageWithBottomNavigation.this, Profile.class));
        //finish();
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

    private void inflateNavDrawer() {

        //set Custom toolbar to activity -----------------------------------------------------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the AccountHeader ----------------------------------------------------------------

        //Profile Making
        IProfile profile = new ProfileDrawerItem()
                .withName(name)
                .withEmail(email)
                .withIcon(photo);

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.gradient_background)
                // .addProfiles(profile)
                .withCompactStyle(true)

                .build();

        //Adding nav drawer items ------------------------------------------------------------------
        SecondaryDrawerItem item1 = new SecondaryDrawerItem().withIdentifier(2).withName("Live Classes").withIcon(R.drawable.profile);
        SecondaryDrawerItem item3 = new SecondaryDrawerItem().withIdentifier(3).withName(R.string.wishlist).withIcon(R.drawable.wishlist);
        SecondaryDrawerItem item4 = new SecondaryDrawerItem().withIdentifier(4).withName(R.string.cart).withIcon(R.drawable.cart);
        SecondaryDrawerItem item5 = new SecondaryDrawerItem().withIdentifier(5).withName(R.string.logout).withIcon(R.drawable.logout);

/*
        SecondaryDrawerItem item7 = new SecondaryDrawerItem().withIdentifier(7).withName("Offers").withIcon(R.drawable.tag);
*/
        // SecondaryDrawerItem item8 = new SecondaryDrawerItem().withIdentifier(8).withName(R.string.aboutapp).withIcon(R.drawable.credits);
        SecondaryDrawerItem item9 = new SecondaryDrawerItem().withIdentifier(9).withName(R.string.feedback).withIcon(R.drawable.feedback);
        SecondaryDrawerItem item10 = new SecondaryDrawerItem().withIdentifier(10).withName(R.string.helpcentre).withIcon(R.drawable.helpccenter);

        SecondaryDrawerItem item12 = new SecondaryDrawerItem().withIdentifier(12).withName("Terms And Condtions");
        SecondaryDrawerItem item13 = new SecondaryDrawerItem().withIdentifier(13).withName("Explore").withIcon(R.drawable.explore);
        SecondaryDrawerItem item14 = new SecondaryDrawerItem().withIdentifier(14).withName("Privacy Policy");
        SecondaryDrawerItem item15 = new SecondaryDrawerItem().withIdentifier(15).withName("Rate This App").withIcon(R.drawable.ic_star_gray);;


        //creating navbar and adding to the toolbar ------------------------------------------------
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                //.withHasStableIds(true)
                // .withDrawerLayout(R.layout.crossfade_drawer)
                .withAccountHeader(headerResult)
                //.withDrawerWidthDp(72)
                // .withGenerateMiniDrawer(false)
                //.withFullscreen(true)
                //.withTranslucentStatusBar(true)
                //.withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        item1, item3, item4, item5, item9, new DividerDrawerItem(), item10, new DividerDrawerItem(), item12, item13, item14,item15
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {


                        Integer value = new Long(drawerItem.getIdentifier()).intValue();

                        switch (value) {

                            case 2:
                                Intent liveclasses = new Intent(HomePageWithBottomNavigation.this, AppWebView.class
                                        //Uri.parse("https://www.examonline.org/events-news-list")
                                );
                                liveclasses.putExtra("url", "https://www.examonline.org/liveclass-schedule");
                                HomePageWithBottomNavigation.this.startActivity(liveclasses);
                                break;
                            case 3:
                                startActivity(new Intent(HomePageWithBottomNavigation.this, Wishlist.class));
                                break;
                            case 4:
                                startActivity(new Intent(HomePageWithBottomNavigation.this, Cart.class));
                                break;
                            case 5:
                                session.logoutUser();
                                GoogleSecurity.googleLogout(GoogleSignIn.getClient(HomePageWithBottomNavigation.this, GoogleSecurity.gso),
                                        HomePageWithBottomNavigation.this);
                                finish();
                                break;

                            /*case 7:
                                startActivity(new Intent(HomePageWithBottomNavigation.this, NotificationActivity.class));
                                break;*/
                            case 9:
                                new EasyFeedback.Builder(HomePageWithBottomNavigation.this)
                                        .withEmail("lms.examonline@gmail.com")
                                        .withSystemInfo()
                                        .build()
                                        .start();
                                break;
                            case 10:
                                startActivity(new Intent(HomePageWithBottomNavigation.this, HelpCenter.class));
                                break;
                            case 12:
                                Intent browserIntent = new Intent(HomePageWithBottomNavigation.this, AppWebView.class
                                        //Uri.parse("https://www.examonline.org/events-news-list")
                                );
                                browserIntent.putExtra("url", "https://www.examonline.org/cafoundationAPP/play-store-terms-conditions.php");
                                HomePageWithBottomNavigation.this.startActivity(browserIntent);
                                break;
                            case 13:
                                if (result != null && result.isDrawerOpen()) {
                                    result.closeDrawer();
                                }
                                tapview();
                                break;
                            case 14:
                                Intent policy = new Intent(HomePageWithBottomNavigation.this, AppWebView.class
                                        //Uri.parse("https://www.examonline.org/events-news-list")
                                );
                                policy.putExtra("url", "https://www.examonline.org/cafoundationAPP/play-store-privacy-policy.php");
                                HomePageWithBottomNavigation.this.startActivity(policy);
                                break;
                            case 15:
                                Uri uri = Uri.parse("market://details?id=" + HomePageWithBottomNavigation.this.getPackageName());
                                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                                // To count with Play market backstack, After pressing back button,
                                // to taken back to our application, we need to add following flags to intent.
                                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                                try {
                                    startActivity(goToMarket);
                                } catch (ActivityNotFoundException e) {
                                    Toast.makeText(HomePageWithBottomNavigation.this, "App Not Found!", Toast.LENGTH_LONG).show();
                                }
                                break;
                            default:
                                Toast.makeText(HomePageWithBottomNavigation.this, "Default", Toast.LENGTH_LONG).show();

                        }

                        return true;
                    }
                })
                .build();

    }

    private void tapview() {

        new TapTargetSequence(this)
                .targets(
                        TapTarget.forView(findViewById(R.id.notifintro), "Notifications", "Latest offers will be available here !")
                                .targetCircleColor(R.color.colorAccent)
                                .titleTextColor(R.color.colorAccent)
                                .titleTextSize(25)
                                .descriptionTextSize(15)
                                .descriptionTextColor(R.color.accent)
                                .drawShadow(true)                   // Whether to draw a drop shadow or not
                                .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)
                                .transparentTarget(true)
                                .outerCircleColor(R.color.first),
                        TapTarget.forView(findViewById(R.id.view_profile), "Profile", "You can view and edit your profile here !")
                                .targetCircleColor(R.color.colorAccent)
                                .titleTextColor(R.color.colorAccent)
                                .titleTextSize(25)
                                .descriptionTextSize(15)
                                .descriptionTextColor(R.color.accent)
                                .drawShadow(true)                   // Whether to draw a drop shadow or not
                                .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)
                                .transparentTarget(true)
                                .outerCircleColor(R.color.third),
                        TapTarget.forView(findViewById(R.id.cart), "Your Cart", "Here is Shortcut to your cart !")
                                .targetCircleColor(R.color.colorAccent)
                                .titleTextColor(R.color.colorAccent)
                                .titleTextSize(25)
                                .descriptionTextSize(15)
                                .descriptionTextColor(R.color.accent)
                                .drawShadow(true)
                                .cancelable(false)// Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)
                                .transparentTarget(true)
                                .outerCircleColor(R.color.second)/*,
                        TapTarget.forView(findViewById(R.id.visitingcards), "Categories", "Product Categories have been listed here !")
                                .targetCircleColor(R.color.colorAccent)
                                .titleTextColor(R.color.colorAccent)
                                .titleTextSize(25)
                                .descriptionTextSize(15)
                                .descriptionTextColor(R.color.accent)
                                .drawShadow(true)
                                .cancelable(false)// Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)
                                .transparentTarget(true)
                                .outerCircleColor(R.color.fourth)*/)
                .listener(new TapTargetSequence.Listener() {
                    // This listener will tell us when interesting(tm) events happen in regards
                    // to the sequence
                    @Override
                    public void onSequenceFinish() {
                        session.setFirstTime(false);
                        Toasty.success(HomePageWithBottomNavigation.this, " You are ready to go !", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        // Boo
                    }
                }).start();

    }


}
