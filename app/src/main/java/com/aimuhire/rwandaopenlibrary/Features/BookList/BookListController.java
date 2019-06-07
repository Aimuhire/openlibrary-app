package com.aimuhire.rwandaopenlibrary.Features.BookList;

import android.util.Log;

import com.aimuhire.rwandaopenlibrary.DataStruct.Book;

import java.util.ArrayList;

public class BookListController {


    private ArrayList<Book> books;

    public ArrayList<Book> getBooks() {
        books = new ArrayList<>();
        Log.d("booksize", "getBooks: " + books.size());
        books.add(new Book("Ham Dam", "Ham was a real hero he kept trying until he finally made it. To this day he is considered a Russian Hero"));
        books.add(new Book("one Dam", "Ham was a real hero he kept trying until he finally made it. To this day he is considered a Russian Hero"));

        books.add(new Book("two Dam", "Ham was a real hero he kept considered a Russian Hero"));

        books.add(new Book("three Dam", "Ham was a real hero he kept trying until he finally made it. To this day he is considered a Russian Hero"));

        books.add(new Book("Ham Dam", "Ham was a real hero he kept trying until he finally made it. To this day he is considered a Russian Hero"));
        books.add(new Book("five Dam", "Ham was a real hero he kept trying until he finally made it. To this day he is considered a Russian Hero"));
        books.add(new Book("Ham Dam", "Ham was a real hero he kept trying until he finally made it. To this day he is considered a Russl hero he kept trying until he finally made it. To this day he is considered a Russian Hero"));
        books.add(new Book("seven Dam", "Ham was a real hero he kept trying until he finally made it. To this day he is considered a Russian Hero"));
        books.add(new Book("Ham Dam", "Ham was a real ho"));
        books.add(new Book("Ham Dam", "Ham "));
        books.add(new Book("Ham Dam", "Ham was a real hero he kept trying until he finally made it. To this day he is considered a Russian Hero"));

        return books;


    }

}
