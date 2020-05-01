package com.lms.exam.activities.ui.home;


import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.database.FirebaseDatabase;
import com.lms.exam.activities.ui.home.view.HomeCoursesRecyclerView;

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


        lecturesRecyclerView.setAdapter(new HomeCoursesRecyclerView(database, my_course_list, lectureName, nextActiviti));
    }

}


