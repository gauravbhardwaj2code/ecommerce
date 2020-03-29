package com.gaurav.commerce.activities.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.gaurav.commerce.R;
import com.gaurav.commerce.activities.course.detail.BuyCourse;
import com.gaurav.commerce.activities.ui.home.view.HomeCoursesRecyclerView;
import com.gaurav.commerce.models.GenericProductModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeVerticalSubjectViewHandler {



    public HomeVerticalSubjectViewHandler(LayoutInflater inflater,RecyclerView lecturesRecyclerView, FirebaseDatabase database, int home_page_course_recycler_view, String lectureName,Class<?> nextActiviti) {

            lecturesRecyclerView.setHasFixedSize(true);
        //using staggered grid pattern in recyclerview
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.VERTICAL,false);
        lecturesRecyclerView.setLayoutManager(mLayoutManager);

        lecturesRecyclerView.setAdapter(new HomeCoursesRecyclerView(database,home_page_course_recycler_view,lectureName,nextActiviti));
    }
}





