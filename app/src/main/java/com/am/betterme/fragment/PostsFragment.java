package com.am.betterme.fragment;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.am.betterme.R;
import com.am.betterme.adapter.PostsAdapter;
import com.am.betterme.data.model.Post;
import com.am.betterme.data.viewmodel.PostsListViewModel;
import com.am.betterme.databinding.PostsFragmentBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PostsFragment extends Fragment {

    private PostsListViewModel mViewModel;
    private PostsFragmentBinding mLayout;
    private Context mContext;
    private PostsAdapter mPostsAdapter;
    private CollectionReference mPostRef = FirebaseFirestore.getInstance().collection("Posts");
    private List<String> tagsList;


    public static PostsFragment newInstance() {
        return new PostsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mContext = getContext();

//        mPostRef.whereEqualTo("is_video", true).get().addOnCompleteListener(task -> {
        mPostRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Post> postList = task.getResult().toObjects(Post.class);
                mPostsAdapter.addAll(postList);

            } else {
                Log.w("ttt", "Error getting documents.", task.getException());
            }
        });
        mLayout = PostsFragmentBinding.inflate(inflater, container, false);
        setupRecyclerView();
        setupTabLayout();


        return mLayout.getRoot();
    }

    private void setupTabLayout() {
        String[] array = {"Relationships", "Motivation", "Social Skills", "Goal Setting", "Habit Building", "Career", "Health", "Family"};

        TabLayout tabLayout = mLayout.tagsTabLayout;
        tabLayout.addTab(tabLayout.newTab());
        TextView tabOne = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tab_tag, null);
        tabOne.setText("All");
        tabLayout.getTabAt(0).setCustomView(tabOne);

        for (int i = 0; i < array.length; i++) {
            tabLayout.addTab(tabLayout.newTab());
            TextView tab = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tab_tag, null);
            tab.setText(array[i]);
            tabLayout.getTabAt(i+1).setCustomView(tab);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(mContext, tab.getText(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PostsListViewModel.class);
        // TODO: Use the ViewModel

    }


    private void setupRecyclerView() {

        RecyclerView postsRecyclerView = mLayout.postsRecyclerView;
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mPostsAdapter = new PostsAdapter(mContext, (view, position, model) -> {
            PostsFragmentDirections.ActionPostsFragmentToPostDetailsFragment action = PostsFragmentDirections.actionPostsFragmentToPostDetailsFragment();
            action.setPostId(position + "");
            action.setPost(model);
            Navigation.findNavController(view).navigate(action);

        });
        postsRecyclerView.setAdapter(mPostsAdapter);
    }
//TODO: Create A Listener here to ne implemented by the main Activity so it can change the Fragment Data When ever this drawer button is clicked

}
