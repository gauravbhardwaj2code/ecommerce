package com.lms.exam.activities.ui.home.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lms.exam.R;

public class SubjectViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView teacherName;
    public TextView cost_price;
    public TextView selling_price;
    public TextView rating;
    public ImageView url;
    public TextView categoryName;
    public TextView language;

    private View mView;

    public SubjectViewHolder(View v) {
        super(v);
        mView = v;
        name = v.findViewById(R.id.name);
        cost_price = v.findViewById(R.id.cost_price);
        teacherName = v.findViewById(R.id.teacher_name);
        selling_price = v.findViewById(R.id.selling_price);
        rating = v.findViewById(R.id.rating);
        url = v.findViewById(R.id.imageurl);
        language = v.findViewById(R.id.language);
        categoryName = v.findViewById(R.id.category_name);
    }
}