package com.example.buddynotes.AdapterClasses;

public class EHelperClass {
    String name;
    String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public EHelperClass(String name, String url) {
        this.name = name;
        this.url = url;
    }
}