package com.example.myapplication;

public class Note {

    private String text;
    private int feeling;
    private int trainsession;

    public Note(String text, int feeling, int trainsession) {
        this.text = text;
        this.feeling = feeling;
        this.trainsession = trainsession;

    }

    public Note() {
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }

    public int getFeeling(){
        return feeling;
    }

    public void setFeeling(int feeling){
        this.feeling = feeling;
    }

    public int getTrainsession(int trainsession){
        return trainsession;
    }

    public void setTrainsession(int trainsession){
        this.trainsession = trainsession;
    }

    @Override
    public String toString(){
        return "Note{" +
                "text='" + text + "\'" +
                ", feeling=" + feeling +
                ", trainsession=" + trainsession +
                "}";
    }
}
