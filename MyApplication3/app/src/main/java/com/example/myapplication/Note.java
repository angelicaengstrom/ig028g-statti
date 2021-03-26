package com.example.myapplication;

import com.google.firebase.Timestamp;

public class Note {

    private String text;
    private int feeling;
    private int trainsession;
    private Timestamp created;
    private String userId;

    public Note(String text, int feeling, int trainsession, Timestamp created, String userId) {
        this.text = text;
        this.feeling = feeling;
        this.trainsession = trainsession;
        this.created = created;
        this.userId = userId;

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

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Note{" +
                "text='" + text + '\'' +
                ", feeling=" + feeling +
                ", trainsession=" + trainsession +
                ", created=" + created +
                ", userId='" + userId + '\'' +
                '}';
    }
}