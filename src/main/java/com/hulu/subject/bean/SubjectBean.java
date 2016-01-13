package com.hulu.subject.bean;

import org.codehaus.jackson.annotate.JsonTypeInfo;

import java.util.Date;

/**
 * Created by huangyiwei on 15/10/9.
 */
public class SubjectBean {
    public static final String SUBJECT_ALL_LIST = "hulu_admin.com.hulu.subject.bean.SubjectBean.ALL_SUBJECT_LIST";

    private Integer id;
    private String subjectName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
