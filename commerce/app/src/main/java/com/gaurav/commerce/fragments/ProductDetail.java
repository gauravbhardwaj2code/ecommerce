package com.gaurav.commerce.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gaurav.commerce.R;
import com.gaurav.commerce.activities.course.dto.DtoCart;
import com.gaurav.commerce.activities.course.dto.DtoSubjectInfo;
import com.gaurav.commerce.activities.course.dto.DtoVariantDetails;
import com.gaurav.commerce.activities.course.dto.DtoVariants;
import com.gaurav.commerce.database.util.MockDatabaseUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    public DtoSubjectInfo subjectInfo;
    public TextView priceView;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProductDetail() {
        // Required empty public constructor
    }


    public static ProductDetail newInstance(DtoSubjectInfo subjectInfo, TextView priceView) {
        ProductDetail fragment = new ProductDetail();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM2, priceView);
        fragment.setArguments(args);
        fragment.priceView=priceView;
        fragment.subjectInfo=subjectInfo;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_product_detail, container, false);

        TextView examView=root.findViewById(R.id.examName);
        examView.setText(subjectInfo.getExamName());

        TextView ratingView=root.findViewById(R.id.rating);
        ratingView.setText(String.valueOf(subjectInfo.getAverageRating()));

        TextView totalRatingView=root.findViewById(R.id.totalrating);
        totalRatingView.setText(String.valueOf(subjectInfo.getTotalRating()));

        TextView totalEnrolled=root.findViewById(R.id.totalEnrolled);
        totalEnrolled.setText(String.valueOf(subjectInfo.getTotalEnrolled())+" enrolled");

        TextView totalHours=root.findViewById(R.id.totalHours);
        totalHours.setText(String.valueOf(subjectInfo.getTotalHours()));

        TextView language=root.findViewById(R.id.language);
        language.setText(String.valueOf(subjectInfo.getLanguage()));

        TextView packageDetail=root.findViewById(R.id.packageDetail);
        packageDetail.setText(String.valueOf(subjectInfo.getPackageContent()));

        TextView description=root.findViewById(R.id.description);
        description.setText(String.valueOf(subjectInfo.getDescription()));

        TextView rating=root.findViewById(R.id.rating);
        rating.setText(String.valueOf(subjectInfo.getAverageRating()));

        ImageView roundedImageView=root.findViewById(R.id.imageurl);
        Picasso.with(getContext()).load(MockDatabaseUtil.getFacultyById(subjectInfo.getFacultyId()).getUrlImage()).into(roundedImageView);

        TextView teacher_description =root.findViewById(R.id.teacher_description);        ;
        teacher_description.setText(String.valueOf(MockDatabaseUtil.getFacultyById(subjectInfo.getFacultyId()).getDescription()));



        if(subjectInfo.getVarients()!=null && subjectInfo.getVarients().size()>0){
            createVariantLook(root,subjectInfo.getVarients());
        }


        return root;
    }

    private void createVariantLook(View root, List<DtoVariants> variants) {
        RadioGroup radioGroup=root.findViewById(R.id.radio_dates);
        for(DtoVariants dtoVariant:variants){
            RadioButton radioButton=new RadioButton(root.getContext());
            radioButton.setText(dtoVariant.getName());
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    generateVariantUi(root,dtoVariant.getDetails(),dtoVariant.getValidity());
                }
            });
            radioGroup.addView(radioButton);
        }
    }


    private void generateVariantUi(View root, List<DtoVariantDetails> details, String validity){
        RadioGroup radioGroup=root.findViewById(R.id.product_variants);
        radioGroup.removeAllViews();
        for(DtoVariantDetails dtoVariantDetails:details){
            RadioButton radioButton=new RadioButton(root.getContext());
            radioButton.setText(dtoVariantDetails.getMode());
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    priceView.setText("₹"+String.valueOf(dtoVariantDetails.getPrice()));
                    DtoCart dtoCart=new DtoCart();
                    dtoCart.setSubjectId(subjectInfo.getId());
                    dtoCart.setMode(dtoVariantDetails.getMode());
                    dtoCart.setPrice(dtoVariantDetails.getPrice());
                    dtoCart.setValidity(validity);
                    subjectInfo.setDtoCart(dtoCart);

                }
            });
            radioGroup.addView(radioButton);
        }
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
