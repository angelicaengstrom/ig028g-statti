package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class Note {

    private String text;
    private int feeling;
    private int trainsession;
    private String created;
    private String userId;
    private String trainingType;
    private List<Row> titles;

    public Note(String text, int feeling, int trainsession, String trainingType, String created, String userId, List<Row> titles) {
        this.text = text;
        this.feeling = feeling;
        this.trainsession = trainsession;
        this.created = created;
        this.trainingType = trainingType;
        this.userId = userId;
        this.titles = titles;

    }
    public Note() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getFeeling() {
        return feeling;
    }

    public void setFeeling(int feeling) {
        this.feeling = feeling;
    }

    public int getTrainsession() {
        return trainsession;
    }

    public void setTrainsession(int trainsession) {
        this.trainsession = trainsession;
    }

    public String getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(String trainingType) {
        this.trainingType = trainingType;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Row> getTitles() {return titles; }

    public void setTitles(List<Row> titles) { this.titles = titles; }

    @Override
    public String toString() {
        return "Note{" +
                "text='" + text + '\'' +
                ", feeling=" + feeling +
                ", trainsession=" + trainsession +
                ", trainingtype='" + trainingType + '\'' +
                ", created='" + created + '\'' +
                ", userId='" + userId + '\'' +
                ", titles={" + titles + '}' +
                '}';
    }
}