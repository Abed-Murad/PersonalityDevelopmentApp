package com.am.betterme.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
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
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerFullScreenListener;
import com.am.betterme.util.FullScreenHelper;

import static com.am.betterme.util.FUNC.startShareIntent;

public class PostDetailsFragment extends Fragment {
    private static final String ARG_POST_ID = "postId";
    private static final String ARG_POST = "post";

    private String mPostId;
    private Post mPost;
    private FullScreenHelper fullScreenHelper;
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
        mLayout = PostDetailsFragmentBinding.inflate(inflater, container, false);
        mLayout.setPost(mPost);
        if (mPost.isIs_video()) {
            mLayout.youtubeView.setVisibility(View.VISIBLE);
            mLayout.postCoverImageView.setVisibility(View.INVISIBLE);
            setupYoutubeView();
        } else {
            Glide.with(getContext()).load(mPost.getImage_url()).into(mLayout.postCoverImageView);
            mLayout.postCoverImageView.setVisibility(View.VISIBLE);
            mLayout.youtubeView.setVisibility(View.INVISIBLE);

            mLayout.shareFab.setOnClickListener(v -> startShareIntent(getContext(), mPost.getTitle(), mPost.getBody()));
        }
        return mLayout.getRoot();
    }


    private void setupYoutubeView() {

        getLifecycle().addObserver(mLayout.youtubeView);
        //mToolbar , fab will be hidden and shown when FullScreen toggles
        fullScreenHelper = new FullScreenHelper(getActivity(), mLayout.shareFab);
        getLifecycle().addObserver(mLayout.youtubeView);

        mLayout.youtubeView.initialize(
                initializedYouTubePlayer ->
                        initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                            @Override
                            public void onReady() {
                                String videoId = "pS-gbqbVd8c"; //Game of Thorns - Light of the Seven
                                initializedYouTubePlayer.loadVideo(videoId, 0);
                                fullScreenHelper.enterFullScreen();
                            }
                        }), true);

        mLayout.youtubeView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
            @Override
            public void onYouTubePlayerEnterFullScreen() {
                fullScreenHelper.enterFullScreen();

            }

            @Override
            public void onYouTubePlayerExitFullScreen() {
                fullScreenHelper.exitFullScreen();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PostDetailsViewModel.class);
        Toast.makeText(getContext(), mPostId, Toast.LENGTH_SHORT).show();
        // TODO: Use the ViewModel
    }

}
