package com.gaurav.commerce.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;
import com.gaurav.commerce.init.SingleTonClasses;
import com.gaurav.commerce.messagereader.GauravMessageListener;
import com.gaurav.commerce.usersession.UserSession;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gaurav.commerce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import java.util.Random;

import static com.gaurav.commerce.usersession.UserSession.KEY_MOBiLE;

public class MobileNoWithOtp extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = database.getReference();

    private MaterialEditText mobileText;
    private RequestQueue queue;
    private UserSession userSession;
    private PinView pinView;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_no_with_otp);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button sendOtpButton=findViewById(R.id.sendotp);
        Button verifyOtp=findViewById(R.id.verifyOtp);
        mobileText=findViewById(R.id.number);
        pinView=findViewById(R.id.pinView);
        userSession=new UserSession(this);

        queue = Volley.newRequestQueue(this);

        Random random=new Random();

        String fourDigitRandomNumber = String.format("%04d", random.nextInt(10000));

        SingleTonClasses.smsBroadcastReceiver.clear();

        if(!isSmsPermissionGranted()){
            showRequestPermissionsInfoAlertDialog(true);
        }

        SingleTonClasses.smsBroadcastReceiver.setGauravMessageListener("1",new GauravMessageListener() {
            @Override
            public void onTextReceived(String text) {
                Toast.makeText(MobileNoWithOtp.this, text, Toast.LENGTH_LONG).show();
            }
        });


        getApplicationContext().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                System.out.println("@@@@@@@@@@@hsgdfvjhsdjbsdjjksdfjksbddns,mfbm");
                Toast.makeText(MobileNoWithOtp.this, "jhsgdcjhgasdjhghdkjahdkjhkajsdhkjahdkjhakdhkahdkjhakdhkahdk", Toast.LENGTH_LONG).show();
            }
        },new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION));

        sendOtpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {




                phoneNumber=mobileText.getText().toString();
                if(verifyMobile(phoneNumber)){

                    sendOtpRequest(phoneNumber,fourDigitRandomNumber);
                }else {
                    Toast.makeText(MobileNoWithOtp.this, "Invalid Mobile No.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        });

        verifyOtp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                String text=pinView.getText().toString();
                if(text!=null && text.length()==4
                && fourDigitRandomNumber.equalsIgnoreCase(text)){
                    userSession.setMobileNo(phoneNumber);
                    Intent loginSuccess = new Intent(MobileNoWithOtp.this, HomePageWithBottomNavigation.class);
                    startActivity(loginSuccess);
                    finish();
                }else{
                    Toast.makeText(MobileNoWithOtp.this, "Invalid Otp Code Please Resend OTP", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void showRequestPermissionsInfoAlertDialog(final boolean makeSystemRequest) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Requesting SMS permission"); // Your own title
        builder.setMessage("The app will now request your permission to send and read SMS related services."); // Your own message

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // Display system runtime permission request?
                if (makeSystemRequest) {
                    requestReadAndSendSmsPermission();
                }
            }
        });

        builder.setCancelable(false);
        builder.show();
    }

    /**
     * Request runtime SMS permission
     */
    private void requestReadAndSendSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
            // You may display a non-blocking explanation here, read more in the documentation:
            // https://developer.android.com/training/permissions/requesting.html
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 10);
    }

    public boolean isSmsPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private void sendOtpRequest(String phoneNumber,String fourDigitRandomNumber) {





        final String url = "https://api.msg91.com/api/v5/otp?authkey=314282AJc6R2tgj5e272b71P1&template_id=5e2945ead6fc0503a97e055c" +
                "&mobile="+phoneNumber+"&invisible=1&otp="+fourDigitRandomNumber+"&userip=192.168.22.43&email="+userSession.getUserDetails().get(UserSession.KEY_EMAIL)+"&sender=EXAMON";



        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        );

        // add it to the RequestQueue
        queue.add(getRequest);
    }

    private Boolean verifyMobile(String phoneNumber) {
        Log.e("inside number",phoneNumber.length()+" ");
        if (phoneNumber.length()>10) {
            return false;
        }else if(phoneNumber.length()<10){
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Check for an existing signed-in user
       // GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
       // updateUI(account);
    }

    private void countFirebaseValues(String sessionmobile, UserSession session) {

        mDatabaseReference.child("cart").child(sessionmobile).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(dataSnapshot.getKey(),dataSnapshot.getChildrenCount() + "");
                session.setCartValue((int)dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabaseReference.child("wishlist").child(sessionmobile).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(dataSnapshot.getKey(),dataSnapshot.getChildrenCount() + "");
                session.setWishlistValue((int)dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
