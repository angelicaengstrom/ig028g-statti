package com.example.myapplication;

import com.google.firebase.Timestamp;

public class Row {

    private String title;
    private String userId;
    private Timestamp created;

    public Row(String title, String userId, Timestamp created) {
        this.title = title;
        this.userId = userId;
        this.created = created;

    }
    public Row() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Row{" +
                "title='" + title + '\'' +
                "userId='" + userId + '\'' +
                ", created=" + created +
                '}';
    }
}