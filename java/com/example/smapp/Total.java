package com.example.smapp;

public class Total {
    private String roll;
    private String date;
    private String sub;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    Total(){

    }
    Total(String roll, String date, String sub){
        this.roll = roll;
        this.date = date;
        this.sub = sub;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }



    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}
