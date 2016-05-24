package com.liweijie.design.graduation.gallery.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liweijie.design.graduation.gallery.R;
import com.liweijie.design.graduation.gallery.bean.ImageBean;
import com.liweijie.design.graduation.gallery.coer.MyImageLoader;
import com.liweijie.design.graduation.gallery.fragment.BaseGridFragment;

import java.util.List;

import butterknife.Bind;

/**
 * Created by liweijie on 2016/5/24.
 * 主页适配器
 */
public class CollectFragmentAdapter extends BaseRecyclerAdapter<ImageBean> {

    public CollectFragmentAdapter(List<ImageBean> mDatas) {
        super(mDatas, 1, 1);
    }

    @Override
    public BaseRecyclerHolder getHolder(View itemtView) {
        return new CollectHolder(itemtView);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_gallery_all_pic;
    }

    @Override
    public int getHeaderLayout() {
        return R.layout.core_blank_view;
    }

    @Override
    public int getBottomLayout() {
        return R.layout.core_blank_view;
    }

    @Override
    public BaseRecyclerHolder getBottomHolder(View itemtView) {
        return new CollectBottomHolder(itemtView);
    }

    @Override
    public BaseRecyclerHolder getHeaderHolder(View itemtView) {
        return new CollectHeaderHolder(itemtView);
    }

    @Override
    public void converHolderView(BaseRecyclerHolder holder, ImageBean imageBean, int position) {
        if (holder instanceof CollectHolder) {
            CollectHolder localHolder = (CollectHolder) holder;
            localHolder.collect_item_tv_cont.setText(String.valueOf(imageBean.getCount()));
            localHolder.collect_item_tv_name.setText("" + imageBean.getName());
            MyImageLoader.getInstance(2, MyImageLoader.Type.LIFO).loadImage(imageBean.getFirstImage(), localHolder.collect_item_iv_first);
        }
    }

    class CollectHolder extends BaseRecyclerHolder {
        @Bind(R.id.collect_item_iv_first)
        ImageView collect_item_iv_first;
        @Bind(R.id.collect_item_tv_name)
        TextView collect_item_tv_name;
        @Bind(R.id.collect_item_tv_cont)
        TextView collect_item_tv_cont;

        public CollectHolder(View itemView) {
            super(itemView);
        }
    }

    class CollectHeaderHolder extends BaseRecyclerHolder {
        public CollectHeaderHolder(View itemView) {
            super(itemView);
        }
    }

    class CollectBottomHolder extends BaseRecyclerHolder {
        public CollectBottomHolder(View itemView) {
            super(itemView);
        }
    }
}
