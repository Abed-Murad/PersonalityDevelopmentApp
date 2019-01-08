package com.am.betterme.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.am.betterme.data.Post;
import com.am.betterme.databinding.CardArticleBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PostsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ARTICLE: {
                CardArticleBinding cardBinding = CardArticleBinding.inflate(mInflater, parent, false);
                return new ViewHolder(cardBinding);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolder && mPostList != null) {
            if (mPostList.size() != 0) {
                final Article article = getItem(position);
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.bindData(article);
            }

        }
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ARTICLE;
    }

    @Override
    public int getItemCount() {
        return mPostList == null ? 0 : mPostList.size();
    }

    public void add(Article article) {
        mPostList.add(article);
        notifyItemInserted(mPostList.size());
    }

    public void addAll(List<Article> articleList) {
        for (Article article : articleList) {
            add(article);
            Logger.d(article);
        }
    }

    private Article getItem(int position) {
        return mPostList.get(position);
    }

    public void SetOnItemClickListener(final OnArticleClickListener mItemClickListener) {
        this.mArticleClickListener = mItemClickListener;
    }

    public interface OnArticleClickListener {
        //TODO: use this method in the real project data not the parameter-less onItemClick()
        void onItemClick(View view, int position, Article model);

        void onBookmarkButtonClick(Article model);
    }

    public class PostHolder extends RecyclerView.ViewHolder {
        private final  mBinding;

        public ViewHolder(CardFeedArticleBinding itemView) {
            super(itemView.getRoot());
            this.mBinding = itemView;
            mBinding.getRoot().setOnClickListener(view -> mArticleClickListener.onItemClick(mBinding.getRoot(), getAdapterPosition(), getItem(getAdapterPosition())));//TODO: Remove this Dummy Data
            mBinding.addToFavoriteFab.setOnClickListener(view -> mArticleClickListener.onBookmarkButtonClick(getItem(getAdapterPosition())));//TODO: Remove this Dummy Data

        }

        private void bindData(Article article) {
            mBinding.setArticle(article);
            Glide.with(mContext).load(article.getUrlToImage()).into(mBinding.articleImage);
            mBinding.executePendingBindings();
        }
    }


}
