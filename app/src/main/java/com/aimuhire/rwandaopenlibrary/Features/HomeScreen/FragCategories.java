package com.aimuhire.rwandaopenlibrary.Features.HomeScreen;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aimuhire.rwandaopenlibrary.DBLocal.DBContract;
import com.aimuhire.rwandaopenlibrary.DBLocal.DBHelper;
import com.aimuhire.rwandaopenlibrary.DataStruct.Book;
import com.aimuhire.rwandaopenlibrary.DataStruct.Genre;
import com.aimuhire.rwandaopenlibrary.R;
import com.aimuhire.rwandaopenlibrary.RootConstants;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FragCategories extends Fragment {
private ProgressBar pbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Genre> genres = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        pbar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.rvCatMain);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getGenresRemote();

        mAdapter = new RVMainCategoryAdapter(getContext(), this.genres);


        recyclerView.setAdapter(mAdapter);
        return view;


    }


    public void getGenresRemote() {


        final ArrayList cList = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = RootConstants.WEB_ROOT+"/genres";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        JSONArray mJsonArray = null;
                        try {

                            mJsonArray = new JSONArray(response);

                            final JSONArray finalMJsonArray = mJsonArray;
                            getContext().deleteDatabase("rwandaopenlibdb");
                            for (int i = 0; i < finalMJsonArray.length(); i++) {
                                JSONObject jsObject = null;
                                try {
                                    jsObject = finalMJsonArray.getJSONObject(i);

                                    JSONArray books = finalMJsonArray.getJSONObject(i).getJSONArray("books");
                                    saveAllBooksLocal(books,i);

                                    Genre genre = new Genre(jsObject.getString("genre"), jsObject.getString("featuredImg"), books.length());
                                    genre.setGenreId(i);
                                    Log.d("idtrack", "genre id set to: " + i + " getid " + genre.getGenreId());
                                    saveGenreLocal(genre);

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }
                            }

                            DisplayLocalGenres();
                            return;


                        } catch (JSONException e) {
                        //    e.printStackTrace();
                            DisplayLocalGenres();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



                DisplayLocalGenres();

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    private void saveAllBooksLocal(JSONArray books,int genreId) {

        for (int i = 0; i < books.length(); i++) {
            try {
                JSONObject jBook = books.getJSONObject(i);

                Book book = new Book(jBook.getString("title"), jBook.getString("description"));
                book.setPages(jBook.getInt("pages"));
                book.setAuthor(jBook.getString("author"));
                book.setCover(jBook.getString("cover"));
                book.setPdf(jBook.getString("pdfPath"));
                book.setGenre(jBook.getString("genre"));
                book.setBookId(i);
                book.setGenreId(genreId);
                saveBookLocal(book);
            } catch (JSONException e) {
                e.printStackTrace();

            }


        }
    }

    private long saveGenreLocal(Genre genre) {
        Log.d("idtrack", "genre to save getid " + genre.getGenreId());

        DBHelper dbHelper = new DBHelper(getContext());

// Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();


// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DBContract.GenresTable.COLUMN_NAME_NAME, genre.getName());
        values.put(DBContract.GenresTable.COLUMN_NAME_BOOK_COUNT, genre.getnBooks());
        values.put(DBContract.GenresTable.COLUMN_NAME_FEATURED_IMAGE, genre.getImg());
        values.put(DBContract.GenresTable.COLUMN_NAME_GENRE_ID, genre.getGenreId());
// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DBContract.GenresTable.TABLE_NAME, null, values);
        db.close();
        if (newRowId > 0) {
            return newRowId;
        }


        return -1;

    }

    private void DisplayLocalGenres() {
        genres.clear();

        DBHelper dbHelper = new DBHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from genres", null);


        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(1);
                int booksCount = Integer.parseInt(cursor.getString(2));
                String featuredBgFile = cursor.getString(3);
                int genreId = Integer.parseInt(cursor.getString(4));

                Genre genre = new Genre(name, featuredBgFile, booksCount);
                genre.setGenreId(genreId);
                Log.d("idtrack", "DisplayLocalBooks: " + genre.getName() + " id " + genre.getGenreId());
                genres.add(genre);

                cursor.moveToNext();
            }
            cursor.close();
            db.close();

        }

if(genres.size()>0)
    pbar.setVisibility(View.GONE);
        mAdapter.notifyDataSetChanged();


    }

    private long saveBookLocal(Book book) {
        Log.d("datatrack", "Books saver: ");
        DBHelper dbHelper = new DBHelper(getContext());

// Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();


// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DBContract.BooksTable.COLUMN_NAME_TITLE, book.getTitle());
        values.put(DBContract.BooksTable.COLUMN_NAME_AUTHOR, book.getAuthor());
        values.put(DBContract.BooksTable.COLUMN_NAME_PAGES, book.getPages());
        values.put(DBContract.BooksTable.COLUMN_NAME_COVER, book.getCover());
        values.put(DBContract.BooksTable.COLUMN_NAME_PDF_PATH, book.getPdf());
        values.put(DBContract.BooksTable.COLUMN_NAME_DESCRIPTION, book.getDescription());
        values.put(DBContract.BooksTable.COLUMN_NAME_GENRE, book.getGenre());
        values.put(DBContract.BooksTable.COLUMN_NAME_BOOK_ID, book.getBookId());
        values.put(DBContract.BooksTable.COLUMN_NAME_GENRE_ID, book.getGenreId());
// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DBContract.BooksTable.TABLE_NAME, null, values);

        if (newRowId > 0) {
            return newRowId;
        }
        Log.d("datatrack", "book not saved saver: ");

        return -1;

    }
}
