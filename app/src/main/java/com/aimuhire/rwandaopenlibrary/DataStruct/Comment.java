package com.aimuhire.rwandaopenlibrary.DataStruct;

public class Comment {
    private String username;
    private String message;
    private String date;
    private int bookId;
    private int genreId;
    public Comment(String name, String message, String date) {
        this.username = name;
        this.message = message;
        this.date = date;
    }

    public Comment() {

    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
