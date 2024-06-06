package com.example.smapp;

public class ToDb {
    String roll;
    String sec;
    String sub;
    String status;
    String total;

    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    ToDb() {
    }

    ToDb(String roll, String sec, String sub,String date, String status, String total) {
        this.roll = roll;
        this.sec = sec;
        this.sub = sub;
        this.date = date;
        this.status = status;
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

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
