package com.aimuhire.rwandaopenlibrary.Features.HomeScreen;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aimuhire.rwandaopenlibrary.DataStruct.Genre;
import com.aimuhire.rwandaopenlibrary.Features.BookList.FragBookList;
import com.aimuhire.rwandaopenlibrary.MainActivity;
import com.aimuhire.rwandaopenlibrary.R;
import com.aimuhire.rwandaopenlibrary.RootConstants;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RVMainCategoryAdapter extends RecyclerView.Adapter<RVMainCategoryAdapter.MyViewHolder> {

    private ArrayList<Genre> genres;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvTitle;
        public TextView tvNbooks;
        public ImageView ivImage;
        public CardView cvGenre;

        public MyViewHolder(View vItem) {
            super(vItem);

            tvTitle = vItem.findViewById(R.id.tvCatTitle);
            tvNbooks = vItem.findViewById(R.id.tvNbooks);
            ivImage = vItem.findViewById(R.id.ivCatImage);
            cvGenre = vItem.findViewById(R.id.cvGenre);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RVMainCategoryAdapter(Context context, ArrayList<Genre> genres) {
        this.mContext = context;
        this.genres = genres;
        Log.d("sizetag", "onCreateView: size got " + genres.size());
        Log.d("sizetag", "onCreateView: sizemember " + this.genres.size());

    }

    // Create new views (invoked by the layout manager)
    @Override
    public RVMainCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_genre_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tvTitle.setText(genres.get(position).getName());
        //holder.ivImage.setBackgroundResource(genres.get(position).getImg());
        Glide.with(mContext)
                .load(RootConstants.WEB_ROOT+"/covers/"+genres.get(position).getImg())
                 .centerCrop()
                .into(holder.ivImage);
        int nBooks = genres.get(position).getnBooks();
        if (nBooks == 1)
            holder.tvNbooks.setText("1 book");
        else if (nBooks > 1)
            holder.tvNbooks.setText(nBooks + " books");
        else
            holder.tvNbooks.setText("No books found");


        holder.cvGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putString("genre",genres.get(position).getName());

                MainActivity mainActivity = (MainActivity) mContext;
                FragmentTransaction ft = mainActivity.getSupportFragmentManager().beginTransaction();
                FragBookList fragBookList =new FragBookList();
                fragBookList.setArguments(args);
                ft.addToBackStack(null);
                ft.replace(R.id.flMainContainer, fragBookList);
                ft.commit();
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return genres.size();
    }


}