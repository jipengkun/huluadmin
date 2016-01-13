package com.hulu.user.bean;

/**
 * Created by huangyiwei on 15/10/26.
 */
public enum UserTypeEnum {
    /**
     * 普通用户
     */
    COMMONLY(0),

    /**
     * 普通管理员
     */
    ADMIN(1),

    /**
     * 超级管理员
     */
    SUPER_ADMIN(2);

    private int value = 0;

    public int val(){
        return this.value;
    }

    UserTypeEnum(int value){
        this.value = value;
    }
}
