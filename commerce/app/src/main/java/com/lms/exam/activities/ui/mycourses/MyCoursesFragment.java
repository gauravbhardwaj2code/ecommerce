package com.lms.exam.activities.ui.mycourses;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lms.exam.R;
import com.lms.exam.activities.course.detail.CourseDetail;
import com.lms.exam.activities.course.dto.DtoLectures;
import com.lms.exam.activities.course.dto.DtoSubjectInfo;
import com.lms.exam.database.util.FetchMySubjects;
import com.lms.exam.networksync.CheckInternetConnection;
import com.lms.exam.usersession.UserSession;
import com.lms.exam.usersession.UserSubjectInfo;
import com.lms.exam.usersession.UserSubjectProgress;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static com.lms.exam.usersession.UserSession.KEY_EMAIL;
import static com.lms.exam.usersession.UserSession.KEY_MOBiLE;

public class MyCoursesFragment extends Fragment implements UpdateList {


    private MyCoursesViewModel myCoursesViewModel;

    private UserSession userSession;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private RecyclerView lecturesRecyclerView;

    private ProgressBar progressBar;

    private CardView cardView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        new CheckInternetConnection(getContext()).checkConnection();

        this.userSession = new UserSession(this.getContext());

        myCoursesViewModel =
                ViewModelProviders.of(this).get(MyCoursesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mycourse, container, false);


        List<DtoSubjectInfo> list = new ArrayList<>();
        this.lecturesRecyclerView = root.findViewById(R.id.recyclerview2);
        this.progressBar = root.findViewById(R.id.progress_bar);
        this.cardView = root.findViewById(R.id.mycoursesview);
        renderList(inflater, lecturesRecyclerView, database, R.layout.mycourse_list, list, CourseDetail.class);

        try {
            FetchMySubjects fetchMySubjects = new FetchMySubjects(this);
            String phone = userSession.getUserDetails().get(KEY_MOBiLE);
            fetchMySubjects.execute(userSession.getUserDetails().get(KEY_EMAIL), phone);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;
    }


    public void toggleProgreeBar() {
        progressBar.setVisibility(View.GONE);
        cardView.setVisibility(View.VISIBLE);
    }


    private void renderList(LayoutInflater inflater, RecyclerView lecturesRecyclerView, FirebaseDatabase database, int home_page_course_recycler_view,
                            List<DtoSubjectInfo> list, Class<?> nextActiviti) {

        lecturesRecyclerView.setHasFixedSize(true);

        //using staggered grid pattern in recyclerview
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.VERTICAL, false);
        lecturesRecyclerView.setLayoutManager(mLayoutManager);


        lecturesRecyclerView.setAdapter(
                new MyCourseCourseRecyclerView(database, home_page_course_recycler_view, list, nextActiviti, this.userSession)
        );
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void updateData(List<DtoSubjectInfo> list) {
        toggleProgreeBar();
        UpdateList updateList = (UpdateList) this.lecturesRecyclerView.getAdapter();
        updateList.updateData(list);

    }
}


class MyCourseCourseRecyclerView extends RecyclerView.Adapter<MyCourseViewHolder> implements UpdateList {


    List<DtoSubjectInfo> list = new ArrayList<>();
    DatabaseReference databaseReference;
    Integer listItemView;
    private Class<?> nextActiviti;


    private UserSession userSession;

    public MyCourseCourseRecyclerView(FirebaseDatabase database, int home_page_course_recycler_view,
                                      List<DtoSubjectInfo> list, Class<?> nextActiviti,
                                      UserSession userSession) {
        this.listItemView = home_page_course_recycler_view;
        this.nextActiviti = nextActiviti;
        this.userSession = userSession;
        this.list = list;

        if (null != list && list.size() > 0) {
            for (DtoSubjectInfo subjectInfo : list) {
                if (!userSession.getUserSubjectInfo().containSubject(subjectInfo.getId())) {
                    UserSubjectInfo userSubjectInfo = userSession.getUserSubjectInfo();
                    UserSubjectProgress userSubjectProgress = new UserSubjectProgress();
                    int totalLecture = 0;
                    if (subjectInfo.getLectures() != null) {
                        for (DtoLectures dtoLectures : subjectInfo.getLectures()) {
                            if (dtoLectures.getLectureContents() != null) {
                                totalLecture = totalLecture + dtoLectures.getLectureContents().size();
                            }
                        }
                    }

                    userSubjectProgress.setTotalVideos(totalLecture);
                    userSubjectInfo.setSubject(subjectInfo.getId().toString(), userSubjectProgress);
                    userSession.setUserSubjectInfo(userSubjectInfo);
                }
            }

        }

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
            @Override
            public void onClick(View v) {
                try {
                    if (list.get(position).getPurchaseMode() != null &&
                            list.get(position).getPurchaseMode().equalsIgnoreCase("online")
                    ) {
                        Intent intent = new Intent(v.getContext(), nextActiviti);
                        intent.putExtra("sId", list.get(position));
                        v.getContext().startActivity(intent);
                    } else {
                        Toasty.error(holder.itemView.getContext(), "Selected Course doesn't contain any Lecture Content.").show();
                    }

                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });

        holder.name.setText(String.valueOf(list.get(position).getName()));
        holder.currentPosition.setText(String.valueOf(list.get(position).getLanguage()));
        holder.teacherName.setText(String.valueOf(list.get(position).getFacultyId()));
        holder.mode.setText(list.get(position).getPurchaseMode());
        holder.expiry.setText("Your Course will expire on " + list.get(position).getPurchaseExpiry());
        Picasso.get().load(list.get(position).getUrlImage()).into(holder.url);
        UserSubjectProgress userSubjectProgress = userSession.getUserSubjectInfo().getSubjectById(list.get(position).getId());
        Integer coveredVideos = 0;
        Integer totalVideos = 0;
        Float percentage = 0f;
        /*if(userSubjectProgress!=null){
            coveredVideos= userSubjectProgress.getCoveredVideos().size();
            totalVideos=userSubjectProgress.getTotalVideos();
            if(coveredVideos>0 && totalVideos>0){
                percentage=new Integer(Math.round((coveredVideos.floatValue()/totalVideos.floatValue())*100)).floatValue();
            }
        }*/

        holder.circularProgressBar.setProgressWithAnimation(percentage, 3000l);
        holder.circle_progress_text.setText(percentage.toString());

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
        holder.circularProgressBar.setRoundBorder(true);
        holder.circularProgressBar.setStartAngle(180f);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void updateData(List<DtoSubjectInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
