package com.example.asilapp.Models;

public class Rating {
    private String centerId;
    private float rating;
    private String comment;

    public Rating() {    }

    public Rating(String centerId, float rating, String comment) {
        this.centerId = centerId;
        this.rating = rating;
        this.comment = comment;
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
