package com.aimuhire.rwandaopenlibrary.Features.HomeScreen;

import android.util.Log;

import com.aimuhire.rwandaopenlibrary.DataStruct.Genre;
import com.aimuhire.rwandaopenlibrary.R;

import java.util.ArrayList;

public class HomeController {

    ArrayList<Genre> genres = new ArrayList<>();


    public ArrayList<Genre> getGenres(){
        genres.clear();
        Log.d("sizetag", "onCreateView: ctrl size"+genres.size());
        int imgId=R.drawable.genrefiller;
        genres.add(new Genre("Love","jn",132));
        genres.add(new Genre("Politics","kj",1));
        genres.add(new Genre("Biographies","jjj",9));
        genres.add(new Genre("Tales","kkk",71));
        genres.add(new Genre("History","kjjj",160));
        genres.add(new Genre("Agriculture","kjh",0));
        genres.add(new Genre("Tech","kjh",201));
        genres.add(new Genre("Economy","jh",75));
        return genres;
     }
}
