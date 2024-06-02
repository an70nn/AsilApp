package com.example.asilapp.Models;

import java.io.Serializable;
import java.util.Date;

public class Transaction {
    private String userID;
    private String title;
    private String category;
    private String price;
    private String date;

    public Transaction(){}

    public Transaction(String userID, String title, String category, String price, String date) {
        this.userID = userID;
        this.title = title;
        this.date = date;
        this.category = category;
        this.price = price;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "userID='" + userID + '\'' +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", price='" + price + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
