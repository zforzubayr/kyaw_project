package com.example.abdallaah.todolist.App;


public class ToDo {
    private String title;
    private String dateCreated;
    private String dateRemind;
    private String description;

    public ToDo(String title, String dateCreated, String dateRemind, String description) {
        this.title = title;
        this.dateCreated = dateCreated;
        this.dateRemind = dateRemind;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateRemind() {
        return dateRemind;
    }

    public void setDateRemind(String dateRemind) {
        this.dateRemind = dateRemind;
    }
}
