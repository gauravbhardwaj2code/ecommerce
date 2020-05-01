package com.lms.exam.activities.ui.mycourses;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lms.exam.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class MyCourseViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView teacherName;
    public TextView circle_progress_text;
    public ImageView url;
    public TextView currentPosition;
    public CircularProgressBar circularProgressBar;
    public TextView mode;
    public TextView expiry;

    private View mView;

    public MyCourseViewHolder(View v) {
        super(v);
        mView = v;
        name = v.findViewById(R.id.title);
        teacherName = v.findViewById(R.id.teacher_name);
        url = v.findViewById(R.id.imageurl);
        currentPosition = v.findViewById(R.id.currentPosition);
        circularProgressBar = v.findViewById(R.id.circle_progress);
        circle_progress_text = v.findViewById(R.id.circle_progress_text);
        mode = v.findViewById(R.id.modecourse);
        expiry = v.findViewById(R.id.expiry);
    }
}
