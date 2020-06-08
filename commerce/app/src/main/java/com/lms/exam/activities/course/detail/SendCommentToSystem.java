package com.lms.exam.activities.course.detail;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.lms.exam.chatbox.Comment;

import java.time.Duration;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendCommentToSystem extends AsyncTask<String, Void, String> {

    public static final String URL = "https://www.examonline.org/api/videocomment";

    private Comment comment;

    public SendCommentToSystem(Comment comment){
        this.comment=comment;
    }

    @Override
    protected String doInBackground(String... strings) {
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
                    .addFormDataPart("student_email", comment.getStudent_email())
                    .addFormDataPart("faculty_email", comment.getFaculty_email())
                    .addFormDataPart("youtube_id", comment.getYoutube_id())
                    .addFormDataPart("comment", comment.getContent())
                    //"[{\"subjectId\":1,\"validity\":\"2020-08-01\",\"mode\":\"online\",\"price\":5000}]")
                    .build();
            Request request = new Request.Builder()
                    .url(URL)
                    .method("POST", body)
                    .build();
            Response response = builder.build().newCall(request).execute();
            result = new Gson().fromJson(response.body().string(), HashMap.class).get("status").toString();
            if (result != null && result.trim().isEmpty() && response.code() == 200) {
                result = "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

}
