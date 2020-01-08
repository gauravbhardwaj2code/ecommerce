package com.gaurav.commerce.prodcutscategory;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.airbnb.lottie.LottieAnimationView;

import com.gaurav.commerce.Cart;
import com.gaurav.commerce.IndividualProduct;
import com.gaurav.commerce.NotificationActivity;
import com.gaurav.commerce.R;
import com.gaurav.commerce.models.GenericProductModel;
import com.gaurav.commerce.networksync.CheckInternetConnection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Tshirts extends AppCompatActivity {


    //created for firebaseui android tutorial by Vamsi Tallapudi

    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private LottieAnimationView tv_no_item;

    //Getting reference to Firebase Database
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        DatabaseReference myRef = database.getReference();

        Map<String,Object> productModelMap=new HashMap<>();
        productModelMap.put("1",new GenericProductModel(1,"cardname","cardname","cardname",44));
        productModelMap.put("2",new GenericProductModel(2,"cardname","cardname","cardname",44));
        productModelMap.put("3",new GenericProductModel(3,"cardname","cardname","cardname",44));
        productModelMap.put("4",new GenericProductModel(4,"cardname","cardname","cardname",44));
        productModelMap.put("5",new GenericProductModel(5,"cardname","cardname","cardname",44));
        productModelMap.put("6",new GenericProductModel(6,"cardname","cardname","cardname",44));

        myRef.child("tshirts").updateChildren(productModelMap);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        //Initializing our Recyclerview
        mRecyclerView = findViewById(R.id.my_recycler_view);
        tv_no_item = findViewById(R.id.tv_no_cards);


        if (mRecyclerView != null) {
            //to enable optimization of recyclerview
            mRecyclerView.setHasFixedSize(true);
        }
        //using staggered grid pattern in recyclerview
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);


       mRecyclerView.setAdapter(new Recycler_View_Adapter(database));
        tv_no_item.setVisibility(View.GONE);

    }

    public void viewCart(View view) {
        startActivity(new Intent(Tshirts.this, Cart.class));
        finish();
    }


    //viewHolder for our Firebase UI
    public static class MovieViewHolder extends RecyclerView.ViewHolder{

        TextView cardname;
        ImageView cardimage;
        TextView cardprice;

        View mView;
        public MovieViewHolder(View v) {
            super(v);
            mView =v;
            cardname = v.findViewById(R.id.cardcategory);
            cardimage = v.findViewById(R.id.cardimage);
            cardprice = v.findViewById(R.id.cardprice);
        }
    }

    public void Notifications(View view) {
        startActivity(new Intent(Tshirts.this, NotificationActivity.class));
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();
    }


     class Recycler_View_Adapter extends RecyclerView.Adapter<MovieViewHolder> {

         List<GenericProductModel> list=new ArrayList<>();
         DatabaseReference databaseReference;

         public Recycler_View_Adapter( FirebaseDatabase database){
            this.databaseReference=database.getReference().child("tshirts");

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

             View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_cardview_layout, parent, false);
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
     }

}
