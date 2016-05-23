package com.liweijie.design.graduation.gallery.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liweijie.design.graduation.gallery.app.App;
import com.liweijie.design.graduation.gallery.event.OnRecyclerViewItemClickListener;
import com.liweijie.design.graduation.gallery.event.OnRecyclerViewItemLongClickListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liweijie on 2016/3/28.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHolder> implements View.OnClickListener,View.OnLongClickListener {

    private List<T> mDatas = new ArrayList<>();
    protected OnRecyclerViewItemClickListener mOnItemClickListener;
    protected OnRecyclerViewItemLongClickListener mOnItemLongClickListener;

    public BaseRecyclerAdapter(List<T> mDatas) {
        this.mDatas.addAll(mDatas);
    }

    @Override
    public final BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemtView = LayoutInflater.from(App.me()).inflate(getItemLayout(), parent, false);

        return getHolder(itemtView,viewType);
    }


    /**
     * 子类必须super
     * @param holder
     * @param position
     */
    @Override
    public final void onBindViewHolder(BaseRecyclerHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this);
        holder.itemView.setOnLongClickListener(this);
        converHolderView(holder,mDatas.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public abstract BaseRecyclerHolder getHolder(View itemtView,int viewType) ;

    public abstract int getItemLayout() ;

    public abstract void converHolderView(BaseRecyclerHolder holder, T t,int position);

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
       for(int i =0;i<list.size();i++) {
           T t = list.get(i);
           if(mDatas.contains(t)){
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
        return mDatas.size()<=position?mDatas.get(position):null;
    }

    public List<T> getDatas(){
        return mDatas;
    }


    // 事件相关
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    @Override
    public  void onClick(View v) {
        if (mOnItemClickListener != null) {
            int position = (int) v.getTag();
            mOnItemClickListener.onItemClick(v,mDatas.get(position),position);
        }
    }

    @Override
    public boolean onLongClick(View v){
        if (mOnItemLongClickListener != null) {
            int position = (int)v.getTag();
            mOnItemLongClickListener.onItemLongClick(v,mDatas.get(position),position);
        }
        return false;
    }

}
