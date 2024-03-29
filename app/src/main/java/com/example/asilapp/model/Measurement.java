package com.example.asilapp.model;

import java.io.Serializable;
import java.util.Date;

public class Measurement implements Serializable {

    private String tool;
    private float value;
    private String user;
    private Date date;

    public Measurement(String tool, float value, String user, Date date) {
        this.tool = tool;
        this.value = value;
        this.user = user;
        this.date = date;
    }
    public static Date setDateToday(){
        return new Date();
    }

    public String getTool() {
        return tool;
    }
    public void setTool(String tool) {
        this.tool = tool;
    }

    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;
    }

    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
