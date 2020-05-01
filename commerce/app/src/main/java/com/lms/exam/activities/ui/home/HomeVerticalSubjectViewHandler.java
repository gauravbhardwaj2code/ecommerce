package com.lms.exam.activities.ui.home;

import android.view.LayoutInflater;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.lms.exam.activities.ui.home.view.HomeCoursesRecyclerView;

public class HomeVerticalSubjectViewHandler {


    public HomeVerticalSubjectViewHandler(LayoutInflater inflater, RecyclerView lecturesRecyclerView, FirebaseDatabase database, int home_page_course_recycler_view, String lectureName, Class<?> nextActiviti) {

        lecturesRecyclerView.setHasFixedSize(true);
        //using staggered grid pattern in recyclerview
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.VERTICAL, false);
        lecturesRecyclerView.setLayoutManager(mLayoutManager);

        lecturesRecyclerView.setAdapter(new HomeCoursesRecyclerView(database, home_page_course_recycler_view, lectureName, nextActiviti));
    }
}





