package com.liweijie.design.graduation.gallery.adapter;

import android.view.View;
import android.widget.TextView;

import com.liweijie.design.graduation.gallery.R;

import java.util.List;

import butterknife.Bind;

/**
 * Created by liweijie on 2016/5/26.
 *
 */
public class ImageDetailAdapter extends BaseRecyclerAdapter<String> {
    public ImageDetailAdapter(List<String> mDatas) {
        super(mDatas, 0, 0);
    }

    @Override
    public BaseRecyclerHolder getHolder(View itemtView) {
        return new ImageDetailHolder(itemtView);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_image_detail;
    }

    @Override
    public void converHolderView(BaseRecyclerHolder holder, String s, int position) {
        ImageDetailHolder localHolde = (ImageDetailHolder) holder;
        localHolde.item_image_detail_tv.setText(s);

    }

    class ImageDetailHolder extends BaseRecyclerHolder {
        @Bind(R.id.item_image_detail_tv)
        TextView item_image_detail_tv;

        public ImageDetailHolder(View itemView) {
            super(itemView);
        }
    }
}
