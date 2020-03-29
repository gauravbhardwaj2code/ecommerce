package com.gaurav.commerce.fragments;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.gaurav.commerce.R;
import com.gaurav.commerce.activities.course.dto.AllPlayersWrapper;
import com.gaurav.commerce.activities.course.dto.DtoLectureContents;
import com.gaurav.commerce.activities.course.dto.DtoLectures;
import com.gaurav.commerce.activities.ui.home.dto.DtoCourse;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import bg.devlabs.fullscreenvideoview.FullscreenVideoView;

public class CurriculamRecycleViewHandler {


    public  CurriculamRecycleViewHandler(RecyclerView recyclerView, int recycleview_curriculam,
                                         List<DtoLectures> lectures, LayoutInflater inflater, Boolean play_allowed, FullscreenVideoView fullscreenVideoView,
                                         AllPlayersWrapper mPlayer,
                                         Integer subjectId) {

            recyclerView.setHasFixedSize(true);
        //using staggered grid pattern in recyclerview
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);


        recyclerView.setAdapter(new CurriculamRecyclerView(lectures,recycleview_curriculam,inflater,play_allowed,fullscreenVideoView, mPlayer,subjectId));
    }

}


class CurriculamRecyclerView extends RecyclerView.Adapter<CurriculamHolder> {



    List<DtoLectures> list=new ArrayList<>();
    Integer listItemView;
    LayoutInflater inflater;
    FullscreenVideoView fullscreenVideoView;
    Integer subjectId;
    private Boolean play_allowed;
    AllPlayersWrapper mPlayer;


    public CurriculamRecyclerView(List<DtoLectures> lectures, int home_page_course_recycler_view,
                                  LayoutInflater inflater,Boolean play_allowed,FullscreenVideoView fullscreenVideoView,
                                  AllPlayersWrapper mPlayer,
                                  Integer subjectId) {
        this.listItemView=home_page_course_recycler_view;
        this.list=lectures;
        this.inflater=inflater;
        this.play_allowed=play_allowed;
        this.fullscreenVideoView=fullscreenVideoView;
        this.mPlayer= mPlayer;
        this.subjectId=subjectId;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CurriculamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(listItemView, parent, false);
        CurriculamHolder holder = new CurriculamHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CurriculamHolder holder, int position) {

        holder.name.setText("Section "+(position+1)+": "+String.valueOf(list.get(position).getTitle()));
        new CurriculamChildRecycleViewHandler(holder.listView,R.layout.listview_curriculam,
                list.get(position),inflater,play_allowed,fullscreenVideoView,mPlayer,subjectId);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

class CurriculamHolder extends RecyclerView.ViewHolder{

    public TextView name;
    public RecyclerView listView;

    public View mView;

    public CurriculamHolder(View v) {
        super(v);
        mView =v;
        name = v.findViewById(R.id.name);
        listView=v.findViewById(R.id.listView);

    }
}



