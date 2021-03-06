package com.lms.exam.database.util;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lms.exam.activities.course.dto.DtoFaculty;
import com.lms.exam.activities.course.dto.DtoLectureContents;
import com.lms.exam.activities.course.dto.DtoLectures;
import com.lms.exam.activities.course.dto.DtoSubjectInfo;
import com.lms.exam.activities.ui.home.dto.DtoHomePageBanner;
import com.lms.exam.activities.ui.home.view.HomeCoursesRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lms.exam.database.constants.DatabaseConstants.ALL_LECTURES;
import static com.lms.exam.database.constants.DatabaseConstants.BANNER_DATA_BASE_NAME;
import static com.lms.exam.database.constants.DatabaseConstants.FACULTY;
import static com.lms.exam.database.constants.DatabaseConstants.H_LECTURES;
import static com.lms.exam.database.constants.DatabaseConstants.SUBJECT_DATA_BASE_NAME;
import static com.lms.exam.database.constants.DatabaseConstants.V_LECTURES;

public class MockDatabaseUtil {


    public static final String PRODUCTS = "products";
    public static Map<String, DtoSubjectInfo> subjectInfoMap = new HashMap<>();
    public static Map<String, HomeCoursesRecyclerView> adapters = new HashMap<>();
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static Map<String, List<Long>> inetrnalDatabaseIds = new HashMap<>();
    private static Map<String, DtoFaculty> facultyMap = new HashMap<>();

    public static void createHomePageBanner() {
        DatabaseReference myRef = database.getReference();

        Map<String, Object> productModelMap = new HashMap<>();
        productModelMap.put("1", new DtoHomePageBanner()
                .setName("First")
                .setImage("https://www.examonline.org/estatic/homeimages/cma-inter-group-2.webp")
                .setCategory("1"));

        productModelMap.put("2", new DtoHomePageBanner()
                .setName("First")
                .setImage("https://www.examonline.org/estatic/homeimages/cma-inter-group-2.webp")
                .setCategory("1"));

        productModelMap.put("3", new DtoHomePageBanner()
                .setName("First")
                .setImage("https://www.examonline.org/estatic/homeimages/cma-inter-group-2.webp")
                .setCategory("1"));

        productModelMap.put("4", new DtoHomePageBanner()
                .setName("First")
                .setImage("https://www.examonline.org/estatic/homeimages/cma-inter-group-2.webp")
                .setCategory("1"));

        myRef.child(BANNER_DATA_BASE_NAME).updateChildren(productModelMap);
    }

    public static void createHomePageBestSellingBanner(String db_name) {

        DatabaseReference myRef = database.getReference();

        Map<String, Object> productModelMap = new HashMap<>();
        productModelMap.put(PRODUCTS, Arrays.asList(1, 2, 3, 4));
        myRef.child(db_name).updateChildren(productModelMap);

    }

    public static void createAllDatabase() {
        createHomePageBestSellingBanner(V_LECTURES);
        createHomePageBestSellingBanner(H_LECTURES);
        createHomePageBestSellingBanner(ALL_LECTURES);
        createFaculty(FACULTY);
        DatabaseReference myRef = database.getReference();
        Map<String, Object> subjectInfoMap = new HashMap<>();

        DtoLectures dtoLectures = new DtoLectures();
        DtoLectureContents dtoLectureContents = new DtoLectureContents();
        dtoLectures.setLectureContents(Arrays.asList(dtoLectureContents, new DtoLectureContents(), new DtoLectureContents()));

        subjectInfoMap.put("1", new DtoSubjectInfo().setId(1)
                .setCourseId(1)
                .setName("Subject 1")
                .setDemoVideoUrl(Arrays.asList("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"))
                .setUrlImage("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg")
                .setFacultyId("1")
                .setLectures(Arrays.asList(dtoLectures, dtoLectures))
                .setExamName("CBSE XII"));
        subjectInfoMap.put("2", new DtoSubjectInfo().setId(2)
                .setCourseId(1)
                .setName("Subject 2")
                .setDemoVideoUrl(Arrays.asList("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"))
                .setUrlImage("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg")
                .setFacultyId("1")
                .setLectures(Arrays.asList(dtoLectures, dtoLectures))
                .setExamName("CBSE XII"));
        subjectInfoMap.put("3", new DtoSubjectInfo().setId(3)
                .setCourseId(1)
                .setName("Subject 3")
                .setDemoVideoUrl(Arrays.asList("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"))
                .setUrlImage("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg")
                .setFacultyId("1")
                .setLectures(Arrays.asList(dtoLectures, dtoLectures))
                .setExamName("CBSE XII"));
        subjectInfoMap.put("4", new DtoSubjectInfo().setId(4)
                .setCourseId(1)
                .setName("Subject 4")
                .setDemoVideoUrl(Arrays.asList("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"))
                .setUrlImage("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg")
                .setFacultyId("1")
                .setLectures(Arrays.asList(dtoLectures, dtoLectures))
                .setExamName("CBSE XII"));


        myRef.child(SUBJECT_DATA_BASE_NAME).updateChildren(subjectInfoMap);


    }

