package com.gaurav.commerce.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.gaurav.commerce.MainActivity;
import com.gaurav.commerce.R;
import com.gaurav.commerce.activities.course.dto.AllPlayersWrapper;
import com.gaurav.commerce.activities.course.dto.DtoLectureContents;
import com.gaurav.commerce.activities.course.dto.DtoLectures;
import com.gaurav.commerce.activities.course.dto.LectureContentType;
import com.gaurav.commerce.usersession.UserSession;
import com.gaurav.commerce.usersession.UserSubjectInfo;
import com.google.android.youtube.player.YouTubePlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import bg.devlabs.fullscreenvideoview.FullscreenVideoView;
import es.dmoral.toasty.Toasty;

public class CurriculamChildRecycleViewHandler {


    public  CurriculamChildRecycleViewHandler(RecyclerView recyclerView, int recycleview_curriculam, DtoLectures lectures,
                                              LayoutInflater inflater, Boolean play_allowed, FullscreenVideoView fullscreenVideoView
    , AllPlayersWrapper mPlayer,Integer subjectId) {

            recyclerView.setHasFixedSize(true);
        //using staggered grid pattern in recyclerview
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);


        recyclerView.setAdapter(new CurriculamChildRecycleView(lectures,recycleview_curriculam,inflater,play_allowed,fullscreenVideoView,mPlayer,subjectId));
    }

}


class CurriculamChildRecycleView extends RecyclerView.Adapter<CurriculamChildHolder> {



    List<DtoLectureContents> list=new ArrayList<>();
    DtoLectures lecture;
    Integer listItemView;
    LayoutInflater inflater;
    FullscreenVideoView fullscreenVideoView;
    private Boolean play_allowed;
    Integer subjectId;
    AllPlayersWrapper mPlayer;
    private View currentLectureView;


    String LECTURES_DATABASE="";


    public CurriculamChildRecycleView(DtoLectures lectures, int home_page_course_recycler_view,
                                      LayoutInflater inflater,Boolean play_allowed, FullscreenVideoView fullscreenVideoView
            ,AllPlayersWrapper mPlayer,Integer subjectId) {
        this.listItemView=home_page_course_recycler_view;
        this.list=lectures.getLectureContents();
        this.lecture=lectures;
        this.inflater=inflater;
        this.play_allowed=play_allowed;
        this.fullscreenVideoView=fullscreenVideoView;
        this.mPlayer=mPlayer;
        this.subjectId=subjectId;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CurriculamChildHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(listItemView, parent, false);
        CurriculamChildHolder holder = new CurriculamChildHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CurriculamChildHolder holder, int position) {

        holder.name.setText(String.valueOf(list.get(position).getTitle()));
        holder.time.setText(String.valueOf(list.get(position).getTime()));
        holder.index.setText(String.valueOf(position+1));
        if(play_allowed){
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url=list.get(position).getUrl();
                    LectureContentType type=list.get(position).getLectureContentType();
                    if(null==url || url.trim().isEmpty()){
                        Toasty.error(inflater.getContext(), "No Resource Found!", Toast.LENGTH_SHORT).show();
                    }else{
                        System.out.println(url);
                        try{
                            UserSubjectInfo userSubjectInfo=new UserSession(v.getContext()).getUserSubjectInfo();
                            userSubjectInfo.getSubjectById(subjectId).coveredVideo(list.get(position).getTitle());
                            userSubjectInfo.getSubjectById(subjectId).setCurrentLectureContent(list.get(position).getTitle());
                            userSubjectInfo.getSubjectById(subjectId).setCurrentSubjectId(subjectId.toString());
                            new UserSession(v.getContext()).setUserSubjectInfo(userSubjectInfo);
                            if(type.equals(LectureContentType.VIDEO)){
                                if(currentLectureView!=null){
                                    currentLectureView.setBackgroundColor(v.getResources().getColor(R.color.white,null));
                                }
                                currentLectureView=holder.mView;
                                currentLectureView.setBackgroundColor(v.getResources().getColor(R.color.grey,null));
                                mPlayer.play(url);
                            }else{
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                v.getContext().startActivity(browserIntent);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                       // fullscreenVideoView.changeUrl(list.get(position).getUrl());
                    }

                };
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

class CurriculamChildHolder extends RecyclerView.ViewHolder{

    public TextView name;
    public TextView time;
    public TextView index;

    public View mView;

    public CurriculamChildHolder(View v) {
        super(v);
        mView =v;
        name = v.findViewById(R.id.lecture_content_name);
        time=v.findViewById(R.id.time);
        index=v.findViewById(R.id.index);

    }
}
