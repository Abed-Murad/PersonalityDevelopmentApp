package com.am.betterme.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.am.betterme.data.model.Post;
import com.am.betterme.databinding.CardPostBinding;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostHolder> {
    private static final int TYPE_ARTICLE = 1;
    private static final int TYPE_VIDEO = 2;

    private Context mContext;
    private List<Post> mPostList;
    private LayoutInflater mInflater;
    private OnArticleClickListener mArticleClickListener;


    public PostsAdapter(Context context, OnArticleClickListener articleClickListener) {
        this.mContext = context;
        this.mPostList = new ArrayList<>();
        this.mArticleClickListener = articleClickListener;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardPostBinding cardBinding = CardPostBinding.inflate(mInflater, parent, false);
        return new PostHolder(cardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder postHolder, final int position) {
        final Post post = getItem(position);

        if (post.isIs_video()) {
            postHolder.mBinding.youtubeIconImageView.setVisibility(View.VISIBLE);

        } else {
            postHolder.mBinding.youtubeIconImageView.setVisibility(View.INVISIBLE);

        }
        postHolder.bindData(post);


    }


    @Override
    public int getItemCount() {
        return mPostList == null ? 0 : mPostList.size();
    }

    public void add(Post post) {
        mPostList.add(post);
        notifyItemInserted(mPostList.size());
    }

    public void addAll(List<Post> postList) {
        for (Post post : postList) {
            add(post);
        }
    }

    private Post getItem(int position) {
        return mPostList.get(position);
    }

    public void SetOnItemClickListener(final OnArticleClickListener mItemClickListener) {
        this.mArticleClickListener = mItemClickListener;
    }

    public interface OnArticleClickListener {
        void onItemClick(View view, int position, Post model);
    }

    class PostHolder extends RecyclerView.ViewHolder {
        private final CardPostBinding mBinding;

        PostHolder(CardPostBinding itemView) {
            super(itemView.getRoot());
            this.mBinding = itemView;
            mBinding.getRoot().setOnClickListener(view ->
                    mArticleClickListener.onItemClick(mBinding.getRoot(),
                            getAdapterPosition(), getItem(getAdapterPosition())));

        }

        private void bindData(Post post) {
            mBinding.setPost(post);
            Glide.with(mContext).load(post.getImage_url()).into(mBinding.imageView2);
            mBinding.executePendingBindings();
        }
    }


}
