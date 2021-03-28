package com.example.myapplication;


public class Data {

    private String value;
    private String prefix;

    public Data(String value, String prefix) {
        this.value = value;
        this.prefix = prefix;

    }
    public Data() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
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
