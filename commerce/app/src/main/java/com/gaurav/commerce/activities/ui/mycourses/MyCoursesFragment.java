package com.gaurav.commerce.activities.ui.mycourses;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gaurav.commerce.R;
import com.gaurav.commerce.activities.course.detail.CourseDetail;
import com.gaurav.commerce.activities.course.dto.DtoLectures;
import com.gaurav.commerce.activities.course.dto.DtoSubjectInfo;
import com.gaurav.commerce.activities.ui.home.view.HomeCoursesRecyclerView;
import com.gaurav.commerce.activities.ui.home.view.SubjectViewHolder;
import com.gaurav.commerce.database.util.MockDatabaseUtil;
import com.gaurav.commerce.networksync.CheckInternetConnection;
import com.gaurav.commerce.usersession.UserSession;
import com.gaurav.commerce.usersession.UserSubjectInfo;
import com.gaurav.commerce.usersession.UserSubjectProgress;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.gaurav.commerce.database.util.MockDatabaseUtil.PRODUCTS;
import static com.gaurav.commerce.usersession.UserSession.KEY_MOBiLE;
import static com.gaurav.commerce.usersession.UserSession.KEY_USER_SUBJECT_INFO;

public class MyCoursesFragment extends Fragment {


    private MyCoursesViewModel myCoursesViewModel;

    private UserSession userSession;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        new CheckInternetConnection(getContext()).checkConnection();

         this.userSession=new UserSession(this.getContext());

        myCoursesViewModel =
                ViewModelProviders.of(this).get(MyCoursesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mycourse, container, false);

        /*CircularProgressBar circularProgressBar = root.findViewById(R.id.circle_progress);

// or with animation
        circularProgressBar.setProgressWithAnimation(65f, 3000l);
        circularProgressBar.setProgressBarColor(Color.BLACK);
        // Set Progress Max
        circularProgressBar.setProgressMax(200f);
// or with gradient
        circularProgressBar.setProgressBarColorStart(Color.GRAY);
        circularProgressBar.setProgressBarColorEnd(Color.GREEN);

        // Set Width
        circularProgressBar.setProgressBarWidth(7f); // in DP
        circularProgressBar.setBackgroundProgressBarWidth(3f); // in DP

// Other
        circularProgressBar.setRoundBorder(true);
        circularProgressBar.setStartAngle(180f);*/

        String phone = userSession.getUserDetails().get(KEY_MOBiLE);

        renderList(inflater,root.findViewById(R.id.recyclerview2),database,R.layout.mycourse_list,phone, CourseDetail.class);


        return root;
    }


    private void renderList(LayoutInflater inflater,RecyclerView lecturesRecyclerView, FirebaseDatabase database, int home_page_course_recycler_view, String lectureName,Class<?> nextActiviti) {

        lecturesRecyclerView.setHasFixedSize(true);
        //using staggered grid pattern in recyclerview
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.VERTICAL,false);
        lecturesRecyclerView.setLayoutManager(mLayoutManager);

        lecturesRecyclerView.setAdapter(new MyCourseCourseRecyclerView(database,home_page_course_recycler_view,lectureName,nextActiviti,this.userSession));
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onStop() {
        super.onStop();
    }

}


class MyCourseCourseRecyclerView extends RecyclerView.Adapter<MyCourseViewHolder> {



    List<DtoSubjectInfo> list=new ArrayList<>();
    DatabaseReference databaseReference;
    Integer listItemView;
    private Class<?> nextActiviti;

    String LECTURES_DATABASE="";

    private UserSession userSession;

    public MyCourseCourseRecyclerView(FirebaseDatabase database, int home_page_course_recycler_view,
                                      String lectureName,Class<?> nextActiviti,
                                      UserSession userSession) {
        this.listItemView=home_page_course_recycler_view;
        this.LECTURES_DATABASE=lectureName;
        this.nextActiviti=nextActiviti;
        this.userSession=userSession;

        //  MockDatabaseUtil.createHomePageBestSellingBanner(database,lectureName);

        this.databaseReference=database.getReference().child(lectureName);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    Map<String,List<Long>> idMap= (Map<String, List<Long>>) dataSnapshot.getValue();
                    Map<Integer,DtoSubjectInfo> map= MockDatabaseUtil.getSubjectInfoMap(idMap.get(PRODUCTS));
                    list=new ArrayList<>(map.values());
                    if(null!=list && list.size()>0){
                        for(DtoSubjectInfo subjectInfo:list){
                            if(!userSession.getUserSubjectInfo().containSubject(subjectInfo.getId())){
                                UserSubjectInfo userSubjectInfo=userSession.getUserSubjectInfo();
                                UserSubjectProgress userSubjectProgress=new UserSubjectProgress();
                                int totalLecture=0;
                                if(subjectInfo.getLectures()!=null){
                                    for(DtoLectures list: subjectInfo.getLectures()){
                                        if(list.getLectureContents()!=null) {
                                            totalLecture = totalLecture + list.getLectureContents().size();
                                        }
                                    }
                                }

                                userSubjectProgress.setTotalVideos(totalLecture);
                                userSubjectInfo.setSubject(subjectInfo.getId().toString(),userSubjectProgress);
                                userSession.setUserSubjectInfo(userSubjectInfo);
                            }
                        }

                    }


                }
                notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    @NonNull
    @Override
    public MyCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(listItemView, parent, false);
        MyCourseViewHolder holder = new MyCourseViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyCourseViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                try {
                    Intent intent=new Intent(v.getContext(), nextActiviti);
                    intent.putExtra("sId",list.get(position));
                    v.getContext().startActivity(intent);
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
        });

        holder.name.setText(String.valueOf(list.get(position).getName()));
        holder.currentPosition.setText(String.valueOf(list.get(position).getLanguage()));
        holder.teacherName.setText(String.valueOf(list.get(position).getFacultyId()));
        Picasso.with(holder.url.getContext()).load(list.get(position).getUrlImage()).into(holder.url);
        Float coveredVideos= Integer.valueOf(userSession.getUserSubjectInfo().getSubjectById(list.get(position).getId()).getCoveredVideos().size()).floatValue();
        Float totalVideos=Integer.valueOf(userSession.getUserSubjectInfo().getSubjectById(list.get(position).getId()).getTotalVideos()).floatValue();

        holder.circularProgressBar.setProgressWithAnimation(Math.round((coveredVideos/totalVideos)*100), 3000l);
        holder.circle_progress_text.setText(String.valueOf(Math.round((coveredVideos/totalVideos)*100)));

        holder.circularProgressBar.setProgressBarColor(Color.BLACK);
        // Set Progress Max
        holder.circularProgressBar.setProgressMax(200f);
// or with gradient
        holder.circularProgressBar.setProgressBarColorStart(Color.GRAY);
        holder.circularProgressBar.setProgressBarColorEnd(Color.GREEN);

        // Set Width
        holder.circularProgressBar.setProgressBarWidth(7f); // in DP
        holder.circularProgressBar.setBackgroundProgressBarWidth(3f); // in DP

// Other
        holder. circularProgressBar.setRoundBorder(true);
        holder.circularProgressBar.setStartAngle(180f);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
