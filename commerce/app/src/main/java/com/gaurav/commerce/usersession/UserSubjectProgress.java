package com.gaurav.commerce.usersession;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserSubjectProgress implements Serializable {

    private String currentLectureContent;

    private String currentSubjectId;

    private Integer lectureId=0;

    private Integer totalVideos=0;

    private Set<String> coveredVideos=new HashSet<>();

    public String getCurrentLectureContent() {
        return currentLectureContent;
    }

    public void setCurrentLectureContent(String currentLectureContent) {
        this.currentLectureContent = currentLectureContent;
    }

    public Integer getLectureId() {
        return lectureId;
    }

    public void setLectureId(Integer lectureId) {
        this.lectureId = lectureId;
    }

    public Integer getTotalVideos() {
        return totalVideos;
    }

    public void setTotalVideos(Integer totalVideos) {
        this.totalVideos = totalVideos;
    }

    public Set<String > getCoveredVideos() {
        return coveredVideos;
    }

    public void setCoveredVideos(Set<String > coveredVideos) {
        this.coveredVideos = coveredVideos;
    }

    public void coveredVideo(String  i){
        coveredVideos.add(i);
    }

    public String getCurrentSubjectId() {
        return currentSubjectId;
    }

    public void setCurrentSubjectId(String currentSubjectId) {
        this.currentSubjectId = currentSubjectId;
    }
}
