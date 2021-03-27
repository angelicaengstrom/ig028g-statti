package com.example.myapplication;


public class Data {

    private int value;
    private String prefix;

    public Data(int value, String prefix) {
        this.value = value;
        this.prefix = prefix;

    }
    public Data() {
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return "Row{" +
                "value=" + value +
                "prefix='" + prefix + '\'' +
                '}';
    }
}
