package com.example.asilapp.model;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String name;
    private String surname;
    private String placeBirth;
    private String birthDate;
    private String country;
    private String email;
    private String pw;
    private int center_id;
    private String phone;
    private String gender;

    public User(int id, String name, String surname, String placeBirth, String birthDate, String country, String email, String pw, int center_id, String phone, String gender) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.placeBirth = placeBirth;
        this.birthDate = birthDate;
        this.country = country;
        this.email = email;
        this.pw = pw;
        this.center_id = center_id;
        this.phone = phone;
        this.gender = gender;
    }


    public User() {
        this.id = 0;
        this.name = "name";
        this.surname = "surname";
        this.placeBirth = "placeBirth";
        this.birthDate = "birthDate";
        this.country = "country";
        this.email = "email";
        this.pw = "pw";
        this.center_id = 0;
        this.phone = "phone";
        this.gender = "gender";
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPlaceBirth() {
        return placeBirth;
    }
    public void setPlaceBirth(String placeBirth) {
        this.placeBirth = placeBirth;
    }

    public String getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }
    public void setPw(String pw) {
        this.pw = pw;
    }

    public int getCenter_id() {
        return center_id;
    }
    public void setCenter_id(int center_id) {
        this.center_id = center_id;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", country='" + country + '\'' +
                ", email='" + email + '\'' +
                ", pw='" + pw + '\'' +
                ", center_id=" + center_id +
                '}';
    }
}
