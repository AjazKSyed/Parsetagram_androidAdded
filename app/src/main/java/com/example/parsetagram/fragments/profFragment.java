 package com.example.parsetagram.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.parsetagram.LoginActivity;
import com.example.parsetagram.Post;
import com.example.parsetagram.PostAdapter;
import com.example.parsetagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

 public class profFragment extends postFragment {

    private Button btnLogout;
    private final String TAG = "profFragment";
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView rvPosts;
    protected PostAdapter adapter;
    protected List<Post> allPosts;

     // onCreateView to inflate the view
     @Nullable
     @Override
     public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         View view = inflater.inflate(R.layout.fragment_post, container, false);

         swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);

         swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
             @Override
             public void onRefresh() {
                 queryPosts();
             }
         });
         return view;
     }
     @Override
     public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
         btnLogout = view.findViewById(R.id.btnLogout);
         btnLogout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 goBackToLogin();
             }
         });

         rvPosts = view.findViewById(R.id.rvPosts);

         allPosts = new ArrayList<>();

         adapter = new PostAdapter(getContext(), allPosts);

         rvPosts.setAdapter(adapter);

         rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));

         queryPosts();
     }

    private void goBackToLogin() {
        Log.d(TAG, "Navigating to Register Activity");
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
    }

     protected void queryPosts() {
         ParseQuery<Post> Query = ParseQuery.getQuery(Post.class);

         Query.include(Post.KEY_USER);
         Query.setLimit(20);
         Query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
         Query.addDescendingOrder(Post.KEY_CREATED_AT);
         Query.findInBackground(new FindCallback<Post>() {
             @Override
             public void done(List<Post> posts, ParseException e) {
                 if(e != null){
                     Log.d(TAG, "Error with query");
                     return;
                 }
                 adapter.clear();
                 adapter.addAll(posts);
                 swipeContainer.setRefreshing(false);

                 for(int i = 0; i <posts.size(); i++){
                     Post p = posts.get(i);
                     Log.d(TAG, "Post: " + p.getDescription() + " username: " + p.getUser().getUsername());
                 }

             }
         });
     }

}