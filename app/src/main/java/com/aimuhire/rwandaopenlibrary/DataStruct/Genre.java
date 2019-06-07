package com.aimuhire.rwandaopenlibrary.DataStruct;

import android.util.Log;

public class Genre {

    private int genreId;
    private String name;
    private String img;
    private int nBooks;

    public int getnBooks() {
        return nBooks;
    }

    public void setnBooks(int nBooks) {
        this.nBooks = nBooks;
    }

    public Genre(String name, String img, int nBooks) {
        this.name = name;
        this.img = img;
        this.nBooks = nBooks;

    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
