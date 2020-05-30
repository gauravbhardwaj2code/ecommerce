package com.lms.exam.async.security;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.lms.exam.usersession.UserSession;

import java.time.Duration;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SystemLogin extends AsyncTask<String, Void, String> {

    private static String URL="https://www.examonline.org/api/register";

    private HashMap<String,String> userDetail;



    public SystemLogin(HashMap<String, String> userDetails) {
        this.userDetail=userDetails;
    }

    @Override
    protected String doInBackground(String... params) {
        String email = userDetail.get(UserSession.KEY_EMAIL);
        String name = userDetail.get(UserSession.KEY_NAME);
        String mobile=userDetail.get(UserSession.KEY_MOBiLE);
        String source="social_login";

        String result = "";
        try {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                builder = new OkHttpClient().newBuilder()
                        .callTimeout(Duration.ofMinutes(5L))
                        .connectTimeout(Duration.ofMinutes(5L));
            }

            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("email", email)
                    .addFormDataPart("name", name)
                    .addFormDataPart("mobile",mobile)
                    .addFormDataPart("source",source)
                    //"[{\"subjectId\":1,\"validity\":\"2020-08-01\",\"mode\":\"online\",\"price\":5000}]")
                    .build();
            Request request = new Request.Builder()
                    .url(URL)
                    .method("POST", body)
                    .build();
            Response response = builder.build().newCall(request).execute();
            result = new Gson().fromJson(response.body().string(), HashMap.class).get("status").toString();
        } catch (Exception e) {
            e.printStackTrace();
            result = "exception";
        }
        return result;
    }

}
