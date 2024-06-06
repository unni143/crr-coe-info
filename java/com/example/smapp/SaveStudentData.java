package com.example.smapp;

public class SaveStudentData {
    private String sub;
    private String roll;
    private String total;
    private String date;

    public SaveStudentData(){

    }

    public SaveStudentData(String count){
        roll = count;
    }

    public SaveStudentData(String sub, String roll, String total, String date){
        this.date = date;
        this.sub = sub;
        this.roll = roll;
        this.total = total;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
