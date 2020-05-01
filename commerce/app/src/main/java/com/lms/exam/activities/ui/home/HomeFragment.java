package com.lms.exam.activities.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.FirebaseDatabase;
import com.lms.exam.R;
import com.lms.exam.activities.AppWebView;
import com.lms.exam.activities.course.detail.BuyCourse;
import com.lms.exam.networksync.CheckInternetConnection;

import me.relex.circleindicator.CircleIndicator;

import static com.lms.exam.database.constants.DatabaseConstants.COURSES;
import static com.lms.exam.database.constants.DatabaseConstants.H_LECTURES;
import static com.lms.exam.database.constants.DatabaseConstants.V_LECTURES;

public class HomeFragment extends Fragment {


    private HomeViewModel homeViewModel;


    private FirebaseDatabase database = FirebaseDatabase.getInstance();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        new CheckInternetConnection(getContext()).checkConnection();


        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ViewPager viewpager = (ViewPager) root.findViewById(R.id.viewpager);

        viewpager.setAdapter(new HomePagerBannerAdapter(database));

        CircleIndicator indicator = root.findViewById(R.id.indicator);
        indicator.setViewPager(viewpager);

        TextView textView = root.findViewById(R.id.news);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(getContext(), AppWebView.class
                        //Uri.parse("https://www.examonline.org/events-news-list")
                );
                browserIntent.putExtra("url", "https://www.examonline.org/events-news-list");
                v.getContext().startActivity(browserIntent);
            }
        });

        //MockDatabaseUtil.createAllDatabase();


        fetchHorizLectures(root, H_LECTURES);
        fetchVerticalLectures(inflater, root, V_LECTURES);
        new CourcesViewHandler(root.findViewById(R.id.category_container), database, R.layout.home_course_category_layout, COURSES);
        return root;
    }


    private void fetchVerticalLectures(LayoutInflater inflater, View root, String v_lectures) {
        new HomeVerticalSubjectViewHandler(inflater, root.findViewById(R.id.recyclerview2), database, R.layout.default_course_list, v_lectures, BuyCourse.class);
    }

    private void fetchHorizLectures(View root, String h_lectures) {
        new HomeHorizontalSubjectViewHandler(root.findViewById(R.id.recyclerview1), database, R.layout.home_page_course_recycler_view, h_lectures, BuyCourse.class);
    }

}





