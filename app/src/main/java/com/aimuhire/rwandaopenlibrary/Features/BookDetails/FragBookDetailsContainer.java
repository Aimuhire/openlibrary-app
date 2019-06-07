package com.aimuhire.rwandaopenlibrary.Features.BookDetails;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aimuhire.rwandaopenlibrary.MainActivity;
import com.aimuhire.rwandaopenlibrary.R;

public class FragBookDetailsContainer extends Fragment {


    MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    mainActivity = (MainActivity) getContext();

        View view=inflater.inflate(R.layout.book_detail_container,container,false);
        mainActivity = (MainActivity) getContext();
        FragmentTransaction ft = mainActivity.getSupportFragmentManager().beginTransaction();
        FragBookDetails fragBookDetails= new FragBookDetails();
        FragBookComments fragBookComments = new FragBookComments();

        fragBookDetails.setArguments(getArguments());
        ft.add(R.id.flBookDetailMain,fragBookDetails);
        fragBookComments.setArguments(getArguments());
        ft.add(R.id.flBookComment, fragBookComments);
        ft.commit();

        return view;
    }



}

