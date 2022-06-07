package com.example.news;

public class News {
    private String title;
    private String text;

    public News(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public News() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
