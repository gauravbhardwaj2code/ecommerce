package com.gaurav.commerce.activities.course.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DtoLectures implements Serializable {

    private String title="No titile";

    private List<DtoLectureContents> lectureContents=new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DtoLectureContents> getLectureContents() {
        return lectureContents;
    }

    public void setLectureContents(List<DtoLectureContents> lectureContents) {
        this.lectureContents = lectureContents;
    }
}
