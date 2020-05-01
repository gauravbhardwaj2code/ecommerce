package com.lms.exam.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.lms.exam.R;
import com.lms.exam.activities.course.dto.DtoSubjectInfo;
import com.lms.exam.database.util.MockDatabaseUtil;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AbountCourse.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AbountCourse#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AbountCourse extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public DtoSubjectInfo subjectInfo;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public AbountCourse() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param2 Parameter 2.
     * @return A new instance of fragment AbountCourse.
     */
    // TODO: Rename and change types and number of parameters
    public static AbountCourse newInstance(DtoSubjectInfo subjectInfo, String param2) {
        AbountCourse fragment = new AbountCourse();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        fragment.subjectInfo = subjectInfo;
        return fragment;
    }

    public void init(DtoSubjectInfo subjectInfo) {
        this.subjectInfo = subjectInfo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_abount_course, container, false);
        TextView examView = root.findViewById(R.id.examName);
        examView.setText(subjectInfo.getExamName());

        TextView ratingView = root.findViewById(R.id.rating);
        ratingView.setText(String.valueOf(subjectInfo.getAverageRating()));

        TextView totalRatingView = root.findViewById(R.id.totalrating);
        totalRatingView.setText(String.valueOf(subjectInfo.getTotalRating()));

        TextView totalEnrolled = root.findViewById(R.id.totalEnrolled);
        totalEnrolled.setText(String.valueOf(subjectInfo.getTotalEnrolled()) + " enrolled");

        TextView totalHours = root.findViewById(R.id.totalHours);
        totalHours.setText(String.valueOf(subjectInfo.getTotalHours()));

        TextView language = root.findViewById(R.id.language);
        language.setText(String.valueOf(subjectInfo.getLanguage()));

        TextView packageDetail = root.findViewById(R.id.packageDetail);
        packageDetail.setText(String.valueOf(subjectInfo.getPackageContent()));

        TextView description = root.findViewById(R.id.description);
        description.setText(String.valueOf(subjectInfo.getDescription()));

        TextView rating = root.findViewById(R.id.rating);
        rating.setText(String.valueOf(subjectInfo.getAverageRating()));

        ImageView roundedImageView = root.findViewById(R.id.imageurl);
        Picasso.get().load(MockDatabaseUtil.getFacultyById(subjectInfo.getFacultyId()).getUrlImage()).into(roundedImageView);

        TextView teacher_description = root.findViewById(R.id.teacher_description);
        ;
        teacher_description.setText(String.valueOf(MockDatabaseUtil.getFacultyById(subjectInfo.getFacultyId()).getDescription()));


        return root;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
