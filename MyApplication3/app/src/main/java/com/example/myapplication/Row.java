package com.example.myapplication;

import java.util.List;

public class Row {

    private String title;
    private List<Data> titleItems;

    public Row(String title, List<Data> titleItems) {
        this.title = title;
        this.titleItems = titleItems;
    }

    public void addTitleItem(Data item) {
        titleItems.add(item);
    }

    public String getTitle() {
        return this.title;
    }

    public List<Data> getTitleItems(){ return this.titleItems; }


}