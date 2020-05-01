package com.lms.exam.activities.lectures.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lms.exam.R;
import com.lms.exam.models.GenericProductModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LectureRecyclerView extends RecyclerView.Adapter<MovieViewHolder> {

    public static final String LECTURES_DATABASE = "lectures";

    List<GenericProductModel> list = new ArrayList<>();
    DatabaseReference databaseReference;

    public LectureRecyclerView(FirebaseDatabase database) {

        createMockData(database);

        this.databaseReference = database.getReference().child(LECTURES_DATABASE);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
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

        Map<String, Object> productModelMap = new HashMap<>();
        productModelMap.put("1", new GenericProductModel(1, "Resource 1", "cardname", "cardname", 44));
        productModelMap.put("2", new GenericProductModel(2, "Resource 1 2", "cardname", "cardname", 44));
        productModelMap.put("3", new GenericProductModel(3, "Resource 1 3", "cardname", "cardname", 44));
        productModelMap.put("4", new GenericProductModel(4, "Resource 1 4", "cardname", "cardname", 44));
        productModelMap.put("5", new GenericProductModel(5, "Resource 1 4", "cardname", "cardname", 44));
        productModelMap.put("6", new GenericProductModel(6, "Resource 1 5", "cardname", "cardname", 44));
        myRef.child(LECTURES_DATABASE).updateChildren(productModelMap);

    }
}


class MovieViewHolder extends RecyclerView.ViewHolder {

    TextView cardname;
    TextView cardprice;

    View mView;

    public MovieViewHolder(View v) {
        super(v);
        mView = v;
        cardname = v.findViewById(R.id.cardcategory);
        cardprice = v.findViewById(R.id.cardprice);
    }
}


