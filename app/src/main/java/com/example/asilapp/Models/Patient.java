package com.example.asilapp.Models;

public class Patient {
    private String name;
    private String surname;
    private String gender;
    private String birthPlace;
    private String birthDate;
    private String country;
    private String phone;
    private String centerID;
    private String email;
    private String password;

    public Patient(String name, String surname, String birthPlace, String birthDate, String country, String email, String password, String centerID, String phone, String gender) {
        this.name = name;
        this.surname = surname;
        this.birthPlace = birthPlace;
        this.birthDate = birthDate;
        this.country = country;
        this.email = email;
        this.password = password;
        this.centerID = centerID;
        this.phone = phone;
        this.gender = gender;
    }


    public Patient() {
        this.name = "name";
        this.surname = "surname";
        this.birthPlace = "birthPlace";
        this.birthDate = "birthDate";
        this.country = "country";
        this.email = "email";
        this.password = "password";
        this.centerID = "CenterID";
        this.phone = "phone";
        this.gender = "gender";
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

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
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

    public String getCenterID() {
        return centerID;
    }
    public void setCenterID(String centerID) {
        this.centerID = centerID;
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

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", country='" + country + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", centerID=" + centerID +
                '}';
    }
}
