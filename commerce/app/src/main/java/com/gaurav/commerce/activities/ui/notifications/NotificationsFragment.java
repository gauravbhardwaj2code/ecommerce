package com.gaurav.commerce.activities.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.gaurav.commerce.Cart;
import com.gaurav.commerce.NotificationActivity;
import com.gaurav.commerce.Profile;
import com.gaurav.commerce.R;
import com.gaurav.commerce.UpdateData;
import com.gaurav.commerce.Wishlist;
import com.gaurav.commerce.networksync.CheckInternetConnection;
import com.gaurav.commerce.usersession.UserSession;
import com.mikepenz.materialdrawer.Drawer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    private Drawer result;
    private TextView tvemail,tvphone;

    private TextView namebutton;
    private CircleImageView primage;
    private TextView updateDetails;
    private LinearLayout addressview;


    //to get user session data
    private UserSession session;
    private HashMap<String, String> user;
    private String name,email,photo,mobile;
    private SliderLayout sliderShow;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);



        initialize(root);

        //check Internet Connection
        new CheckInternetConnection(getContext()).checkConnection();

        //retrieve session values and display on listviews
        getValues();

        return root;

    }

    private void initialize(View root) {

        addressview = root.findViewById(R.id.addressview);
        primage=root.findViewById(R.id.profilepic);
        tvemail=root.findViewById(R.id.emailview);
        tvphone=root.findViewById(R.id.mobileview);
        namebutton=root.findViewById(R.id.name_button);
        updateDetails=root.findViewById(R.id.updatedetails);

        addressview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotificationsFragment.this.getActivity(),Wishlist.class));
            }
        });
    }


    private void getValues() {

        //create new session object by passing application context
        session = new UserSession(getContext().getApplicationContext());

        //validating session
        session.isLoggedIn();

        //get User details if logged in
        user = session.getUserDetails();

        name=user.get(UserSession.KEY_NAME);
        email=user.get(UserSession.KEY_EMAIL);
        mobile=user.get(UserSession.KEY_MOBiLE);
        photo=user.get(UserSession.KEY_PHOTO);

        //setting values
        tvemail.setText(email);
        tvphone.setText(mobile);
        namebutton.setText(name);

        Picasso.get().load(photo).into(primage);


    }



    public void viewCart(View view) {
       // startActivity(new Intent(Profile.this, Cart.class));
        //finish();
    }
}

