package com.gaurav.commerce.http;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FetchPaymentLink extends AsyncTask<String, Void, String> {




    @Override
    protected String doInBackground(String... params) {

        String url=params[0];

        String result="";
        try {
            OkHttpClient.Builder builder =  new OkHttpClient().newBuilder();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                builder = new OkHttpClient().newBuilder()
                        .callTimeout(Duration.ofMinutes(5L))
                        .connectTimeout(Duration.ofMinutes(5L));
            }

            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = builder.build().newCall(request).execute();
            Map<String,Object> responseMap= (Map<String, Object>) new Gson().fromJson(response.body().string(), HashMap.class).get("payment_options");
            result=responseMap.get("payment_url").toString();
        }
        catch(Exception e){
            e.printStackTrace();
            result = null;
        }
        return result;
    }

}
