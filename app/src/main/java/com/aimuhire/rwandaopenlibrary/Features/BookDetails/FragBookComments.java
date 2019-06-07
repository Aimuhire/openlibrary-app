package com.aimuhire.rwandaopenlibrary.Features.BookDetails;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aimuhire.rwandaopenlibrary.DataStruct.Book;
import com.aimuhire.rwandaopenlibrary.DataStruct.Comment;
import com.aimuhire.rwandaopenlibrary.DataStruct.Genre;
import com.aimuhire.rwandaopenlibrary.Features.PDFScreen.FragPDFWebView;
import com.aimuhire.rwandaopenlibrary.MainActivity;
import com.aimuhire.rwandaopenlibrary.R;
import com.aimuhire.rwandaopenlibrary.RootConstants;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragBookComments extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    MainActivity mainActivity;
    private Book mBook = new Book();

    private TextView etNoComments;
    private  ArrayList<Comment> mComments = new ArrayList<>();
    FragCommentModal fragCommentModal = new FragCommentModal();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBook.setBookId(getArguments().getInt("bookId"));
            mBook.setGenreId(getArguments().getInt("genreId"));
             getCommentsRemote();
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

        View view = inflater.inflate(R.layout.book_details_comment_fragment, container, false);



        recyclerView = view.findViewById(R.id.rvComments);

        etNoComments =view.findViewById(R.id.tvNoComments);

                recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.srlRefreshComments);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCommentsRemote();
          swipeRefreshLayout.setRefreshing(false);
            }
        });
        mAdapter = new RVCommentsAdapter(getContext(), mComments);
        recyclerView.setAdapter(mAdapter);

        return view;
    }




    public void getCommentsRemote() {



        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = RootConstants.WEB_ROOT+"/comments/"+mBook.getGenreId()+"/"+mBook.getBookId();
        Log.d("cocoma", "onResponse: was here");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mComments.clear();
                        Log.d("cocoma", "onResponse: here too");
                        JSONArray mJsonArray = null;
                        try {

                            mJsonArray = new JSONArray(response);

                            final JSONArray finalMJsonArray = mJsonArray;
                            for (int i = 0; i < finalMJsonArray.length(); i++) {
                                JSONObject jsObject = null;
                                try {

                                    JSONObject jsComment = finalMJsonArray.getJSONObject(i);
                                    Comment cmt = new Comment();
                                    cmt.setUsername(jsComment.getString("username"));

                                    cmt.setDate(jsComment.getString("date"));
                                    cmt.setMessage(jsComment.getString("message"));

                                     mComments.add(cmt);
                                    Log.d("cocoma", "onResponse: "+cmt.getMessage());
                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }
                            }
if(mComments.size()>0)
    etNoComments.setVisibility(View.GONE);
else
    etNoComments.setVisibility(View.VISIBLE);

                            mAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                             e.printStackTrace();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {




            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

}
