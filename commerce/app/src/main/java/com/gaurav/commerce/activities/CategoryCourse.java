package com.gaurav.commerce.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import com.gaurav.commerce.activities.course.detail.BuyCourse;
import com.gaurav.commerce.activities.course.dto.DtoSubjectInfo;
import com.gaurav.commerce.activities.ui.home.HomeVerticalSubjectViewHandler;
import com.gaurav.commerce.activities.ui.home.view.HomeCoursesRecyclerView;
import com.gaurav.commerce.activities.ui.home.view.SubjectViewHolder;
import com.gaurav.commerce.database.util.MockDatabaseUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaurav.commerce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.gaurav.commerce.database.constants.DatabaseConstants.ALL_LECTURES;
import static com.gaurav.commerce.database.util.MockDatabaseUtil.PRODUCTS;

public class CategoryCourse extends AppCompatActivity {

    List<DtoSubjectInfo> list=new ArrayList<>();
    DatabaseReference databaseReference;
    Integer listItemView;
    private Class<?> nextActiviti;

    String LECTURES_DATABASE="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_course);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<DtoSubjectInfo> list= (List<DtoSubjectInfo>) getIntent().getExtras().get("list");
        String nameTitle= (String) getIntent().getExtras().get("nametitle");
        TextView title=findViewById(R.id.title);
        title.setText(nameTitle);
        drawList(list);

    }


    private void drawList(List<DtoSubjectInfo> list) {

        RecyclerView lecturesRecyclerView = findViewById(R.id.recyclerview2);
        lecturesRecyclerView.setHasFixedSize(true);
        //using staggered grid pattern in recyclerview
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        lecturesRecyclerView.setLayoutManager(mLayoutManager);

        lecturesRecyclerView.setAdapter(new CategoryCourseRecyclerView(list));
    }
}

class CategoryCourseRecyclerView extends RecyclerView.Adapter<SubjectViewHolder> {



    List<DtoSubjectInfo> list=new ArrayList<>();
    Integer listItemView;



    public CategoryCourseRecyclerView(List<DtoSubjectInfo> list) {
        this.listItemView=R.layout.default_course_list;
        this.list=list;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(listItemView, parent, false);
        SubjectViewHolder holder = new SubjectViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                try {
                    Intent intent=new Intent(v.getContext(), BuyCourse.class);
                    intent.putExtra("sId",list.get(position));
                    v.getContext().startActivity(intent);
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
        });

        holder.name.setText(String.valueOf(list.get(position).getName()));
        holder.language.setText(String.valueOf(list.get(position).getLanguage()));
        holder.cost_price.setText("₹"+String.valueOf(list.get(position).getCostPrice()));
        holder.cost_price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.cost_price.setVisibility(View.INVISIBLE);
        holder.teacherName.setText(String.valueOf(list.get(position).getFacultyId()));
        holder.selling_price.setText("₹"+String.valueOf(list.get(position).getCostPrice()));
        holder.rating.setText(String.valueOf(list.get(position).getAverageRating()));
        Picasso.with(holder.url.getContext()).load(list.get(position).getUrlImage()).into(holder.url);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
