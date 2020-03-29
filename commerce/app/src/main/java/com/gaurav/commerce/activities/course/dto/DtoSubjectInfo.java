package com.gaurav.commerce.activities.course.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DtoSubjectInfo  implements Serializable {

    private Integer id;

    private Integer courseId;

    private String name;

    private String urlImage;

    private String demoVideoUrl;

    private String examName;

    private Double averageRating=0.0;

    private Double sellingPrice=0.0;

    private Double costPrice=0.0;

    private Integer totalEnrolled=0;

    private String language="Hindi";

    private Double totalHours=0.0;

    private Integer facultyId;

    private String description="description here";

    private List<String> packageContent=Arrays.asList("Videos");

    private Integer totalRating=0;

    private List<Integer> allRating= Arrays.asList(0,0,0,0,0);

    private List<DtoLectures> lectures=new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public DtoSubjectInfo setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getExamName() {
        return examName;
    }

    public DtoSubjectInfo setExamName(String examName) {
        this.examName = examName;
        return this;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public DtoSubjectInfo setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
        return this;
    }

    public Integer getTotalEnrolled() {
        return totalEnrolled;
    }

    public DtoSubjectInfo setTotalEnrolled(Integer totalEnrolled) {
        this.totalEnrolled = totalEnrolled;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public DtoSubjectInfo setLanguage(String language) {
        this.language = language;
        return this;
    }

    public Double getTotalHours() {
        return totalHours;
    }

    public DtoSubjectInfo setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
        return this;
    }

    public Integer getFacultyId() {
        return facultyId;
    }

    public DtoSubjectInfo setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DtoSubjectInfo setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<String> getPackageContent() {
        return packageContent;
    }

    public DtoSubjectInfo setPackageContent(List<String> packageContent) {
        this.packageContent = packageContent;
        return this;
    }

    public Integer getTotalRating() {
        return totalRating;
    }

    public DtoSubjectInfo setTotalRating(Integer totalRating) {
        this.totalRating = totalRating;
        return this;
    }

    public List<Integer> getAllRating() {
        return allRating;
    }

    public DtoSubjectInfo setAllRating(List<Integer> allRating) {
        this.allRating = allRating;
        return this;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public DtoSubjectInfo setCourseId(Integer courseId) {
        this.courseId = courseId;
        return this;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public DtoSubjectInfo setUrlImage(String urlImage) {
        this.urlImage = urlImage;
        return this;
    }

    public String getDemoVideoUrl() {
        return demoVideoUrl;
    }

    public DtoSubjectInfo setDemoVideoUrl(String demoVideoUrl) {
        this.demoVideoUrl = demoVideoUrl;
        return this;
    }

    public String getName() {
        return name;
    }

    public DtoSubjectInfo setName(String name) {
        this.name = name;
        return this;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public DtoSubjectInfo setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
        return this;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public DtoSubjectInfo setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
        return this;
    }

    public List<DtoLectures> getLectures() {
        return lectures;
    }

    public DtoSubjectInfo setLectures(List<DtoLectures> lectures) {
        this.lectures = lectures;
        return this;
    }

    @Override
    public String toString() {
        return "DtoSubjectInfo{" +
                "id=" + id +
                ", examName='" + examName + '\'' +
                ", averageRating=" + averageRating +
                ", totalEnrolled=" + totalEnrolled +
                ", language='" + language + '\'' +
                ", totalHours=" + totalHours +
                ", facultyId=" + facultyId +
                ", description='" + description + '\'' +
                ", packageContent=" + packageContent +
                ", totalRating=" + totalRating +
                ", allRating=" + allRating +
                '}';
    }
}
