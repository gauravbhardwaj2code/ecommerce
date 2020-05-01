package com.lms.exam.database.util;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lms.exam.activities.course.dto.DtoSubjectInfo;
import com.lms.exam.activities.ui.mycourses.UpdateList;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FetchMySubjects extends AsyncTask<String, Void, String> {

    public static final String URL = "https://www.examonline.org/api/mycourse";
    List<DtoSubjectInfo> list;
    private UpdateList updateList;

    public FetchMySubjects(UpdateList updateList) {
        this.updateList = updateList;
        list = new ArrayList<>();
    }


    @Override
    protected String doInBackground(String... params) {
        String email = params[0];
        String mobile = params[1];
        String data = "";
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
            data = response.body().string();

            try {
                System.out.println(data);
                if (null != data && !data.trim().isEmpty()) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<HashMap<String, Object>>>() {
                    }.getType();
                    List<HashMap<String, Object>> listOfSubject =
                            gson.fromJson(data, listType);
                    for (HashMap<String, Object> map : listOfSubject) {
                        Double id = Double.parseDouble(String.valueOf(map.get("id")));
                        DtoSubjectInfo dtoSubjectInfo = MockDatabaseUtil.getSubjectInfoById(id.longValue());
                        if (dtoSubjectInfo != null) {
                            dtoSubjectInfo.setPurchaseMode(String.valueOf(map.get("mode")));
                            dtoSubjectInfo.setPurchaseExpiry(String.valueOf(map.get("expiry")));
                            list.add(dtoSubjectInfo);
                        }

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            data = null;
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        updateList.updateData(list);
    }
}