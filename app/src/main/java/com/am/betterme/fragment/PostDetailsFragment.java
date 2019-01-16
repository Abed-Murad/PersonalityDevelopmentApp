package com.am.betterme.fragment;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.am.betterme.data.model.Post;
import com.am.betterme.data.viewmodel.PostDetailsViewModel;
import com.am.betterme.databinding.PostDetailsFragmentBinding;
import com.am.betterme.databinding.VideoDetailsFragmentBinding;
import com.bumptech.glide.Glide;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerFullScreenListener;
import com.am.betterme.util.FullScreenHelper;

import static com.am.betterme.util.FUNC.startShareIntentForArticle;

public class PostDetailsFragment extends Fragment {
    private static final String ARG_POST_ID = "postId";
    private static final String ARG_POST = "post";

    private String mPostId;
    private Post mPost;
    private FullScreenHelper fullScreenHelper;
    private PostDetailsViewModel mViewModel;
    ViewDataBinding mLayout;

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
        if (mPost.isIs_video()) {
            VideoDetailsFragmentBinding mLayout;
            mLayout = VideoDetailsFragmentBinding.inflate(inflater, container, false);
            mLayout.youtubeView.setVisibility(View.VISIBLE);
            setupYoutubeView(mLayout);
            mLayout.setPost(mPost);
            return mLayout.getRoot();

        } else {
            PostDetailsFragmentBinding mLayout;
            mLayout = PostDetailsFragmentBinding.inflate(inflater, container, false);
            Glide.with(getContext()).load(mPost.getImage_url()).into(mLayout.postCoverImageView);
            mLayout.postCoverImageView.setVisibility(View.VISIBLE);
            mLayout.shareFab.setOnClickListener(v -> startShareIntentForArticle(getContext(), mPost.getTitle(), mPost.getBody()));
            mLayout.setPost(mPost);
            return mLayout.getRoot();

        }
    }


    private void setupYoutubeView(VideoDetailsFragmentBinding mLayout) {
        fullScreenHelper = new FullScreenHelper(getActivity(), mLayout.dateTextView, mLayout.titleTextView);
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
        mLayout.youtubeView.getPlayerUIController().showFullscreenButton(false);

/*
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
*/
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PostDetailsViewModel.class);
        Toast.makeText(getContext(), mPostId, Toast.LENGTH_SHORT).show();
        // TODO: Use the ViewModel
    }

}
