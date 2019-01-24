package com.am.betterme.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.am.betterme.R;
import com.am.betterme.activity.MainActivity;
import com.am.betterme.adapter.PostsAdapter;
import com.am.betterme.data.model.Post;
import com.am.betterme.data.viewmodel.PostsListViewModel;
import com.am.betterme.databinding.PostsFragmentBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.logger.Logger;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import static com.am.betterme.util.CONST.ALL;
import static com.am.betterme.util.CONST.CAREER;
import static com.am.betterme.util.CONST.FAMILY;
import static com.am.betterme.util.CONST.GOAL_SETTING;
import static com.am.betterme.util.CONST.HABIT_BUILDING;
import static com.am.betterme.util.CONST.HEALTH;
import static com.am.betterme.util.CONST.MOTIVATION;
import static com.am.betterme.util.CONST.POSTS_KEY;
import static com.am.betterme.util.CONST.RELATIONSHIPS;
import static com.am.betterme.util.CONST.SOCIAL_SKILLS;
import static com.am.betterme.util.CONST.TAGS_ARRAY;

public class PostsFragment extends Fragment implements MainActivity.OnPostsCategoryChangeListener {

    private PostsListViewModel mViewModel;
    private PostsFragmentBinding mLayout;
    private Context mContext;
    private PostsAdapter mPostsAdapter;
    private CollectionReference mPostRef = FirebaseFirestore.getInstance().collection(POSTS_KEY);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        ((MainActivity) getActivity()).setOnDataListener(this);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mLayout = PostsFragmentBinding.inflate(inflater, container, false);
        setupRecyclerView();
        setupTabLayout();
        return mLayout.getRoot();
    }

    private void setupTabLayout() {
        for (int i = 0; i < TAGS_ARRAY.length; i++) {
            TextView customTab = (TextView) LayoutInflater.from(mContext).inflate(R.layout.custom_tab_layout, null);//get custom view
            customTab.setText(TAGS_ARRAY[i]);//set text over view
            TabLayout.Tab tab = mLayout.tagsTabLayout.getTabAt(i);//get tab via position
            if (tab != null)
                tab.setCustomView(customTab);//set custom view
        }
        mLayout.tagsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                Toast.makeText(mContext, tabPosition + "", Toast.LENGTH_SHORT).show();
                getPostsWithTag(tabPosition);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void getPostsWithTag(int tabPosition) {
        switch (tabPosition) {
            case ALL:
                break;
            case RELATIONSHIPS:
                break;
            case MOTIVATION:
                break;
            case SOCIAL_SKILLS:
                break;
            case CAREER:
                break;
            case GOAL_SETTING:
                break;
            case HABIT_BUILDING:
                break;
            case HEALTH:
                break;
            case FAMILY:
                break;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PostsListViewModel.class);
        // TODO: Use the ViewModel
    }


    private void setupRecyclerView() {

        mLayout.postsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mPostsAdapter = new PostsAdapter(mContext, (view, position, model) -> {
            PostsFragmentDirections.ActionPostsFragmentToPostDetailsFragment action = PostsFragmentDirections.actionPostsFragmentToPostDetailsFragment();
            action.setPost(model);
            Navigation.findNavController(view).navigate(action);
        });
        mLayout.postsRecyclerView.setAdapter(mPostsAdapter);
        getAllPosts();
    }

    private void getAllPosts() {
        mLayout.progressBar.setVisibility(View.VISIBLE);

        mPostRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Post> postList = task.getResult().toObjects(Post.class);
                mPostsAdapter.addAll(postList);
                mLayout.progressBar.setVisibility(View.INVISIBLE);
            } else {
                Logger.e("Error getting documents.", task.getException());
            }
        });
    }

    private void getVideosOnly() {
        mLayout.progressBar.setVisibility(View.VISIBLE);

        mPostRef.whereEqualTo("isVideo", true).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Post> postList = task.getResult().toObjects(Post.class);
                mPostsAdapter.addAll(postList);
                mLayout.progressBar.setVisibility(View.INVISIBLE);

            } else {
                Logger.e("Error getting documents.", task.getException());
            }
        });
    }

    private void getArticlesOnly() {
        mLayout.progressBar.setVisibility(View.VISIBLE);

        mPostRef.whereEqualTo("isVideo", false).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Post> postList = task.getResult().toObjects(Post.class);
                mPostsAdapter.addAll(postList);
                mLayout.progressBar.setVisibility(View.INVISIBLE);

            } else {
                Logger.e("Error getting documents.", task.getException());
            }
        });
    }


    @Override
    public void onChange(int category) {
        switch (category) {
            case R.id.navAll:
                getAllPosts();
                break;
            case R.id.navArticles:
                getArticlesOnly();
                break;
            case R.id.navVideos:
                getVideosOnly();
                break;
        }
    }
}
