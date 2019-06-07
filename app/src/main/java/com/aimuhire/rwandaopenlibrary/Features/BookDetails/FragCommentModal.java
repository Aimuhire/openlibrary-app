package com.aimuhire.rwandaopenlibrary.Features.BookDetails;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aimuhire.rwandaopenlibrary.DataStruct.Comment;
import com.aimuhire.rwandaopenlibrary.MainActivity;
import com.aimuhire.rwandaopenlibrary.R;
import com.aimuhire.rwandaopenlibrary.RootConstants;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class FragCommentModal extends Fragment {

    private RecyclerView recyclerView;
    private FragmentTransaction mFt;
    private MainActivity mainActivity;
    private EditText etComment;
    private EditText etDisplayName;

    private RecyclerView.Adapter mAdapter;
private Comment comment = new Comment();
    public FragCommentModal() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) getContext();
        mFt = mainActivity.getSupportFragmentManager().beginTransaction();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_comment, container, false);

        Button btnComment = view.findViewById(R.id.btnSend);
        etComment = view.findViewById(R.id.etComment);
        etDisplayName = view.findViewById(R.id.etDisplayName);

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getArguments() != null) {
                    comment.setBookId(getArguments().getInt("bookId"));
                    comment.setGenreId(getArguments().getInt("genreId"));
                }

                if (etComment.getText().toString().length() <1)
               etComment.setError("The comment is required");
               else if (etDisplayName.getText().toString().length() <1)
                    etDisplayName.setError("The name is required");
                else
                    postComment();
            }
        });

        Button btnCancel = view.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = mainActivity.getSupportFragmentManager().beginTransaction();
                FragBookComments fragBookComments = new FragBookComments();
                Bundle args = new Bundle();
                args.putInt("genreId", comment.getGenreId());
                args.putInt("bookId", comment.getBookId());
                fragBookComments.setArguments(args);
                ft.replace(R.id.flBookComment, fragBookComments);
                ft.commit();

            }
        });



        return view;
    }


    public boolean postComment() {

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());


        comment.setUsername(etDisplayName.getText().toString());
        comment.setMessage( etComment.getText().toString());
        comment.setDate(date);

        RequestQueue queue = Volley.newRequestQueue(getContext());

        String url = RootConstants.WEB_ROOT + "/comment";


        HashMap data = new HashMap();
        data.put("username", comment.getUsername());
        data.put("message", comment.getMessage());
        data.put("date", comment.getDate());
        data.put("genreId", comment.getGenreId());
        data.put("bookId", comment.getBookId());


        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(data),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                                                                                                                                                                                                    Toast.makeText(getContext(), "Comment added successfully!.", Toast.LENGTH_LONG).show();
                        FragmentTransaction ft = mainActivity.getSupportFragmentManager().beginTransaction();
                        FragBookComments fragBookComments = new FragBookComments();
                        Bundle args = new Bundle();
                        args.putInt("genreId", comment.getGenreId());
                        args.putInt("bookId", comment.getBookId());
                        fragBookComments.setArguments(args);
                        ft.replace(R.id.flBookComment, fragBookComments);
                        ft.commit();
                    }
                },
                new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            //here I want to post data to sever
        };
        queue.add(jsonobj);

        return false;
    }


}
