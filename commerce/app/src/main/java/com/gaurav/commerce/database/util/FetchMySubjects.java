package com.gaurav.commerce.database.util;

import android.os.AsyncTask;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FetchMySubjects extends AsyncTask<String, Void, String> {

    public static final String URL = "https://www.examonline.org/api/mycourse";


    @Override
    protected String doInBackground(String... params){
        String email = params[0];
        String mobile = params[1];
        String result="";
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("email", email)
                    .addFormDataPart("mobile", mobile)
                    .build();
            Request request = new Request.Builder()
                    .url(URL)
                    .method("POST", body)
                    .build();
            Response response = client.newCall(request).execute();
            result=response.body().string();
        }
        catch(Exception e){
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }
}