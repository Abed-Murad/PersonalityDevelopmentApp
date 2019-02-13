package com.am.betterme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.am.betterme.data.model.Post;
import com.am.betterme.databinding.CardPostBinding;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostHolder> {

    private Context mContext;
    private List<Post> mPostList;
    private LayoutInflater mInflater;
    private OnArticleClickListener mArticleClickListener;


    public PostsAdapter(Context context, OnArticleClickListener articleClickListener) {
        this.mContext = context;
        this.mPostList = new ArrayList<>();
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mArticleClickListener = articleClickListener;
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
        Logger.d(post.toString());
        postHolder.bind(post);
    }


    @Override
    public int getItemCount() {
        return mPostList == null ? 0 : mPostList.size();
    }

    private void clear() {
        mPostList.clear();
    }
    public void add(Post post) {
        mPostList.add(post);
    }

    public void addAll(List<Post> postList) {
        clear();
        for (Post post : postList) {
            add(post);
        }
        notifyDataSetChanged();
    }

    private Post getItem(int position) {
        return mPostList.get(position);
    }

    class PostHolder extends RecyclerView.ViewHolder {
        private final CardPostBinding mBinding;

        PostHolder(CardPostBinding itemView) {
            super(itemView.getRoot());

            this.mBinding = itemView;
            mBinding.getRoot().setOnClickListener(view ->
                    mArticleClickListener.onItemClick(
                              mBinding.getRoot()
                            , getAdapterPosition()
                            , getItem(getAdapterPosition()))
            );

        }

        private void bind(Post post) {
            mBinding.youtubeIconImageView.setVisibility(post.getBody().isEmpty() ? View.VISIBLE : View.INVISIBLE);
            mBinding.setPost(post);
            Glide.with(mContext).load(post.getImageUrl()).into(mBinding.imageView2);
            mBinding.executePendingBindings();
        }
    }

    public interface OnArticleClickListener {
        void onItemClick(View view, int position, Post model);
    }

}
