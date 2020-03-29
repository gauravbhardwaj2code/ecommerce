package com.gaurav.commerce.activities.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.daimajia.slider.library.SliderLayout;
import com.gaurav.commerce.R;
import com.gaurav.commerce.activities.AppWebView;
import com.gaurav.commerce.activities.course.detail.BuyCourse;
import com.gaurav.commerce.networksync.CheckInternetConnection;
import com.gaurav.commerce.usersession.UserSession;
import com.google.firebase.database.FirebaseDatabase;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.materialdrawer.Drawer;

import java.util.HashMap;

import me.relex.circleindicator.CircleIndicator;

import static com.gaurav.commerce.database.constants.DatabaseConstants.COURSES;
import static com.gaurav.commerce.database.constants.DatabaseConstants.H_LECTURES;
import static com.gaurav.commerce.database.constants.DatabaseConstants.V_LECTURES;

public class HomeFragment extends Fragment {



    private HomeViewModel homeViewModel;

    private SliderLayout sliderShow;
    private SliderLayout sliderShow1;
    private Drawer result;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    //to get user session data
    private UserSession session;
    private HashMap<String, String> user;
    private String name, email, photo, mobile;
    private String  first_time;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        new CheckInternetConnection(getContext()).checkConnection();



        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

      //  inflateImageSlider(root);
       /* final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        ViewPager viewpager = (ViewPager) root.findViewById(R.id.viewpager);

        viewpager.setAdapter(new HomePagerBannerAdapter(database));

        CircleIndicator indicator = root.findViewById(R.id.indicator);
        indicator.setViewPager(viewpager);

        TextView textView=root.findViewById(R.id.news);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(getContext(), AppWebView.class
                        //Uri.parse("https://www.examonline.org/events-news-list")
                );
                v.getContext().startActivity(browserIntent);
            }
        });

        //MockDatabaseUtil.createAllDatabase();


        fetchHorizLectures(root, H_LECTURES);
        fetchVerticalLectures(inflater,root, V_LECTURES);
        new CourcesViewHandler(root.findViewById(R.id.category_container),database,R.layout.home_course_category_layout, COURSES);
        return root;
    }


    private void fetchVerticalLectures(LayoutInflater inflater, View root, String v_lectures) {
        new HomeVerticalSubjectViewHandler(inflater,root.findViewById(R.id.recyclerview2),database,R.layout.default_course_list,v_lectures, BuyCourse.class);
    }

    private void fetchHorizLectures(View root, String h_lectures) {
        new HomeHorizontalSubjectViewHandler(root.findViewById(R.id.recyclerview1),database,R.layout.home_page_course_recycler_view,h_lectures, BuyCourse.class);
    }


    /*private void inflateImageSlider(View root) {

        // Using Image Slider -----------------------------------------------------------------------
        sliderShow = root.findViewById(R.id.slider);

        //populating Image slider
        ArrayList<String> sliderImages = new ArrayList<>();
        sliderImages.add("https://www.examonline.org/estatic/homeimages/cma-final-b.webp");
        sliderImages.add("https://www.examonline.org/estatic/homeimages/cfr.webp");
        sliderImages.add("https://www.examonline.org/estatic/homeimages/mcq.webp");
        sliderImages.add("https://www.examonline.org/estatic/homeimages/blue-cma-inter.webp");

        for (String s : sliderImages) {
            DefaultSliderView sliderView = new DefaultSliderView(getContext());
            sliderView.image(s);
            sliderShow.addSlider(sliderView);
        }

        sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);

        //
        sliderShow1 =root.findViewById(R.id.slider2);

        sliderImages = new ArrayList<>();
        sliderImages.add("https://www.examonline.org/images/slider/cma-costing1.png");
        sliderImages.add("https://www.examonline.org/images/slider/ipc-cma.webp");
        sliderImages.add("https://www.examonline.org/images/slider/cainter-tax-sprao.webp");
        sliderImages.add("https://www.examonline.org/images/slider/cmafinal-cfr.webp");

        for (String s : sliderImages) {
            DefaultSliderView sliderView = new DefaultSliderView(getContext());
            sliderView.image(s);
            sliderShow1.addSlider(sliderView);
        }

        sliderShow1.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);

    }


    private void getValues() {

        //create new session object by passing application context
        session = new UserSession(getContext());

        //validating session
        session.isLoggedIn();

        //get User details if logged in
        user = session.getUserDetails();

        name = user.get(UserSession.KEY_NAME);
        email = user.get(UserSession.KEY_EMAIL);
        mobile = user.get(UserSession.KEY_MOBiLE);
        photo = user.get(UserSession.KEY_PHOTO);
    }
*/
}





