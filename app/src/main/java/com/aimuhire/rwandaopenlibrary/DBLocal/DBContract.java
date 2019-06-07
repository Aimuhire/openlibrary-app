package com.aimuhire.rwandaopenlibrary.DBLocal;


import android.provider.BaseColumns;

public final class DBContract {
    // To prevent someone from accidentally instantiating the contract class,
    public static final String SQL_CREATE_ENTRIES_BOOKS =
            "CREATE TABLE " + BooksTable.TABLE_NAME + " (" +
                    BooksTable._ID + " INTEGER PRIMARY KEY," +
                    BooksTable.COLUMN_NAME_TITLE + " TEXT," +
                    BooksTable.COLUMN_NAME_AUTHOR + " TEXT," +
                    BooksTable.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    BooksTable.COLUMN_NAME_PAGES + " NUMBER," +
                    BooksTable.COLUMN_NAME_GENRE + " TEXT," +
                    BooksTable.COLUMN_NAME_PDF_PATH + " TEXT," +
                    BooksTable.COLUMN_NAME_COVER + " TEXT," +
                    BooksTable.COLUMN_NAME_BOOK_ID + " NUMBER," +
                    BooksTable.COLUMN_NAME_GENRE_ID + " NUMBER)";

    public static final String SQL_CREATE_ENTRIES_GENRES =
            "CREATE TABLE " + GenresTable.TABLE_NAME + " (" +
                    GenresTable._ID + " INTEGER PRIMARY KEY," +
                    GenresTable.COLUMN_NAME_NAME + " TEXT," +
                    GenresTable.COLUMN_NAME_BOOK_COUNT + " NUMBER," +
                    GenresTable.COLUMN_NAME_FEATURED_IMAGE + " TEXT," +
                    GenresTable.COLUMN_NAME_GENRE_ID + " NUMBER)";

    private DBContract() {
    }

    public static class BooksTable implements BaseColumns {

        public static final String TABLE_NAME = "books";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_PAGES = "pages";
        public static final String COLUMN_NAME_GENRE = "genre";
        public static final String COLUMN_NAME_PDF_PATH = "pdf_path";
        public static final String COLUMN_NAME_COVER = "cover";
        public static final String COLUMN_NAME_BOOK_ID = "bookId";
        public static final String COLUMN_NAME_GENRE_ID = "genreId";
    }


    public static class GenresTable implements BaseColumns {


        public static final String TABLE_NAME = "genres";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_BOOK_COUNT = "books_count";
        public static final String COLUMN_NAME_FEATURED_IMAGE = "featured_bg";
        public static final String COLUMN_NAME_GENRE_ID = "genreId";
    }


}
