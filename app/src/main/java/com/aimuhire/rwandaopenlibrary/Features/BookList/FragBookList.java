package com.aimuhire.rwandaopenlibrary.Features.BookList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aimuhire.rwandaopenlibrary.DBLocal.DBHelper;
import com.aimuhire.rwandaopenlibrary.DataStruct.Book;
import com.aimuhire.rwandaopenlibrary.R;

import java.util.ArrayList;


public class FragBookList extends Fragment {

    private BookListController bookListController = new BookListController();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private String mGenre;
private ArrayList<Book> mBooks = new ArrayList<>();

    public FragBookList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mGenre=getArguments().getString("genre");

         }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.book_list_fragment, container, false);

        recyclerView = view.findViewById(R.id.rvBookList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RVBookListAdapter(getContext(),this.mBooks);
        recyclerView.setAdapter(mAdapter);

        return view;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DisplayLocalBooks();
    }


    private void DisplayLocalBooks() {

        Log.d("bookadded", "called: ");
        mBooks.clear();

        DBHelper dbHelper = new DBHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from books where genre=\""+this.mGenre+"\"",null);


        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String title = cursor.getString(1);
                String author = cursor.getString(2);
                String description = cursor.getString(3);
                int pages = cursor.getInt(4);
                String genre = cursor.getString(5);
                String pdfPath = cursor.getString(6);
                String cover = cursor.getString(7);
                int bookId = cursor.getInt(8);
                int genreId = cursor.getInt(9);

                Book book = new Book(title,description);
                book.setPdf(pdfPath);
                book.setAuthor(author);
                book.setPages(pages);
                book.setCover(cover);
                book.setGenre(genre);
                book.setBookId(bookId);
                book.setGenreId(genreId);

                mBooks.add(book);
                Log.d("idtrack", "DisplayLocalBooks: "+book.getTitle()+" id " +book.getBookId());
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();



        mAdapter.notifyDataSetChanged();


    }
}
