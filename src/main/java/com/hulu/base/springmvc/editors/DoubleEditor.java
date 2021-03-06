package com.hulu.base.springmvc.editors;

import org.springframework.beans.propertyeditors.PropertiesEditor;

/**
 * Created by huangyiwei on 15/10/15.
 */
public class DoubleEditor extends PropertiesEditor {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.equals("")) {
            text = "0";
        }
        setValue(Double.parseDouble(text));
    }

    @Override
    public String getAsText() {
        return getValue().toString();
    }
}
