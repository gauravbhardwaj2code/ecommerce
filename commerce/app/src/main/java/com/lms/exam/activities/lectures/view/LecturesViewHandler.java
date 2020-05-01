package com.lms.exam.activities.lectures.view;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.database.FirebaseDatabase;

public class LecturesViewHandler {


    public LecturesViewHandler(RecyclerView lecturesRecyclerView, FirebaseDatabase database) {

        if (lecturesRecyclerView != null) {
            //to enable optimization of recyclerview
            lecturesRecyclerView.setHasFixedSize(true);
        }
        //using staggered grid pattern in recyclerview
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        lecturesRecyclerView.setLayoutManager(mLayoutManager);


        lecturesRecyclerView.setAdapter(new LectureRecyclerView(database));
    }
}
