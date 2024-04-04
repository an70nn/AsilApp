package com.example.asilapp.model;

import java.io.Serializable;

public class User implements Serializable {

    private static int counter = 0;

    private int id;
    private String name;
    private String surname;
    private String gender;
    private String birthPlace;
    private String birthDate;
    private String country;
    private String phone;
    private String center_id;
    private String email;
    private String password;

    public User(String name, String surname, String birthPlace, String birthDate, String country, String email, String password, String center_id, String phone, String gender) {

        this.id = counter;
        this.name = name;
        this.surname = surname;
        this.birthPlace = birthPlace;
        this.birthDate = birthDate;
        this.country = country;
        this.email = email;
        this.password = password;
        this.center_id = center_id;
        this.phone = phone;
        this.gender = gender;

        counter++;
    }


    public User() {
        this.id = 0;
        this.name = "name";
        this.surname = "surname";
        this.birthPlace = "birthPlace";
        this.birthDate = "birthDate";
        this.country = "country";
        this.email = "email";
        this.password = "password";
        this.center_id = "0";
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
        return birthPlace;
    }
    public void setPlaceBirth(String placeBirth) {
        this.birthPlace = placeBirth;
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

    public String getCenter_id() {
        return center_id;
    }
    public void setCenter_id(String center_id) {
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

    public String getPassword() {
        return password;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public void setPassword(String password) {
        this.password = password;
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
                ", password='" + password + '\'' +
                ", center_id=" + center_id +
                '}';
    }
}
