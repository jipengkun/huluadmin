package com.hulu.user.bean;

import java.util.Date;

/**
 * Created by huangyiwei on 15/10/23.
 */
//-- 插入一条超级管理员记录到用户表，登录账号为手机号，密码为六个1。
//INSERT INTO `t_user` (`phone`, `phone_bind`, `nick_name`, `password`, `headpic`, `register_time`, `register_source`, `name`, `sex`, `age`, `height`, `weight`, `is_admin`) VALUES ("18800000000", 2, "SuperAdmin", "96e79218965eb72c92a549dd5a330112", NULL, NOW(), NULL, "超级管理员", NULL, NULL, NULL, NULL, 2);
public class UserBean {
    public final static String USER_SESSION_KEY = "USER_SESSION_KEY";

    private int userid;

    private String phone;
    /**
     * 是否绑定手机
     */
    private String isBindPhone;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像图片名称
     */
    private String headpic;
    /**
     * 注册时间
     */
    private Date registerTime;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别 1男 0女
     */
    private String sex;

    /**
     * 年龄
     */
    private String age;

    /**
     * 身高
     */
    private String height;

    /*
     * 体重
     */
    private String weight;

    /**
     * 是否为管理员：0不是，1普通管理员，2超级管理员
     *
     */
    private Integer isAdmin;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsBindPhone() {
        return isBindPhone;
    }

    public void setIsBindPhone(String isBindPhone) {
        this.isBindPhone = isBindPhone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadpic() {
        return headpic;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }
}
