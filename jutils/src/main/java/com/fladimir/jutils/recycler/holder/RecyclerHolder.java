package com.fladimir.jutils.recycler.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by NingJiang on 2017/6/22.
 * Class Note:通用ViewHolder
 */

public class RecyclerHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> mViews;

    public RecyclerHolder(View itemView) {
        super(itemView);
        //一般不会超过8个吧
        this.mViews = new SparseArray<>();
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     */
    private <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     */
    public RecyclerHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     */
    public RecyclerHolder setImageByUrl(Context context, int viewId, String url) {
        ImageView view = getView(viewId);
        Glide.with(context).load(url).into(view);
        return this;
    }
}
