package com.hulu.base.eazyui.bean;

/**
 * Created by huangyiwei on 15/10/12.
 */
public class EasyUIComboBox {

    public EasyUIComboBox(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public EasyUIComboBox(String id, String text, boolean selected) {
        this.id = id;
        this.text = text;
        this.selected = selected;
    }

    private String id;
    private String text;
    private boolean selected = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
