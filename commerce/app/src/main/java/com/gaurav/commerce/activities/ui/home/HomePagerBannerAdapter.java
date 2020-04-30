package com.gaurav.commerce.activities.ui.home;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.gaurav.commerce.activities.ui.home.dto.DtoHomePageBanner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.gaurav.commerce.database.constants.DatabaseConstants.BANNER_DATA_BASE_NAME;

public class HomePagerBannerAdapter extends PagerAdapter {


    private final Random random = new Random();
    private int mSize;
    DatabaseReference databaseReference;
    private List<DtoHomePageBanner> list=new ArrayList<>();


    public HomePagerBannerAdapter(FirebaseDatabase database) {
        this.mSize = 0;
        this.databaseReference=database.getReference().child(BANNER_DATA_BASE_NAME);
       // MockDatabaseUtil.createHomePageBanner();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                mSize = 0;
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    list.add(data.getValue(DtoHomePageBanner.class));
                }

                mSize = list.size();
                notifyDataSetChanged();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }


    @Override public int getCount() {
        return mSize;
    }

    @Override public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup view, int position, @NonNull Object object) {
        view.removeView((View) object);
    }

    @NonNull @Override public Object instantiateItem(@NonNull ViewGroup view, int position) {

        ImageView imageView=new ImageView(view.getContext());
        Picasso.get().load(list.get(position).getImage())
                .fit().into(imageView);
        view.addView(imageView,ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return imageView;

        /*TextView textView = new TextView(view.getContext());
        textView.setText(String.valueOf(position + 1));
        textView.setBackgroundColor(0xff000000 | random.nextInt(0x00ffffff));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(48);
        view.addView(textView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return textView;*/
    }

}