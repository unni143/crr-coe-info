package com.example.smapp;

public class AttClass {
    private String absent;
    private String date;
    private String sec;
    private String sub;
    private String total;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public AttClass(){}
    public AttClass(String absent, String date, String sec, String sub,String type, String total){
        this.absent = absent;
        this.date = date;
        this.sec = sec;
        this.sub = sub;
        this.total = total;
        this.type = type;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAbsent() {
        return absent;
    }

    public void setAbsent(String absent) {
        this.absent = absent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}
