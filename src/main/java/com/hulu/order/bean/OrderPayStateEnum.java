package com.hulu.order.bean;

/**
 * Created by huangyiwei on 15/11/3.
 */
public enum OrderPayStateEnum {
    UN_PAY("未支付",3),

    PAYING("支付中",4),

    SUCCESS("支付成功",1),

    FAIL("支付失败",2);

    private String name;
    private int value;

    OrderPayStateEnum(String name,int value){
        this.name = name;
        this.value = value;
    }

    public String getName(){
        return this.name;
    }

    public int val(){
        return this.value;
    }
}
