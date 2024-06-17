package com.example.asilapp.Models;

public class CentroAccoglienza {
    private String id;
    private String name;
    private String valueRanking;
    private String image;
    private String city;
    private String province;
    private String region;
    private String address;
    private String phone;
    private String openingTime;
    private String norma;
    private String rule;
    private String description;
    private String email;

    public CentroAccoglienza(){}

    public CentroAccoglienza(String id, String name, String valueRanking, String image, String city, String province, String region, String address, String phone, String openingTime, String norma, String rule, String description, String email) {
        this.id = id;
        this.name = name;
        this.valueRanking = valueRanking;
        this.image = image;
        this.city = city;
        this.province = province;
        this.region = region;
        this.address = address;
        this.phone = phone;
        this.openingTime = openingTime;
        this.norma = norma;
        this.rule = rule;
        this.description = description;
        this.email = email;
    }

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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "CentroAccoglienza{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", valueRanking='" + valueRanking + '\'' +
                ", image='" + image + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", region='" + region + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", openingTime='" + openingTime + '\'' +
                ", norma='" + norma + '\'' +
                ", rule='" + rule + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

