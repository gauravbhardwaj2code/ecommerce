package com.lms.exam.activities.course.detail;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lms.exam.R;
import com.lms.exam.activities.course.dto.AllPlayersWrapper;
import com.lms.exam.activities.course.dto.DtoSubjectInfo;
import com.lms.exam.chatbox.Comment;
import com.lms.exam.chatbox.CommentAdapter;
import com.lms.exam.fragments.CurriculamRecycleViewHandler;
import com.lms.exam.usersession.UserSession;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import bg.devlabs.fullscreenvideoview.FullscreenVideoView;

public class CourseDetailPagerAdapter extends PagerAdapter {


    private Context mContext;
    private DtoSubjectInfo subjectInfo;
    private FullscreenVideoView fullscreenVideoView;
    private  AllPlayersWrapper playersWrapper;
    private FirebaseDatabase firebaseDatabase;
    int subjectId;
    static String COMMENT_KEY = "Comment" ;

    private String[] titles={"Content","Comments & QA"};

    public CourseDetailPagerAdapter(CourseDetail courseDetail) {
        mContext = courseDetail;
    }

    public CourseDetailPagerAdapter(CourseDetail courseDetail, DtoSubjectInfo subjectInfo, FullscreenVideoView fullscreenVideoView,
                                    AllPlayersWrapper playersWrapper, Integer id,FirebaseDatabase firebaseDatabase) {
        mContext = courseDetail;
        this.subjectInfo=subjectInfo;
        this.fullscreenVideoView=fullscreenVideoView;
        this.playersWrapper=playersWrapper;
        this.subjectId=id;
        this.firebaseDatabase=firebaseDatabase;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        if(position==0){
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.course_list_tab_view, collection, false);
            new CurriculamRecycleViewHandler(layout.getRootView().findViewById(R.id.lectures_list), R.layout.recycleview_curriculam,
                    subjectInfo.getLectures(), inflater, true, fullscreenVideoView, playersWrapper, subjectId);
            collection.addView(layout);
            return layout;
        }else{
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.activity_post_detail, collection, false);
            initCommentLayout(layout);
            collection.addView(layout);
            return layout;
        }

    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return titles[position];
    }


    private void initCommentLayout(ViewGroup viewGroup){
        ImageView imgPost,imgUserPost,imgCurrentUser;
        TextView txtPostDesc,txtPostDateName,txtPostTitle;
        EditText editTextComment;
        Button btnAddComment;
        String PostKey;

        RecyclerView RvComment;


        View rootView=viewGroup.getRootView();
        // ini Views
        RvComment = rootView.findViewById(R.id.rv_comment);


        editTextComment = rootView.findViewById(R.id.post_detail_comment);
        btnAddComment = rootView.findViewById(R.id.post_detail_add_comment_btn);
        UserSession userSession = new UserSession(viewGroup.getContext());
        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnAddComment.setVisibility(View.INVISIBLE);
                DatabaseReference commentReference = firebaseDatabase.getReference(COMMENT_KEY).child(subjectId+"").push();
                String comment_content = editTextComment.getText().toString();
                String uid = "uuid";
                String uname = userSession.getUserDetails().get(UserSession.KEY_NAME);
                String uimg = userSession.getUserDetails().get(UserSession.KEY_PHOTO)==null?"https://image.shutterstock.com/image-vector/businessman-icon-260nw-561625345.jpg":userSession.getUserDetails().get(UserSession.KEY_PHOTO);
                Comment comment = new Comment(comment_content,uid,uimg,uname);

                commentReference.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showMessage("comment added",viewGroup.getContext());
                        editTextComment.setText("");
                        btnAddComment.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showMessage("fail to add comment : "+e.getMessage(),viewGroup.getContext());
                    }
                });



            }
        });


        RvComment.setLayoutManager(new LinearLayoutManager(viewGroup.getContext()));

        DatabaseReference commentRef = firebaseDatabase.getReference(COMMENT_KEY).child(subjectId+"");
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Comment> listComment = new ArrayList<>();
                for (DataSnapshot snap:dataSnapshot.getChildren()) {

                    Comment comment = snap.getValue(Comment.class);
                    listComment.add(comment) ;

                }

                CommentAdapter commentAdapter = new CommentAdapter(viewGroup.getContext(),listComment);
                RvComment.setAdapter(commentAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void showMessage(String message, Context context) {

        Toast.makeText(context,message,Toast.LENGTH_LONG).show();

    }


    private String timestampToString(long time) {

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy",calendar).toString();
        return date;


    }



}