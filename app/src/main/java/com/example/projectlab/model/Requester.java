package com.example.projectlab.model;

public class Requester {
    private String Cover;
    private String Title;
    private String Author;
    private String Requester;
    private String Receiver;

    public Requester(String cover, String title, String author, String requester, String receiver) {
        this.Cover = cover;
        this.Title = title;
        this.Author = author;
        this.Requester = requester;
        this.Receiver = receiver;
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

    public String getRequester() {
        return Requester;
    }

    public void setRequester(String requester) {
        this.Requester = requester;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        this.Receiver = receiver;
    }
}
