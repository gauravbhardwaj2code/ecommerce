package com.gaurav.commerce.activities.ui.home.view;

import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaurav.commerce.activities.course.dto.DtoSubjectInfo;
import com.gaurav.commerce.database.util.MockDatabaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gaurav.commerce.database.util.MockDatabaseUtil.PRODUCTS;

public class HomeCoursesRecyclerView extends RecyclerView.Adapter<SubjectViewHolder> {



    List<DtoSubjectInfo> list=new ArrayList<>();
    DatabaseReference databaseReference;
    Integer listItemView;
    private Class<?> nextActiviti;

    String LECTURES_DATABASE="";


    public HomeCoursesRecyclerView(FirebaseDatabase database, int home_page_course_recycler_view, String lectureName,Class<?> nextActiviti) {
        this.listItemView=home_page_course_recycler_view;
        this.LECTURES_DATABASE=lectureName;
        this.nextActiviti=nextActiviti;

      //  MockDatabaseUtil.createHomePageBestSellingBanner(database,lectureName);

        updateList();

        MockDatabaseUtil.adapters.put(lectureName,this);

        /*this.databaseReference=database.getReference().child(lectureName);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    Map<String,List<Long>> idMap= (Map<String, List<Long>>) dataSnapshot.getValue();
                    List<Long> ids=new ArrayList<>();
                    if(idMap.get(PRODUCTS) instanceof HashMap){
                        ids=new ArrayList(((HashMap) idMap.get(PRODUCTS)).values());
                    }else{
                        ids=idMap.get(PRODUCTS);
                    }
                    Map<Integer,DtoSubjectInfo> map=MockDatabaseUtil.getSubjectInfoMap(ids);
                    list=new ArrayList<>(map.values());
                }
                notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });*/
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
                    Intent intent=new Intent(v.getContext(), nextActiviti);
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
        holder.cost_price.setVisibility(View.INVISIBLE);
        holder.cost_price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.teacherName.setText(String.valueOf(list.get(position).getFacultyId()));
        holder.selling_price.setText("₹"+String.valueOf(list.get(position).getCostPrice()));
        holder.rating.setText(String.valueOf(list.get(position).getAverageRating()));
        Picasso.with(holder.url.getContext()).load(list.get(position).getUrlImage()).into(holder.url);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void updateList(){
        Map<Integer,DtoSubjectInfo> map=MockDatabaseUtil.getSubjectInfoMap(MockDatabaseUtil.getInternalDatabaseIds(LECTURES_DATABASE));
        list=new ArrayList<>(map.values());
        notifyDataSetChanged();
    }

}
