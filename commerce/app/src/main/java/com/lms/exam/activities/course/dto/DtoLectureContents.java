package com.lms.exam.activities.course.dto;

import java.io.Serializable;

public class DtoLectureContents implements Serializable {

    private String title = "No title";

    private Object time = "0.0";

    private String url = "";

    private LectureContentType lectureContentType = LectureContentType.VIDEO;

    private String free = "false";

    private String downloadable;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getTime() {
        return time;
    }

    public void setTime(Object time) {
        this.time = time;
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

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getDownloadable() {
        return downloadable;
    }

    public void setDownloadable(String downloadable) {
        this.downloadable = downloadable;
    }
}
