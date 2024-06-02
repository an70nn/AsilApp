package com.example.asilapp.Models;

import java.io.Serializable;
import java.util.Date;

public class Measurement {
    private String userID;
    private String date;
    private String time;
    private String value;
    private String category;

    public Measurement(){}

    public Measurement(String userID, String data, String time, String value, String category) {
        this.userID = userID;
        this.date = data;
        this.time = time;
        this.value = value;
        this.category = category;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "userID='" + userID + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
