package com.hulu.diagnoseTime.bean;

import com.hulu.doctor.bean.DoctorBean;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;

/**
 * Created by huangyiwei on 15/10/14.
 */
public class DiagnoseTimeBean {
    /**
     * id
     */
    private Integer id;
    /**
     * 医生ID
     */
    private Integer doctorId;
    /**
     * 医生
     */
    private DoctorBean doctorBean;
    /**
     * 出诊日期
     */
    private Date visDate;
    /**
     * 出诊时间
     * 1上午 2下午 3全天
     */
    private Integer visTime;
    /**
     * 每次出诊可挂号数量
     */
    private Integer totality;
    /**
     * 号码余额
     */
    private Integer numBalance;
    /**
     * 更新时间
     */
    private Date updateTime;

    //================= 临时字段===================

    /**
     * 该出诊记录的第一个预约单
     */
    private Date firstOrderTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Date getVisDate() {
        return visDate;
    }

    public void setVisDate(Date visDate) {
        this.visDate = visDate;
    }

    public Integer getVisTime() {
        return visTime;
    }

    public void setVisTime(Integer visTime) {
        this.visTime = visTime;
    }

    public Integer getTotality() {
        return totality;
    }

    public void setTotality(Integer totality) {
        this.totality = totality;
    }

    public Integer getNumBalance() {
        return numBalance;
    }

    public void setNumBalance(Integer numBalance) {
        this.numBalance = numBalance;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public DoctorBean getDoctorBean() {
        return doctorBean;
    }

    public void setDoctorBean(DoctorBean doctorBean) {
        this.doctorBean = doctorBean;
    }

    public Date getFirstOrderTime() {
        return firstOrderTime;
    }

    public void setFirstOrderTime(Date firstOrderTime) {
        this.firstOrderTime = firstOrderTime;
    }
}
