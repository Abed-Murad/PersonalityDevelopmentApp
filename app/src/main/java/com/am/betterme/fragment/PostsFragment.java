package com.am.betterme.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.am.betterme.adapter.PostsAdapter;
import com.am.betterme.data.model.Post;
import com.am.betterme.data.viewmodel.PostsListViewModel;
import com.am.betterme.databinding.PostsFragmentBinding;
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

import static com.am.betterme.util.CONST.POSTS_KEY;
import static com.am.betterme.util.CONST.TAGS_ARRAY;

public class PostsFragment extends Fragment {

    private PostsListViewModel mViewModel;
    private PostsFragmentBinding mLayout;
    private Context mContext;
    private PostsAdapter mPostsAdapter;
    private CollectionReference mPostRef = FirebaseFirestore.getInstance().collection(POSTS_KEY);
    String bb = "";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mLayout = PostsFragmentBinding.inflate(inflater, container, false);
        setupRecyclerView();
        setupTabLayout();

        return mLayout.getRoot();
    }

    //TODO: FIX The OnClick Bug
    private void setupTabLayout() {
        mLayout.tagsTabLayout.setTags(TAGS_ARRAY);
        mLayout.tagsTabLayout.setOnTagClickListener(tag -> Toast.makeText(getActivity(), tag, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PostsListViewModel.class);
        // TODO: Use the ViewModel
        Toast.makeText(mContext, bb, Toast.LENGTH_SHORT).show();


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
        mPostRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Post> postList = task.getResult().toObjects(Post.class);
                mPostsAdapter.addAll(postList);

            } else {
                Logger.e("Error getting documents.", task.getException());
            }
        });
    }

    private void getVideosOnly() {

        mPostRef.whereEqualTo("is_video", true).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Post> postList = task.getResult().toObjects(Post.class);
                mPostsAdapter.addAll(postList);

            } else {
                Logger.e("Error getting documents.", task.getException());
            }
        });
    }

    private void getArticlesOnly() {
        mPostRef.whereEqualTo("is_video", false).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Post> postList = task.getResult().toObjects(Post.class);
                mPostsAdapter.addAll(postList);

            } else {
                Logger.e("Error getting documents.", task.getException());
            }
        });
    }


//TODO: Create A Listener here to ne implemented by the main Activity so it can change the Fragment Data When ever this drawer button is clicked

}
