package com.am.betterme.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.am.betterme.R;
import com.am.betterme.adapter.PostsAdapter;
import com.am.betterme.data.model.Post;
import com.am.betterme.databinding.ActivitySearchResultsBinding;
import com.am.betterme.fragment.PostsFragmentDirections;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.logger.Logger;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import static com.am.betterme.util.CONST.POSTS_KEY;

public class SearchResultsActivity extends AppCompatActivity {
    ActivitySearchResultsBinding mLayout;
    private PostsAdapter mPostsAdapter;
    private CollectionReference mPostsCollection = FirebaseFirestore.getInstance().collection(POSTS_KEY);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayout = DataBindingUtil.setContentView(this, R.layout.activity_search_results);
        setupRecyclerView();
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        handleIntent(getIntent());
    }
    private void setupRecyclerView() {
        mLayout.postsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPostsAdapter = new PostsAdapter(this, (view, position, model) -> {
            PostsFragmentDirections.ActionPostsFragmentToPostDetailsFragment action = PostsFragmentDirections.actionPostsFragmentToPostDetailsFragment();
            action.setPost(model);
            Navigation.findNavController(view).navigate(action);
        });
        mLayout.postsRecyclerView.setAdapter(mPostsAdapter);
        searchAndGetPosts();
    }

    private void searchAndGetPosts() {
        mPostsCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Post> postList = task.getResult().toObjects(Post.class);
                mPostsAdapter.addAll(postList);

            } else {
                Logger.e("Error getting documents.", task.getException());
            }
        });
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            showResults(query);
        }

    }

    private void showResults(String query) {
        Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
    }
}
