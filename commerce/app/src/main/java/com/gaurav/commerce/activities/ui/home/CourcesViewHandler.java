package com.gaurav.commerce.activities.ui.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.gaurav.commerce.R;
import com.gaurav.commerce.activities.CategoryCourse;
import com.gaurav.commerce.activities.ui.home.dto.DtoCourse;
import com.gaurav.commerce.database.util.MockDatabaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CourcesViewHandler {



    public CourcesViewHandler(RecyclerView lecturesRecyclerView, FirebaseDatabase database, int my_course_list, String lectureName) {
        if (lecturesRecyclerView != null) {
            //to enable optimization of recyclerview
            lecturesRecyclerView.setHasFixedSize(true);
        }
        //using staggered grid pattern in recyclerview
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        lecturesRecyclerView.setLayoutManager(mLayoutManager);


        lecturesRecyclerView.setAdapter(new CoursesRecyclerView(database,my_course_list,lectureName));
    }
}



class CoursesRecyclerView extends RecyclerView.Adapter<CourseViewHolder> {



    List<DtoCourse> list=new ArrayList<>();
    DatabaseReference databaseReference;
    Integer listItemView;

    String LECTURES_DATABASE="";


    public CoursesRecyclerView(FirebaseDatabase database, int home_page_course_recycler_view, String lectureName) {
        this.listItemView=home_page_course_recycler_view;
        this.LECTURES_DATABASE=lectureName;

        //  MockDatabaseUtil.createHomePageBestSellingBanner(database,lectureName);

        this.databaseReference=database.getReference().child(lectureName);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    list.add(data.getValue(DtoCourse.class));
                }
                notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }


    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(listItemView, parent, false);
        CourseViewHolder holder = new CourseViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {

        holder.name.setText(String.valueOf(list.get(position).getName()));
       // Picasso.with(holder.url.getContext()).load(list.get(position).getIconUrl()).into(holder.url);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CategoryCourse.class);
                intent.putExtra("list", new ArrayList(MockDatabaseUtil.getAllSubjectByCourseId(list.get(position).getId().intValue()).values()));
                intent.putExtra("nametitle",list.get(position).getName());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

class CourseViewHolder extends RecyclerView.ViewHolder{

    public TextView name;
    public ImageView url;

    public View mView;

    public CourseViewHolder(View v) {
        super(v);
        mView =v;
        name = v.findViewById(R.id.name);
        url=v.findViewById(R.id.imageurl);
    }
}



