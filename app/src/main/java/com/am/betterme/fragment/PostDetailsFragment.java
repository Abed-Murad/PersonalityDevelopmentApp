package com.am.betterme.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.am.betterme.R;
import com.am.betterme.data.model.Post;
import com.am.betterme.data.viewmodel.PostDetailsViewModel;
import com.am.betterme.databinding.PostDetailsFragmentBinding;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

public class PostDetailsFragment extends Fragment {
    private static final String ARG_POST_ID = "postId";
    private static final String ARG_POST = "post";

    private String mPostId;
    private Post mPost;
    private PostDetailsViewModel mViewModel;
    private PostDetailsFragmentBinding mLayout;

    public static PostDetailsFragment newInstance() {
        return new PostDetailsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPostId = getArguments().getString(ARG_POST_ID);
            mPost = getArguments().getParcelable(ARG_POST);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mLayout = PostDetailsFragmentBinding.inflate(inflater ,  container, false);
        mLayout.setPost(mPost);
        Glide.with(getContext()).load(mPost.getImage_url()).into(mLayout.imageView3);
        return mLayout.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PostDetailsViewModel.class);
        Toast.makeText(getContext(), mPostId, Toast.LENGTH_SHORT).show();
        // TODO: Use the ViewModel
    }

}
