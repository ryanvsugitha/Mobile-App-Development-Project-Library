package com.example.projectlab.model;

import com.example.projectlab.R;

public class Book {

    private String Cover;
    private String Title;
    private String Author;
    private String Synopsis;

    public Book(String cover, String title, String author, String synopsis) {
        this.Cover = cover;
        this.Title = title;
        this.Author = author;
        this.Synopsis = synopsis;
    }

    public String getCover() {
        return Cover;
    }

    public void setCover(String cover) {
        this.Cover = cover;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        this.Author = author;
    }

    public String getSynopsis() {
        return Synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.Synopsis = synopsis;
    }
}

