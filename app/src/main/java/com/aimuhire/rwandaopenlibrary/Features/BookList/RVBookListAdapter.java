package com.aimuhire.rwandaopenlibrary.Features.BookList;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aimuhire.rwandaopenlibrary.DataStruct.Book;
import com.aimuhire.rwandaopenlibrary.Features.BookDetails.FragBookDetails;
import com.aimuhire.rwandaopenlibrary.Features.BookDetails.FragBookDetailsContainer;
import com.aimuhire.rwandaopenlibrary.Features.PDFScreen.FragPDFWebView;
import com.aimuhire.rwandaopenlibrary.MainActivity;
import com.aimuhire.rwandaopenlibrary.R;
import com.aimuhire.rwandaopenlibrary.RootConstants;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class RVBookListAdapter extends RecyclerView.Adapter<RVBookListAdapter.MyViewHolder> {

    private ArrayList<Book> books;
    private Context mContext;

    public RVBookListAdapter(Context context, ArrayList<Book> books) {
        this.books = books;
        this.mContext = context;
    }


    @NonNull
    @Override
    public RVBookListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_book_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder vholder, final int position) {

        vholder.tvTitle.setText(books.get(position).getTitle());
        vholder.tvDesciption.setText(books.get(position).getDescription());
        Glide.with(mContext).load(RootConstants.WEB_ROOT + "/covers/" + books.get(position).getCover())
                .centerCrop()
                .into(vholder.ivCover);
        vholder.cvBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) mContext;
                FragmentTransaction ft = mainActivity.getSupportFragmentManager().beginTransaction();
                ft.addToBackStack(null);

                Bundle args = new Bundle();
                args.putInt("bookId", books.get(position).getBookId());
                args.putString("title", books.get(position).getTitle());
                args.putString("description", books.get(position).getDescription());
                args.putString("author", books.get(position).getAuthor());
                args.putString("cover", books.get(position).getCover());
                args.putString("pdf", books.get(position).getPdf());
                args.putString("genre", books.get(position).getGenre());
                args.putInt("pages", books.get(position).getPages());
                args.putInt("genreId", books.get(position).getGenreId());

                FragBookDetailsContainer fragBookDetailsContainer = new FragBookDetailsContainer();
                fragBookDetailsContainer.setArguments(args);
                ft.replace(R.id.flMainContainer, fragBookDetailsContainer);
                ft.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return books.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvDesciption;
        private ImageView ivCover;
        private CardView cvBook;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesciption = itemView.findViewById(R.id.tvDescription);
            ivCover = itemView.findViewById(R.id.ivCover);
            cvBook = itemView.findViewById(R.id.cvBook);
        }
    }
}
