package com.hulu.order.bean;

import com.hulu.user.bean.UserBean;

import java.util.Date;

/**
 * Created by huangyiwei on 15/10/20.
 */
public class OrderBean {
    /**
     * id
     */
    private Integer id;

    /**
     * 订单流水
     */
    private String orderSequence;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户
     */
    private UserBean userBean;

    /**
     * 出诊时间id
     */
    private Integer timeId;

    /**
     * 预约人id
     */
    private Integer orderPersonId;

    /**
     * 医生id
     */
    private Integer doctorId;

    /**
     * 预约号
     */
    private Integer orderNum;

    /**
     * 订单金额
     */
    private Double amount;

    /**
     * 支付状态：1支付成功 2支付失败 3未支付 4支付中
     */
    private Integer paystat;

    /**
     * 订单创建时间
     */
    private Date createTime;

    /**
     * 订单支付完成时间
     */
    private Date payTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderSequence() {
        return orderSequence;
    }

    public void setOrderSequence(String orderSequence) {
        this.orderSequence = orderSequence;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTimeId() {
        return timeId;
    }

    public void setTimeId(Integer timeId) {
        this.timeId = timeId;
    }

    public Integer getOrderPersonId() {
        return orderPersonId;
    }

    public void setOrderPersonId(Integer orderPersonId) {
        this.orderPersonId = orderPersonId;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getPaystat() {
        return paystat;
    }

    public void setPaystat(Integer paystat) {
        this.paystat = paystat;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
}
