package com.example.parsetagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import android.view.MenuItem;


import com.example.parsetagram.fragments.compFragment;
import com.example.parsetagram.fragments.postFragment;
import com.example.parsetagram.fragments.profFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView bottomNav;
    final FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_Home:
                        fragment = new postFragment();
                        break;
                    case R.id.action_Compose:
                        fragment = new compFragment();
                        break;
                    case R.id.action_Profile:
                    default:
                        fragment = new profFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        bottomNav.setSelectedItemId(R.id.action_Home);
    }

//    void queryPosts() {
//        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
//        query.include(Post.KEY_USER);
//        query.findInBackground(new FindCallback<Post>() {
//            @Override
//            public void done(List<Post> objects, ParseException e) {
//                if(e!= null) {
//                    Log.e(TAG, "issue with getting posts", e);
//                    return;
//                }
//                for(Post p : objects) {
//                    Log.i(TAG, "post: " + p.getDescription() + ", username: " + p.getUser().getUsername());
//                }
//            }
//        });
//    }
}