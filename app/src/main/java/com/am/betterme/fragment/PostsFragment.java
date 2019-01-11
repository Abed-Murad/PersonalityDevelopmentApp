package com.am.betterme.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.am.betterme.R;
import com.am.betterme.adapter.PostsAdapter;
import com.am.betterme.data.viewmodel.PostsListViewModel;
import com.am.betterme.databinding.PostsFragmentBinding;
import com.google.android.material.tabs.TabLayout;

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
    public static PostsFragment newInstance() {
        return new PostsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mContext = getContext();

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
