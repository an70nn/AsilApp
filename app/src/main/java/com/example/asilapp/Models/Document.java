package com.example.asilapp.Models;

public class Document {
    private String id;
    private String name;
    private String link;
    private String buttonShare;

    public Document(){}

    public Document(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public Document(String id, String name, String link, String buttonShare) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.buttonShare = buttonShare;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getButtonShare() {
        return buttonShare;
    }

    public void setButtonShare(String buttonShare) {
        this.buttonShare = buttonShare;
    }
}
