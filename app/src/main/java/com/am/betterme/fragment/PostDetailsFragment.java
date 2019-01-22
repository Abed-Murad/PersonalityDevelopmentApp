package com.am.betterme.fragment;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import static com.am.betterme.util.CONST.TAGS_ARRAY;
import static com.am.betterme.util.CONST.TEST_VIDEO_ID;
import static com.am.betterme.util.FUNC.shareArticle;

public class PostDetailsFragment extends Fragment {
    private static final String ARG_POST = "post";

    private PostDetailsViewModel mViewModel;

    private Post mPost;
    private AppCompatActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();
        if (getArguments() != null) mPost = getArguments().getParcelable(ARG_POST);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mPost.isIs_video()) {
            VideoDetailsFragmentBinding mVideoLayout = setupVideoLayout(inflater, container);
            return mVideoLayout.getRoot();

        } else {
            PostDetailsFragmentBinding mPostLayout = setupPostLayout(inflater, container);
            return mPostLayout.getRoot();

        }
    }

    private PostDetailsFragmentBinding setupPostLayout(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        PostDetailsFragmentBinding mLayout = PostDetailsFragmentBinding.inflate(inflater, container, false);
        Glide.with(mActivity).load(mPost.getImage_url()).into(mLayout.postCoverImageView);
        mLayout.postCoverImageView.setVisibility(View.VISIBLE);
        mLayout.shareFab.setOnClickListener(v -> shareArticle(mActivity, mLayout.bodyTextView.getText().toString(), mPost.getBody()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mLayout.bodyTextView.setText(Html.fromHtml(mPost.getBody(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            mLayout.bodyTextView.setText(Html.fromHtml(mPost.getBody()));
        }
        mLayout.setPost(mPost);
        return mLayout;
    }

    private VideoDetailsFragmentBinding setupVideoLayout(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        VideoDetailsFragmentBinding mLayout = VideoDetailsFragmentBinding.inflate(inflater, container, false);
        mLayout.youtubeView.setVisibility(View.VISIBLE);
        setupYoutubeView(mLayout);
        mLayout.tagsTabLayout.setTags(TAGS_ARRAY);
        mLayout.tagsTabLayout.setOnTagClickListener(tag -> Toast.makeText(mActivity, tag, Toast.LENGTH_SHORT).show());
        mLayout.setPost(mPost);
        return mLayout;
    }

    private void setupYoutubeView(VideoDetailsFragmentBinding mLayout) {

        getLifecycle().addObserver(mLayout.youtubeView);
        mLayout.youtubeView.getPlayerUIController().showFullscreenButton(false);

        mLayout.youtubeView.initialize(
                initializedYouTubePlayer ->
                        initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                            @Override
                            public void onReady() {
                                initializedYouTubePlayer.loadVideo(TEST_VIDEO_ID, 0);
                            }
                        }), true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PostDetailsViewModel.class);
        // TODO: Use the ViewModel
    }

}
