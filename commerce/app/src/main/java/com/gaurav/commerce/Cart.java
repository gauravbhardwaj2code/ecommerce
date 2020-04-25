package com.gaurav.commerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.airbnb.lottie.LottieAnimationView;
import com.gaurav.commerce.activities.course.dto.DtoCart;
import com.gaurav.commerce.activities.course.dto.DtoSubjectInfo;
import com.gaurav.commerce.database.util.MockDatabaseUtil;
import com.gaurav.commerce.networksync.CheckInternetConnection;
import com.gaurav.commerce.usersession.UserSession;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static com.gaurav.commerce.database.constants.DatabaseConstants.CART_ITEMS_KEY;

public class Cart extends AppCompatActivity {

    //to get user session data
    private UserSession session;
    private HashMap<String, String> user;
    private String name,email,photo,mobile;
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;

    //Getting reference to Firebase Database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = database.getReference();
    private LottieAnimationView tv_no_item;
    private LinearLayout activitycartlist;
    private LottieAnimationView emptycart;

    private List<DtoCart> cartcollect;
    private float totalcost=0;
    private int totalproducts=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Cart");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        //retrieve session values and display on listviews
        getValues();

        //SharedPreference for Cart Value
        session = new UserSession(getApplicationContext());

        //validating session
        session.isLoggedIn();

        mRecyclerView = findViewById(R.id.recyclerview);
        tv_no_item = findViewById(R.id.tv_no_cards);
        activitycartlist = findViewById(R.id.activity_cart_list);
        emptycart = findViewById(R.id.empty_cart);
        cartcollect = new ArrayList<>(session.getCartWishlistItems(CART_ITEMS_KEY));

        if (mRecyclerView != null) {
            //to enable optimization of recyclerview
            mRecyclerView.setHasFixedSize(true);
        }
        //using staggered grid pattern in recyclerview
        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if(session.getCartValue()>0) {
            populateRecyclerView();
        }else if(session.getCartValue() == 0)  {
            tv_no_item.setVisibility(View.GONE);
            activitycartlist.setVisibility(View.GONE);
            emptycart.setVisibility(View.VISIBLE);
        }
    }

    private void populateRecyclerView() {


        mRecyclerView.setAdapter(new RecyclerView.Adapter<MovieViewHolder>() {
            @NonNull
            @Override
            public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
                MovieViewHolder holder = new MovieViewHolder(v);
                return holder;
            }

            @Override
            public void onBindViewHolder(@NonNull MovieViewHolder viewHolder, int position) {
                if(tv_no_item.getVisibility()== View.VISIBLE){
                    tv_no_item.setVisibility(View.GONE);
                }

                DtoSubjectInfo subjectInfo=MockDatabaseUtil.subjectInfoMap.get(String.valueOf(cartcollect.get(position).getSubjectId()));

                viewHolder.cardname.setText(subjectInfo.getName());
                viewHolder.cardprice.setText("₹ "+cartcollect.get(position).getPrice());
                viewHolder.cardcount.setText("Quantity : "+1);
                viewHolder.language.setText("Language: "+subjectInfo.getLanguage());
                Picasso.with(Cart.this).load(subjectInfo.getUrlImage()).into(viewHolder.cardimage);
                viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        session.deleteCartWishlistItems(CART_ITEMS_KEY,cartcollect.get(position));
                        cartcollect = new ArrayList<>(session.getCartWishlistItems(CART_ITEMS_KEY));
                        notifyDataSetChanged();
                    }
                });

                if(cartcollect.get(position).getPrice()!=null){
                    totalcost = totalcost+cartcollect.get(position).getPrice().floatValue();
                }
                totalproducts =totalproducts+1;
                //cartcollect.add(model);


            }

            @Override
            public int getItemCount() {
                return cartcollect.size();
            }

        });


    }

    public void checkout(View view) {
        Intent intent = new Intent(Cart.this,OrderDetails.class);
        intent.putExtra("totalprice", Float.toString(totalcost));
        intent.putExtra("totalproducts", Integer.toString(totalproducts));
        //intent.putExtra("cartproducts",cartcollect);
        startActivity(intent);
       // finish();
    }

    //viewHolder for our Firebase UI
    public static class MovieViewHolder extends RecyclerView.ViewHolder{

        TextView cardname;
        ImageView cardimage;
        TextView cardprice;
        TextView cardcount;
        TextView language;
        ImageView carddelete;
        ImageView delete;

        View mView;
        public MovieViewHolder(View v) {
            super(v);
            mView = v;
            cardname = v.findViewById(R.id.cart_prtitle);
            cardimage = v.findViewById(R.id.image_cartlist);
            cardprice = v.findViewById(R.id.cart_prprice);
            cardcount = v.findViewById(R.id.cart_prcount);
            carddelete = v.findViewById(R.id.deletecard);
            language =v.findViewById(R.id.language);
            delete=v.findViewById(R.id.deletecard);
        }
    }


    private void getValues() {

        //create new session object by passing application context
        session = new UserSession(getApplicationContext());

        //validating session
        session.isLoggedIn();

        //get User details if logged in
        user = session.getUserDetails();

        name = user.get(UserSession.KEY_NAME);
        email = user.get(UserSession.KEY_EMAIL);
        mobile = user.get(UserSession.KEY_MOBiLE);
        photo = user.get(UserSession.KEY_PHOTO);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void viewProfile(View view) {
        startActivity(new Intent(Cart.this,Profile.class));
    }

    @Override
    protected void onResume() {
        super.onResume();

        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();

    }

    public void Notifications(View view) {

        startActivity(new Intent(Cart.this,NotificationActivity.class));
    }
}


