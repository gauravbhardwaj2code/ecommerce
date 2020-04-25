package com.gaurav.commerce.database.util;

import android.os.AsyncTask;


import com.gaurav.commerce.activities.course.dto.DtoSubjectInfo;
import com.gaurav.commerce.activities.ui.mycourses.UpdateList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.gaurav.commerce.usersession.UserSession.KEY_EMAIL;

public class FetchMySubjects extends AsyncTask<String, Void, String> {

    public static final String URL = "https://www.examonline.org/api/mycourse";

    private UpdateList updateList;
    public FetchMySubjects(UpdateList updateList) {
        this.updateList=updateList;
    }


    @Override
    protected String doInBackground(String... params){
        String email = params[0];
        String mobile = params[1];
        String data="";
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("email", email)
                    .addFormDataPart("mobile", mobile)
                    .build();
            Request request = new Request.Builder()
                    .url(URL)
                    .method("POST", body)
                    .build();
            Response response = client.newCall(request).execute();
            data=response.body().string();

            List<Long> ids=new ArrayList<>();
            List<DtoSubjectInfo> list=new ArrayList<>();
            try {
                System.out.println(data);
                if(null!=data && !data.trim().isEmpty()){
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<HashMap<String, Object>>>(){}.getType();
                    List<HashMap<String, Object>> listOfSubject =
                            gson.fromJson(data, listType);
                    for(HashMap<String, Object> map:listOfSubject){
                        Double id=Double.parseDouble(String.valueOf(map.get("id")));
                        ids.add(id.longValue());
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            for(Long id:ids){
                list.add(MockDatabaseUtil.getSubjectInfoById(id));
            }

            updateList.updateData(list);

        }
        catch(Exception e){
            e.printStackTrace();
            data = null;
        }
        return data;
    }

    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }
}