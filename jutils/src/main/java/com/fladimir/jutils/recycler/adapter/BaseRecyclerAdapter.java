package com.fladimir.jutils.recycler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fladimir.jutils.recycler.holder.RecyclerHolder;
import com.fladimir.jutils.recycler.impl.OnItemClickListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by NingJiang on 2017/6/22.
 * Class Note:RecycleView封装
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerHolder> {
    private List<T> mList;
    private Context mContext;
    protected final int mItemLayoutId;
    protected boolean isScrolling;
    private OnItemClickListener listener;

    public BaseRecyclerAdapter(RecyclerView recyclerView, Collection<T> datas, int itemLayoutId) {
        if (datas == null) {
            mList = new ArrayList<>();
        } else if (datas instanceof List) {
            mList = (List<T>) datas;
        } else {
            mList = new ArrayList<>(datas);
        }
        mItemLayoutId = itemLayoutId;
        mContext = recyclerView.getContext();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isScrolling = !(newState == RecyclerView.SCROLL_STATE_IDLE);
                if (!isScrolling) {
                    notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * Recycler适配器填充方法
     *
     * @param holder      viewholder
     * @param item        javabean
     * @param isScrolling RecyclerView是否正在滚动
     */
    public abstract void convert(RecyclerHolder holder, T item, int position, boolean isScrolling);

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View root = inflater.inflate(mItemLayoutId, viewGroup, false);
        return new RecyclerHolder(root);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder recyclerHolder, int i) {
        convert(recyclerHolder, mList.get(i), i, isScrolling);
        recyclerHolder.itemView.setOnClickListener(getOnClickListener(i));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        listener = l;
    }

    private View.OnClickListener getOnClickListener(final int position) {
        return v -> {
            if (listener != null && v != null) {
                listener.onItemClick(v, mList.get(position), position);
            }
        };
    }

    /**
     * 更新
     *
     * @param datas
     * @return
     */
    public BaseRecyclerAdapter<T> notify(Collection<T> datas) {
        if (datas == null) {
            mList = new ArrayList<>();
        } else if (datas instanceof List) {
            mList = (List<T>) datas;
        } else {
            mList = new ArrayList<>(datas);
        }
        return this;
    }
}