    private static void createFaculty(String faculty) {
        DatabaseReference myRef = database.getReference();
        Map<String, Object> productModelMap = new HashMap<>();

        productModelMap.put("1", new DtoFaculty().setName("Faculty name")
                .setDescription("Faculty Description")
                .setUrlImage("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg")
                .setId(1));

        myRef.child(faculty).updateChildren(productModelMap);

    }

    public static void initDatabase() {

        if (inetrnalDatabaseIds.size() > 0) {
            return;
        }

        initFaculty(FACULTY);

        List<Long> allCourseId = new ArrayList<>();

        List<Long> popularCourses = new ArrayList<>();

        DatabaseReference databaseReference = database.getReference().child(SUBJECT_DATA_BASE_NAME);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    try {
                        DtoSubjectInfo subjectInfo = data.getValue(DtoSubjectInfo.class);
                        subjectInfoMap.put(subjectInfo.getId().toString(), subjectInfo);
                        allCourseId.add(subjectInfo.getId().longValue());
                        if (subjectInfo.getCategory() != null &&
                                subjectInfo.getCategory().equalsIgnoreCase("popular")) {
                            popularCourses.add(subjectInfo.getId().longValue());
                        }
                    } catch (Exception e) {
                        System.out.println(data.getValue());
                        e.printStackTrace();
                    }
                }

                inetrnalDatabaseIds.put(ALL_LECTURES, allCourseId);
                inetrnalDatabaseIds.put(H_LECTURES, popularCourses);


                Collections.sort(allCourseId);
                List<Long> latestCourses = new ArrayList<Long>(allCourseId.subList(allCourseId.size() - 5, allCourseId.size()));
                inetrnalDatabaseIds.put(V_LECTURES, latestCourses);

                notifyDatabases(V_LECTURES);
                notifyDatabases(H_LECTURES);
                notifyDatabases(ALL_LECTURES);
            }

            private void notifyDatabases(String name) {
                if (adapters.containsKey(name)) {
                    adapters.get(name).updateList();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    private static void initFaculty(String faculty) {
        DatabaseReference databaseReference = database.getReference().child(faculty);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    DtoFaculty dtoFaculty = data.getValue(DtoFaculty.class);
                    facultyMap.put(dtoFaculty.getName(), dtoFaculty);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }


    public static DtoFaculty getFacultyById(String id) {
        return facultyMap.get(id);
    }

    public static Map<Integer, DtoSubjectInfo> getAllSubjectByCourseId(Integer courseId) {
        Map<Integer, DtoSubjectInfo> map = new HashMap<>();
        for (String id : subjectInfoMap.keySet()) {
            DtoSubjectInfo dtoSubjectInfo = subjectInfoMap.get(id);
            if (dtoSubjectInfo.getCourseId().toString().equalsIgnoreCase(courseId.toString())) {
                map.put(Integer.parseInt(id), dtoSubjectInfo);
            }
        }
        return map;

    }

    public static Map<Integer, DtoSubjectInfo> getSubjectInfoMap(List<Long> ids) {
        Map<Integer, DtoSubjectInfo> map = new HashMap<>();
        if (null != ids && ids.size() > 0) {
            for (Long id : ids) {
                map.put(id.intValue(), subjectInfoMap.get(id.toString()));
            }
        }
        return map;
    }

    public static DtoSubjectInfo getSubjectInfoById(Long id) {
        return subjectInfoMap.get(id.toString());
    }

    public static List<Long> getInternalDatabaseIds(String name) {
        return inetrnalDatabaseIds.get(name);
    }
}
