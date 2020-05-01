package com.lms.exam.activities.ui.mycourses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyCoursesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyCoursesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}