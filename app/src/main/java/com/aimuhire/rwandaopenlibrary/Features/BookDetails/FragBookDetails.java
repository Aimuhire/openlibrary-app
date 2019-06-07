package com.aimuhire.rwandaopenlibrary.Features.BookDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aimuhire.rwandaopenlibrary.DataStruct.Book;
import com.aimuhire.rwandaopenlibrary.Features.PDFScreen.FragPDFWebView;
import com.aimuhire.rwandaopenlibrary.MainActivity;
import com.aimuhire.rwandaopenlibrary.R;
import com.aimuhire.rwandaopenlibrary.RootConstants;
import com.bumptech.glide.Glide;

public class FragBookDetails extends Fragment {
    MainActivity mainActivity;
    private Book mBook = new Book();
    FragCommentModal fragCommentModal = new FragCommentModal();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBook.setBookId(getArguments().getInt("bookId"));
            mBook.setTitle(getArguments().getString("title"));
            mBook.setAuthor(getArguments().getString("author"));
            mBook.setCover(getArguments().getString("cover"));
            mBook.setPdf(getArguments().getString("pdf"));
            mBook.setGenre(getArguments().getString("genre"));
            mBook.setDescription(getArguments().getString("description"));
            mBook.setPages(getArguments().getInt("pages"));
            mBook.setGenreId(getArguments().getInt("genreId"));


        }

        mainActivity = (MainActivity) getContext();
        FragmentTransaction ft = mainActivity.getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putInt("genreId", mBook.getGenreId());
        args.putInt("bookId", mBook.getBookId());
        fragCommentModal.setArguments(args);
        ft.add(R.id.flMainContainer, fragCommentModal);
        ft.addToBackStack(null);
        ft.hide(fragCommentModal);
        ft.commit();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.book_details_fragment, container, false);

        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvGenre = view.findViewById(R.id.tvGenre);
        TextView tvAuthor = view.findViewById(R.id.tvAuthor);
        TextView tvDescription = view.findViewById(R.id.tvDescription);
        ImageView ivCover = view.findViewById(R.id.ivCoverBig);
        Button btnComment = view.findViewById(R.id.btnComment);

        Button btnShare = view.findViewById(R.id.btnShare);
        Button btnReadNow = view.findViewById(R.id.btnReadNow);
        tvTitle.setText(mBook.getTitle());
        tvAuthor.setText(mBook.getAuthor());
        tvGenre.setText(mBook.getGenre());
        tvDescription.setText(mBook.getDescription());
        Glide.with(getContext()).load(RootConstants.WEB_ROOT + "/covers/" + mBook.getCover())
                .centerCrop()
                .into(ivCover);


        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = mainActivity.getSupportFragmentManager().beginTransaction();
                Bundle args = new Bundle();
                args.putInt("genreId",mBook.getGenreId());
                args.putInt("bookId",mBook.getBookId());
                FragCommentModal fragCommentModal= new FragCommentModal();
                fragCommentModal.setArguments(args);
                ft.replace(R.id.flBookComment,fragCommentModal );
                ft.commit();


            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey! Check out " + mBook.getTitle() + " a Rwanda Open Library book at " + RootConstants.WEB_ROOT + "/pdfs/" + mBook.getPdf());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        ivCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPdfFragment();
            }
        });
        btnReadNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openPdfFragment();

            }
        });


        return view;
    }

    private void openPdfFragment() {
        MainActivity mainActivity = (MainActivity) getContext();
        FragmentTransaction ft = mainActivity.getSupportFragmentManager().beginTransaction();
        FragPDFWebView fragPDFWebView = new FragPDFWebView();
        Bundle args = new Bundle();
        args.putString("pdf", mBook.getPdf());
        fragPDFWebView.setArguments(args);

        ft.addToBackStack(null);
        ft.replace(R.id.flMainContainer, fragPDFWebView);
        ft.commit();
    }
}
