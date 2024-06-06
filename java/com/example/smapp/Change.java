package com.example.smapp;

import java.io.Serializable;

public class Change implements Serializable {

    private String text;
    private boolean isSelected = false;

    /*public Change(String text) {
        this.text = text;
    }*/

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public boolean isSelected() {
        return isSelected;
    }
}
