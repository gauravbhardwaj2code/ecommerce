package com.lms.exam.usersession;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserSubjectInfo implements Serializable {

    Map<String, UserSubjectProgress> allSubjects = new HashMap<>();

    public boolean containSubject(Object id) {
        if (id != null) {
            return allSubjects.containsKey(id.toString());
        }
        return false;

    }

    public Map<String, UserSubjectProgress> getAllSubjects() {
        return allSubjects;
    }

    public void setAllSubjects(Map<String, UserSubjectProgress> allSubjects) {
        this.allSubjects = allSubjects;
    }

    public UserSubjectProgress getSubjectById(Object id) {
        return allSubjects.get(id.toString());
    }

    public void setSubject(String key, UserSubjectProgress subjectProgress) {
        allSubjects.put(key, subjectProgress);
    }
}
