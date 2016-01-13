package com.hulu.base.eazyui.bean;

import java.util.List;
import java.util.Objects;

/**
 * Created by huangyiwei on 15/10/9.
 */
public class EasyUIDateGrid {
    public EasyUIDateGrid(List rows) {
        if(rows != null){
            this.total = rows.size();
            this.rows = rows;
        }
    }

    public EasyUIDateGrid(List rows, int total) {
        if(rows != null){
            this.total = total;
            this.rows = rows;
        }
    }

    private int total;
    private List rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
