package com.example.abdallaah.todolist.App.Model;


import java.text.DateFormat;
import java.util.Date;

public class ToDo {

    private int id;
    private String title;
    private String dateCreated;
    private String dateRemind;
    private String description;

    public ToDo(String title, String dateCreated, String dateRemind, String description, int id) {
        this.title = title;
        if(dateCreated != null){
            this.dateCreated = dateCreated;
        }
        else{
            this.dateCreated = DateFormat.getDateTimeInstance().format(new Date());
        }
        this.dateRemind = dateRemind;
        this.description = description;
        this.id = id;
    }

    public int getId() {
        return id;
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

    public String getDateRemind() {
        return dateRemind;
    }

}
