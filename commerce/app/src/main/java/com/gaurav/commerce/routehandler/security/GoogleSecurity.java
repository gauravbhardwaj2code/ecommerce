package com.gaurav.commerce.routehandler.security;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class GoogleSecurity {

    public static GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();

  public static void  googleLogout(GoogleSignInClient mGoogleSignInClient, AppCompatActivity compatActivity){
      mGoogleSignInClient.signOut()
              .addOnCompleteListener(compatActivity, new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {
                      // ...
                  }
              });

  };

}
