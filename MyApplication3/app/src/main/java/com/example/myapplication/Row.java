package com.example.myapplication;

import java.util.List;

public class Row {

    private String title;
    private List<String> titleItems;

    public Row(String title, List<String> titleItems) {
        this.title = title;
        this.titleItems = titleItems;
    }

    public String getTitle() {
        return this.title;
    }

    public List<String> getTitleItems(){ return this.titleItems; }


}