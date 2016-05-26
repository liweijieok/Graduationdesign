package com.liweijie.design.graduation.gallery.adapter;

import android.view.View;
import android.widget.TextView;

import com.liweijie.design.graduation.gallery.R;

import java.util.List;

import butterknife.Bind;

/**
 *
 * Created by liweijie on 2016/5/26.
 *
 */
public class DialogItemAdapter extends BaseRecyclerAdapter<String> {

    public DialogItemAdapter(List<String> mDatas) {
        super(mDatas, 0, 0);
    }

    @Override
    public BaseRecyclerHolder getHolder(View itemtView) {
        return new DialogItemHolder(itemtView);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_menu_dialog;
    }

    @Override
    public void converHolderView(BaseRecyclerHolder holder, String s, int position) {
        DialogItemHolder localHolder = (DialogItemHolder) holder;
        localHolder.menu_item_name.setText(s + "");
    }

    class DialogItemHolder extends BaseRecyclerHolder {
        @Bind(R.id.menu_item_name)
        TextView menu_item_name;

        public DialogItemHolder(View itemView) {
            super(itemView);
        }
    }
}
