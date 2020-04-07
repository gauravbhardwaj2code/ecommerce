package com.gaurav.commerce.usersession;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.gaurav.commerce.LoginActivity;
import com.gaurav.commerce.activities.course.dto.DtoCart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.util.JsonMapper;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import static com.gaurav.commerce.database.constants.DatabaseConstants.CART_ITEMS_KEY;
import static com.gaurav.commerce.database.constants.DatabaseConstants.WISHLIST_ITEMS_KEY;

/**
 * Created by kshitij on 12/18/17.
 */

public class UserSession {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "UserSessionPref";

    // First time logic Check
    public static final String FIRST_TIME = "firsttime";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    // Mobile number (make variable public to access from outside)
    public static final String KEY_MOBiLE = "mobile";

    // user avatar (make variable public to access from outside)
    public static final String KEY_PHOTO = "photo";

    // number of items in our cart
    public static final String KEY_CART = "cartvalue";

    // number of items in our wishlist
    public static final String KEY_WISHLIST = "wishlistvalue";

    // check first time app launch
    public static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public static final String KEY_USER_SUBJECT_INFO = "key_user_subject_info";

    // Constructor
    public UserSession(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, String email, String mobile, String photo){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // Storing phone number in pref
        editor.putString(KEY_MOBiLE, mobile);

        // Storing image url in pref
        editor.putString(KEY_PHOTO, photo);


        Gson gson = new Gson();

        editor.putString(KEY_USER_SUBJECT_INFO,gson.toJson(new UserSubjectInfo()));
        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }

    }


    public void setMobileNo(String mobileNo){
        editor.putString(KEY_MOBiLE, mobileNo);
        editor.commit();
    }


    public UserSubjectInfo getUserSubjectInfo(){
        Gson gson = new Gson();
        UserSubjectInfo userSubjectInfo=null;
        try{
            userSubjectInfo=gson.fromJson(pref.getString(KEY_USER_SUBJECT_INFO,null),UserSubjectInfo.class);
        }catch (Exception e){
         e.printStackTrace();
        }

        if(userSubjectInfo==null){
            userSubjectInfo=new UserSubjectInfo();
            editor.putString(KEY_USER_SUBJECT_INFO,gson.toJson(userSubjectInfo));
            editor.commit();
        }
        return userSubjectInfo;
    }

    public UserSubjectInfo setUserSubjectInfo(UserSubjectInfo userSubjectInfo){
            Gson gson = new Gson();
            editor.putString(KEY_USER_SUBJECT_INFO,gson.toJson(userSubjectInfo));
            editor.commit();

        return userSubjectInfo;
    }

    public Set<DtoCart> getCartWishlistItems(String name){
        Gson gson = new Gson();
        Set<DtoCart> list=null;
        try{
            list=gson.fromJson(pref.getString(name,null),Set.class);

            Set<DtoCart> newSet=new HashSet<>();
            if(list!=null && list.size()>0){
                for(Object o:list){
                    if(o  instanceof LinkedTreeMap){
                        LinkedTreeMap<String,Object> data= (LinkedTreeMap<String, Object>) o;
                        DtoCart dtoCart=new DtoCart();
                        dtoCart.setValidity(String.valueOf(data.get("validity")));
                        dtoCart.setMode(String.valueOf(data.get("mode")));
                        if(data.get("subjectId") instanceof Double){
                            dtoCart.setSubjectId(new Double((Double) data.get("subjectId")).intValue());
                        }else{
                            dtoCart.setSubjectId(Integer.parseInt(String.valueOf(data.get("subjectId"))));
                        }
                        if(data.get("price") instanceof Double){
                            dtoCart.setPrice(new Double((Double) data.get("price")));
                        }else{
                            dtoCart.setPrice(Double.parseDouble(String.valueOf(data.get("price"))));
                        }
                        newSet.add(dtoCart);
                    }else{
                        newSet.add((DtoCart) o);
                    }
                }
                return newSet;
            }
        }catch (Exception e){
            e.printStackTrace();
        }



        if(list==null){
            list=new HashSet<>();
            editor.putString(name,gson.toJson(list));
            editor.commit();
        }
        return list;
    }

    public void setCartWishlistItems(String name,Set<DtoCart> list){
        Gson gson = new Gson();
        editor.putString(name,gson.toJson(list));
        editor.commit();
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // user phone number
        user.put(KEY_MOBiLE, pref.getString(KEY_MOBiLE, null));

        // user avatar
        user.put(KEY_PHOTO, pref.getString(KEY_PHOTO, null)) ;

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.putBoolean(IS_LOGIN,false);
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);
    }




    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public int getCartValue(){
        return pref.getInt(KEY_CART,getCartWishlistItems(CART_ITEMS_KEY).size());
    }

    public int getWishlistValue(){
        return pref.getInt(KEY_WISHLIST,getCartWishlistItems(WISHLIST_ITEMS_KEY).size());
    }

    public Boolean getFirstTime() {
        return pref.getBoolean(FIRST_TIME, true);
    }

    public void setFirstTime(Boolean n){
        editor.putBoolean(FIRST_TIME,n);
        editor.commit();
    }


    public void increaseCartValue(){
        int val = getCartValue()+1;
        editor.putInt(KEY_CART,val);
        editor.commit();
        Log.e("Cart Value PE", "Var value : "+val+"Cart Value :"+getCartValue()+" ");
    }

    public void increaseWishlistValue(){
        int val = getWishlistValue()+1;
        editor.putInt(KEY_WISHLIST,val);
        editor.commit();
        Log.e("Cart Value PE", "Var value : "+val+"Cart Value :"+getCartValue()+" ");
    }

    public void decreaseCartValue(){
        int val = getCartValue()-1;
        editor.putInt(KEY_CART,val);
        editor.commit();
        Log.e("Cart Value PE", "Var value : "+val+"Cart Value :"+getCartValue()+" ");
    }

    public void decreaseWishlistValue(){
        int val = getWishlistValue()-1;
        editor.putInt(KEY_WISHLIST,val);
        editor.commit();
        Log.e("Cart Value PE", "Var value : "+val+"Cart Value :"+getCartValue()+" ");
    }

    public void setCartValue(int count){
        editor.putInt(KEY_CART,count);
        editor.commit();
    }

    public void setWishlistValue(int count){
        editor.putInt(KEY_WISHLIST,count);
        editor.commit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}