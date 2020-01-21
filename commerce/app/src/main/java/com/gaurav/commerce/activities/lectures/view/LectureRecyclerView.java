package com.gaurav.commerce.activities.lectures.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaurav.commerce.R;
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

public class LectureRecyclerView extends RecyclerView.Adapter<MovieViewHolder> {

    public static final String LECTURES_DATABASE = "lectures";

    List<GenericProductModel> list=new ArrayList<>();
    DatabaseReference databaseReference;

    public LectureRecyclerView(FirebaseDatabase database){

        createMockData(database);

        this.databaseReference=database.getReference().child(LECTURES_DATABASE);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    list.add(data.getValue(GenericProductModel.class));
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
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lecture_item_layout, parent, false);
        MovieViewHolder holder = new MovieViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        holder.cardname.setText(list.get(position).getCardname());
        //holder.cardprice.setText(list.get(position).description);
        // holder.imageView.setImageResource(list.get(position).imageId);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    private void createMockData(FirebaseDatabase database) {

        DatabaseReference myRef = database.getReference();

        Map<String,Object> productModelMap=new HashMap<>();
        productModelMap.put("1",new GenericProductModel(1,"Lecture 1","cardname","cardname",44));
        productModelMap.put("2",new GenericProductModel(2,"Lecture 2","cardname","cardname",44));
        productModelMap.put("3",new GenericProductModel(3,"Lecture 3","cardname","cardname",44));
        productModelMap.put("4",new GenericProductModel(4,"Lecture 4","cardname","cardname",44));
        productModelMap.put("5",new GenericProductModel(5,"Lecture 4","cardname","cardname",44));
        productModelMap.put("6",new GenericProductModel(6,"Lecture 5","cardname","cardname",44));

        productModelMap.put("7",new GenericProductModel(7,"Lecture 1","cardname","cardname",44));
        productModelMap.put("8",new GenericProductModel(8,"Lecture 2","cardname","cardname",44));
        productModelMap.put("9",new GenericProductModel(9,"Lecture 3","cardname","cardname",44));
        productModelMap.put("10",new GenericProductModel(10,"Lecture 4","cardname","cardname",44));
        productModelMap.put("11",new GenericProductModel(11,"Lecture 4","cardname","cardname",44));
        productModelMap.put("61",new GenericProductModel(12,"Lecture 5","cardname","cardname",44));
        productModelMap.put("111",new GenericProductModel(13,"Lecture 1","cardname","cardname",44));
        productModelMap.put("21",new GenericProductModel(24,"Lecture 2","cardname","cardname",44));
        productModelMap.put("31",new GenericProductModel(34,"Lecture 3","cardname","cardname",44));
        productModelMap.put("41",new GenericProductModel(44,"Lecture 4","cardname","cardname",44));
        productModelMap.put("51",new GenericProductModel(54,"Lecture 4","cardname","cardname",44));
        productModelMap.put("61",new GenericProductModel(64,"Lecture 5","cardname","cardname",44));
        myRef.child(LECTURES_DATABASE).updateChildren(productModelMap);

    }
}


class MovieViewHolder extends RecyclerView.ViewHolder{

    TextView cardname;
    TextView cardprice;

    View mView;
    public MovieViewHolder(View v) {
        super(v);
        mView =v;
        cardname = v.findViewById(R.id.cardcategory);
        cardprice = v.findViewById(R.id.cardprice);
    }
}


