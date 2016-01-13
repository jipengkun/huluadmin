package com.hulu.hospital.bean;

import org.codehaus.jackson.annotate.JsonTypeInfo;

import java.util.Date;

/**
 * Created by huangyiwei on 15/10/9.
 */
public class HospitalBean {
    public final static String HOSPITAL_ALL_LIST = "hulu_admin.com.hulu.hospital.bean.HospitalBean.HOSPITAL_ALL_LIST";

    private Integer id;
    private String name;
    private String address;
    private String description;
    private String arbitration;
    private Date updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getArbitration() {
        return arbitration;
    }

    public void setArbitration(String arbitration) {
        this.arbitration = arbitration;
    }
}
