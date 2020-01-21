package com.gaurav.commerce;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.gaurav.commerce.networksync.CheckInternetConnection;
import com.gaurav.commerce.routehandler.security.GoogleSecurity;
import com.gaurav.commerce.routehandler.security.RouteHandler;
import com.gaurav.commerce.usersession.UserSession;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {

    private EditText edtemail,edtpass;
    private String email,pass,sessionmobile;
    private TextView appname,forgotpass,registernow;
    private RequestQueue requestQueue;
    private UserSession session;
    public static final String TAG = "MyTag";
    private int cartcount, wishlistcount;

    //Getting reference to Firebase Database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = database.getReference();


    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.e("Login CheckPoint","LoginActivity started");
        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        Typeface typeface = ResourcesCompat.getFont(this, R.font.blacklist);
        appname = findViewById(R.id.appname);
        appname.setTypeface(typeface);

        edtemail= findViewById(R.id.email);
        edtpass= findViewById(R.id.password);

        Bundle registerinfo=getIntent().getExtras();
        if (registerinfo!=null) {
                edtemail.setText(registerinfo.getString("email"));
        }

        session= new UserSession(getApplicationContext());

        requestQueue = Volley.newRequestQueue(LoginActivity.this);//Creating the RequestQueue

        //if user wants to register
        registernow= findViewById(R.id.register_now);
        registernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,Register.class));
                finish();
            }
        });

        //if user forgets password
        forgotpass=findViewById(R.id.forgot_pass);
        forgotpass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this,ForgotPassword.class));
            }
        });


        //Validating login details
        Button button=findViewById(R.id.login_button);


        signInButton=findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);



        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, GoogleSecurity.gso);

        signInButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email=edtemail.getText().toString();
                pass=edtpass.getText().toString();

                if (validateUsername(email) && validatePassword(pass)) { //Username and Password Validation


                    //Passing all received data from server to next activity
                    String sessionname = "gaurav";
                    sessionmobile = null;
                    String sessionemail = "bhardwajagag@haha.com";
                    String sessionphoto = "https://iconorbit.com/icons/256-watermark/2203201617274056402-Rounded%20Book%20Clip%20Art.jpg";

                    //create shared preference and store data
                    session.createLoginSession(sessionname, sessionemail, sessionmobile, sessionphoto);

                    //count value of firebase cart and wishlist
                    //countFirebaseValues();

                    RouteHandler.launchHomeScreen(LoginActivity.this);


                    return;

                    //Progress Bar while connection establishes


                }
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);

            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                Log.i("Google SignIn",acct.getPhotoUrl().toString());
                sessionmobile = null;

                //create shared preference and store data
                session.createLoginSession(personGivenName, personEmail, sessionmobile, acct.getPhotoUrl().toString());

                //count value of firebase cart and wishlist
                //countFirebaseValues();

                RouteHandler.launchHomeScreen(LoginActivity.this);
            }
            // Signed in successfully, show authenticated UI.
            // updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Google SignIn", "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Check for an existing signed-in user
        // GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        // updateUI(account);
    }

    private boolean validatePassword(String pass) {


        if (pass.length() < 4 || pass.length() > 20) {
            edtpass.setError("Password Must consist of 4 to 20 characters");
            return false;
        }
        return true;
    }

    private boolean validateUsername(String email) {

        if (email.length() < 4 || email.length() > 30) {
            edtemail.setError("Email Must consist of 4 to 30 characters");
            return false;
        } else if (!email.matches("^[A-za-z0-9.@]+")) {
            edtemail.setError("Only . and @ characters allowed");
            return false;
        } else if (!email.contains("@") || !email.contains(".")) {
            edtemail.setError("Email must contain @ and .");
            return false;
        }
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Login CheckPoint","LoginActivity resumed");
        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        }

    @Override
    protected void onStop () {
        super.onStop();
        Log.e("Login CheckPoint","LoginActivity stopped");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
