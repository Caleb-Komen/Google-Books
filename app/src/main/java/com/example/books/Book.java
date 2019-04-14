package com.example.books;

public class Book {

    String author, title, datePublished;
    String imageUrl, url;

    public Book(String author, String title, String imageUrl, String datePublished, String url) {
        this.author = author;
        this.title = title;
        this.imageUrl = imageUrl;
        this.datePublished = datePublished;
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public String getUrl() {
        return url;
    }
}
