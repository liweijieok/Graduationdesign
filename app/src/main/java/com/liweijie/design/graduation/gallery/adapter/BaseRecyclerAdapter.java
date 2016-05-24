package com.liweijie.design.graduation.gallery.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liweijie.design.graduation.gallery.app.App;
import com.liweijie.design.graduation.gallery.event.OnRecyclerViewItemClickListener;
import com.liweijie.design.graduation.gallery.event.OnRecyclerViewItemLongClickListener;
import com.liweijie.design.graduation.gallery.util.L;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liweijie on 2016/3/28.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHolder> implements View.OnClickListener, View.OnLongClickListener {

    private List<T> mDatas = new ArrayList<>();
    protected OnRecyclerViewItemClickListener mOnItemClickListener;
    protected OnRecyclerViewItemLongClickListener mOnItemLongClickListener;

    private int mHeaderCount ;//头部View个数
    private int mBottomCount ;//底部View个数

    //三种类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_BOTTOM = 2;


    public BaseRecyclerAdapter(List<T> mDatas, int mHeaderCount, int mBottomCount) {
        this.mDatas.addAll(mDatas);
        this.mHeaderCount = mHeaderCount;
        this.mBottomCount = mBottomCount;
    }

    @Override
    public final BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemtView;
        if (viewType == ITEM_TYPE_HEADER) {
            itemtView = LayoutInflater.from(App.me()).inflate(getHeaderLayout(), parent, false);
            return getBottomHolder(itemtView);
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            itemtView = LayoutInflater.from(App.me()).inflate(getBottomLayout(), parent, false);
            return getHeaderHolder(itemtView);
        } else if (viewType == ITEM_TYPE_CONTENT) {
            itemtView = LayoutInflater.from(App.me()).inflate(getItemLayout(), parent, false);
            return getHolder(itemtView);
        }
        return null;

    }


    /**
     * 子类必须super
     *
     * @param holder
     * @param position
     */
    @Override
    public final void onBindViewHolder(BaseRecyclerHolder holder, int position) {
        if (isBottomView(position)) {
            converBottomHolderView(holder);
        } else if (isHeaderView(position)) {
            converHeaderHolderView(holder);
        } else {
            // 需要注意获取数据时候开始的位置，需要减去头部占据的size
            holder.itemView.setTag(position-mHeaderCount);
            holder.itemView.setOnClickListener(this);
            holder.itemView.setOnLongClickListener(this);
            converHolderView(holder, mDatas.get(position-mHeaderCount), position-mHeaderCount);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + mHeaderCount + mBottomCount;
    }

    public int getContentCount() {
        return mDatas.size();
    }

    //判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }

    //判断当前item是否是FooterView
    public boolean isBottomView(int position) {
        L.i("Bottom",position+"==" +(mHeaderCount+getContentCount()));
        return mBottomCount != 0 && position >= (mHeaderCount + getContentCount());
    }

    @Override
    public int getItemViewType(int position) {
        int dataItemCount = getContentCount();
        // 第一个
        if (mHeaderCount != 0 && position < mHeaderCount) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else if (mBottomCount != 0 && position >= (mHeaderCount + dataItemCount)) { //底部
            //底部View
            return ITEM_TYPE_BOTTOM;
        } else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }


    public abstract BaseRecyclerHolder getHolder(View itemtView);

    /**
     * 子类需要，子类实现
     *
     * @return
     */
    public BaseRecyclerHolder getBottomHolder(View itemtView) {
        return null;
    }

    /**
     * 子类需要，子类实现
     *
     * @return
     */
    public BaseRecyclerHolder getHeaderHolder(View itemtView) {
        return null;
    }

    public abstract int getItemLayout();

    /**
     * 子类需要，子类实现
     *
     * @return
     */
    public int getHeaderLayout() {
        return 0;
    }

    /**
     * 子类需要，子类实现
     *
     * @return
     */
    public int getBottomLayout() {
        return 0;
    }

    public abstract void converHolderView(BaseRecyclerHolder holder, T t, int position);

    /**
     * 子类需要，子类实现
     *
     * @return
     */
    public void converBottomHolderView(BaseRecyclerHolder holder) {

    }

    /**
     * 子类需要，子类实现
     *
     * @return
     */
    public void converHeaderHolderView(BaseRecyclerHolder holder) {

    }


    // 一般操作
    public void add(T t) {
        mDatas.add(t);
        this.notifyDataSetChanged();
    }

    public void addList(List<T> list) {
        mDatas.addAll(list);
        this.notifyDataSetChanged();
    }

    public void remove(T t) {
        mDatas.remove(t);
        this.notifyDataSetChanged();
    }

    public void removeList(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            T t = list.get(i);
            if (mDatas.contains(t)) {
                mDatas.remove(t);
            }
        }
        this.notifyDataSetChanged();
    }

    public void clear() {
        mDatas.clear();
        this.notifyDataSetChanged();
    }

    public void clearAndRefresh(List<T> list) {
        mDatas.clear();
        mDatas.addAll(list);
        this.notifyDataSetChanged();
    }

    public T get(int position) {
        return mDatas.size() <= position ? mDatas.get(position) : null;
    }

    public List<T> getDatas() {
        return mDatas;
    }


    // 事件相关
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    // 事件

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            int position = (int) v.getTag();
            mOnItemClickListener.onItemClick(v, mDatas.get(position), position);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemLongClickListener != null) {
            int position = (int) v.getTag();
            mOnItemLongClickListener.onItemLongClick(v, mDatas.get(position), position);
        }
        return false;
    }


}
