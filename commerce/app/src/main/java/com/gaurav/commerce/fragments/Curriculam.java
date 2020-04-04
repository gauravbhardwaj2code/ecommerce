package com.gaurav.commerce.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaurav.commerce.R;
import com.gaurav.commerce.activities.course.dto.AllPlayersWrapper;
import com.gaurav.commerce.activities.course.dto.DtoSubjectInfo;
import com.gaurav.commerce.activities.ui.home.CourcesViewHandler;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.firebase.database.FirebaseDatabase;

import bg.devlabs.fullscreenvideoview.FullscreenVideoView;

import static com.gaurav.commerce.database.constants.DatabaseConstants.COURSES;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Curriculam.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Curriculam#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Curriculam extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "play_allowed";
    public DtoSubjectInfo subjectInfo;
    public FullscreenVideoView fullscreenVideoView;
    public AllPlayersWrapper youTubePlayer;

    // TODO: Rename and change types of parameters
    private Boolean PLAY_ALLOWED;

    private OnFragmentInteractionListener mListener;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public Curriculam() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Curriculam.
     */
    // TODO: Rename and change types and number of parameters
    public static Curriculam newInstance(DtoSubjectInfo subjectInfo, Boolean playAllowed,
                                         FullscreenVideoView fullscreenVideoView,
                                         AllPlayersWrapper youTubePlayer) {
        Curriculam fragment = new Curriculam();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM2, playAllowed);
        fragment.setArguments(args);
        fragment.subjectInfo=subjectInfo;
        fragment.youTubePlayer=youTubePlayer;
        fragment.fullscreenVideoView=fullscreenVideoView;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            PLAY_ALLOWED = getArguments().getBoolean(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View root=inflater.inflate(R.layout.fragment_curriculam, container, false);
       // TextView lecture_hours=root.findViewById(R.id.lecture_hours);
      //  lecture_hours.setText(String.valueOf(subjectInfo.getTotalHours())+" Hours");
        new CurriculamRecycleViewHandler(root.findViewById(R.id.lectures_list),R.layout.recycleview_curriculam,
                subjectInfo.getLectures(),inflater,PLAY_ALLOWED,fullscreenVideoView,youTubePlayer,subjectInfo.getId());
        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
