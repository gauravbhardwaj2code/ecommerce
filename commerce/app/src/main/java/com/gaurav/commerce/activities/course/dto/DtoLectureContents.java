package com.gaurav.commerce.activities.course.dto;

import java.io.Serializable;

public class DtoLectureContents  implements Serializable {

    private String title="No title";

    private Double time=0.0;

    private String url="";

    private LectureContentType lectureContentType=LectureContentType.VIDEO;

    private boolean free=false;

    public String getTitle() {
        return title;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LectureContentType getLectureContentType() {
        return lectureContentType;
    }

    public void setLectureContentType(LectureContentType lectureContentType) {
        this.lectureContentType = lectureContentType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }
}
