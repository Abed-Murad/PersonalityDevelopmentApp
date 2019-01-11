package com.am.betterme.fragment;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.am.betterme.R;
import com.am.betterme.adapter.PostsAdapter;
import com.am.betterme.data.model.Post;
import com.am.betterme.data.viewmodel.PostsListViewModel;
import com.am.betterme.databinding.PostsFragmentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.api.LogDescriptor;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.orhanobut.logger.Logger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PostsFragment extends Fragment {

    private PostsListViewModel mViewModel;
    private PostsFragmentBinding mLayout;
    private Context mContext;
    private CollectionReference mPostRef = FirebaseFirestore.getInstance().collection("Posts");
    public static PostsFragment newInstance() {
        return new PostsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        mPostRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d("ttt", document.getId() + " => " + document.getData());
                    Post post = document.toObject(Post.class);
                    Logger.d(post);
                }
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
        TabLayout tabLayout = mLayout.tagsTabLayout;
        tabLayout.addTab(tabLayout.newTab());
        TextView tabOne = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tab_tag, null);
        tabOne.setText("All");
        tabLayout.getTabAt(0).setCustomView(tabOne);

        for (int i = 1; i < 10; i++) {
            tabLayout.addTab(tabLayout.newTab());
            TextView tab = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tab_tag, null);
            tab.setText("Self Steam");
            tabLayout.getTabAt(i).setCustomView(tab);
        }

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
        postsRecyclerView.setAdapter(new PostsAdapter(mContext, (view, position, model) -> {
            PostsFragmentDirections.ActionPostsFragmentToPostDetailsFragment action = PostsFragmentDirections.actionPostsFragmentToPostDetailsFragment();
            action.setPostId(position + "");
            Navigation.findNavController(view).navigate(action);

        }));
    }



}
