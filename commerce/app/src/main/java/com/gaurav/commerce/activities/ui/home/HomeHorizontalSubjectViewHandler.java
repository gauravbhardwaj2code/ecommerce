package com.gaurav.commerce.activities.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.gaurav.commerce.activities.course.detail.CourseDetail;
import com.gaurav.commerce.activities.ui.home.view.HomeCoursesRecyclerView;

import com.google.firebase.database.FirebaseDatabase;

public class HomeHorizontalSubjectViewHandler {



    public HomeHorizontalSubjectViewHandler(RecyclerView lecturesRecyclerView, FirebaseDatabase database, int my_course_list, String lectureName,
                                            Class<?> nextActiviti) {
        if (lecturesRecyclerView != null) {
            //to enable optimization of recyclerview
            lecturesRecyclerView.setHasFixedSize(true);
        }
        //using staggered grid pattern in recyclerview
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        lecturesRecyclerView.setLayoutManager(mLayoutManager);


        lecturesRecyclerView.setAdapter(new HomeCoursesRecyclerView(database,my_course_list,lectureName,nextActiviti));
    }

}


