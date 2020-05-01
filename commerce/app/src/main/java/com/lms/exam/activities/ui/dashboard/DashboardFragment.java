package com.lms.exam.activities.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.database.FirebaseDatabase;
import com.lms.exam.R;
import com.lms.exam.activities.course.detail.BuyCourse;
import com.lms.exam.activities.ui.home.HomeVerticalSubjectViewHandler;

import static com.lms.exam.database.constants.DatabaseConstants.ALL_LECTURES;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        new HomeVerticalSubjectViewHandler(inflater, root.findViewById(R.id.recyclerview2), database, R.layout.default_course_list, ALL_LECTURES, BuyCourse.class);
        return root;


    }
}