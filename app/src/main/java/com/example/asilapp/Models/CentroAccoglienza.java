package com.example.asilapp.Models;

public class CentroAccoglienza {
    private String id;
    private String name;
    private String valueRanking;
    private String image;
    private String city;
    private String address;
    private String phone;
    private String openingTime;
    private String norma;
    private String rule;
    private String description;

    public CentroAccoglienza(String id, String name, String valueRanking, String image, String city, String address, String phone, String openingTime, String norma, String rule, String description) {
        this.id = id;
        this.name = name;
        this.valueRanking = valueRanking;
        this.image = image;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.openingTime = openingTime;
        this.norma = norma;
        this.rule = rule;
        this.description = description;
    }

    public CentroAccoglienza(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValueRanking() {
        return valueRanking;
    }

    public void setValueRanking(String valueRanking) {
        this.valueRanking = valueRanking;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getNorma() {
        return norma;
    }

    public void setNorma(String norma) {
        this.norma = norma;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Center{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", valueRanking='" + valueRanking + '\'' +
                ", image='" + image + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", openingTime='" + openingTime + '\'' +
                ", norma='" + norma + '\'' +
                ", rule='" + rule + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

