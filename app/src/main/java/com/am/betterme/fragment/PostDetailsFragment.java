package com.am.betterme.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import me.gujun.android.taggroup.TagGroup;

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

import static com.am.betterme.util.FUNC.startShareIntentForArticle;

public class PostDetailsFragment extends Fragment {
    private static final String ARG_POST_ID = "postId";
    private static final String ARG_POST = "post";

    private String mPostId;
    private Post mPost;
    private PostDetailsViewModel mViewModel;
    private AppCompatActivity mActivity;

    public static PostDetailsFragment newInstance() {
        return new PostDetailsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();
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
            String[] array = {"All","Relationships", "Motivation", "Social Skills", "Goal Setting", "Habit Building", "Career", "Health", "Family"};
            mLayout.tagsTabLayout.setTags(array);
            mLayout.tagsTabLayout.setOnTagClickListener(new TagGroup.OnTagClickListener() {
                @Override
                public void onTagClick(String tag) {
                    Toast.makeText(mActivity, tag, Toast.LENGTH_SHORT).show();
                }
            });
            mLayout.setPost(mPost);
            return mLayout.getRoot();

        } else {
            PostDetailsFragmentBinding mLayout;
            mLayout = PostDetailsFragmentBinding.inflate(inflater, container, false);
            Glide.with(mActivity).load(mPost.getImage_url()).into(mLayout.postCoverImageView);
            mLayout.postCoverImageView.setVisibility(View.VISIBLE);
            mLayout.shareFab.setOnClickListener(v -> startShareIntentForArticle(mActivity, mPost.getTitle(), mPost.getBody()));
            mLayout.setPost(mPost);
            return mLayout.getRoot();

        }
    }


    private void setupYoutubeView(VideoDetailsFragmentBinding mLayout) {
        getLifecycle().addObserver(mLayout.youtubeView);
        mLayout.youtubeView.initialize(
                initializedYouTubePlayer ->
                        initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                            @Override
                            public void onReady() {
                                String videoId = "wA38GCX4Tb0"; //Game of Thrones | Season 8 | Official Tease
                                initializedYouTubePlayer.loadVideo(videoId, 0);
                            }
                        }), true);
        mLayout.youtubeView.getPlayerUIController().showFullscreenButton(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PostDetailsViewModel.class);
        Toast.makeText(mActivity, mPostId, Toast.LENGTH_SHORT).show();
        // TODO: Use the ViewModel
    }

}
