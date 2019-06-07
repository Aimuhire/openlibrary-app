package com.aimuhire.rwandaopenlibrary.Features.BookDetails;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aimuhire.rwandaopenlibrary.DataStruct.Comment;
import com.aimuhire.rwandaopenlibrary.R;

import java.util.ArrayList;

public class RVCommentsAdapter extends RecyclerView.Adapter<RVCommentsAdapter.ViewHolder> {
    private ArrayList<Comment> mComments;
    private Context mContext;

    public RVCommentsAdapter(@NonNull Context context, ArrayList<Comment> comments) {
        this.mComments = comments;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_item_comment, parent, false);

        ViewHolder holder = new ViewHolder(v);

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vHolder, int position) {
        vHolder.tvUsername.setText(mComments.get(position).getUsername());
        vHolder.tvMessage.setText(mComments.get(position).getMessage());
        vHolder.tvDate.setText(mComments.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvUsername;
        TextView tvMessage;
        TextView tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvDate = itemView.findViewById(R.id.tvDateTime);

        }
    }
}
