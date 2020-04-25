package com.gaurav.commerce.http;

import android.content.Context;
import android.os.AsyncTask;

import com.gaurav.commerce.usersession.UserSession;
import com.google.gson.Gson;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import static com.gaurav.commerce.database.constants.DatabaseConstants.CART_ITEMS_KEY;


public class CreateOrder extends AsyncTask<String, Void, String> {

    public static final String URL = "https://www.examonline.org/api/sale";


    private Context context;

    private UserSession userSession;

    private  List<Map<String,Object>> cartItems=new ArrayList<>();


    public CreateOrder(Context context){
        this.context=context;
        userSession=new UserSession(context);
        userSession.getCartWishlistItems(CART_ITEMS_KEY).forEach(cart->{
            Map<String,Object> carts=new HashMap<>();
            carts.put("subjectId",cart.getSubjectId());
            carts.put("mode",cart.getMode());
            carts.put("price",cart.getPrice());
            carts.put("validity",cart.getValidity());
            cartItems.add(carts);
        });
    }

    @Override
    protected String doInBackground(String... params) {

        String result="";
        try {
            OkHttpClient.Builder builder =  new OkHttpClient().newBuilder();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                builder = new OkHttpClient().newBuilder()
                        .callTimeout(Duration.ofMinutes(5L))
                        .connectTimeout(Duration.ofMinutes(5L));
            }

            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("email", userSession.getUserDetails().get(UserSession.KEY_EMAIL))
                    .addFormDataPart("mobile", userSession.getUserDetails().get(UserSession.KEY_MOBiLE))
                    .addFormDataPart("orderItems",new Gson().toJson(cartItems))
                            //"[{\"subjectId\":1,\"validity\":\"2020-08-01\",\"mode\":\"online\",\"price\":5000}]")
                    .build();
            Request request = new Request.Builder()
                    .url(URL)
                    .method("POST", body)
                    .build();
            Response response = builder.build().newCall(request).execute();
            result=new Gson().fromJson(response.body().string(), HashMap.class).get("order_id").toString();
        }
        catch(Exception e){
            e.printStackTrace();
            result = null;
        }
        return result;
    }
}
