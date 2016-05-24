package com.liweijie.design.graduation.gallery.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liweijie.design.graduation.gallery.R;
import com.liweijie.design.graduation.gallery.app.App;
import com.liweijie.design.graduation.gallery.bean.LeftmenuBean;
import com.liweijie.design.graduation.gallery.util.ResourceUtil;

import java.util.List;

import butterknife.Bind;

/**
 * Created by liweijie on 2016/5/21.
 */
public class LeftMenuAdapter extends BaseRecyclerAdapter<LeftmenuBean> {

    private int selected;

    public LeftMenuAdapter(List<LeftmenuBean> mDatas, int selected) {
        super(mDatas, 0, 0);
        this.selected = selected;
    }

    @Override
    public BaseRecyclerHolder getHolder(View itemtView) {
        return new LeftMenuHolder(itemtView);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_left_menu;
    }


    @Override
    public void converHolderView(BaseRecyclerHolder holder, LeftmenuBean leftmenuBean, int position) {
        LeftMenuHolder baseHolder = (LeftMenuHolder) holder;
        baseHolder.iv_left_menu_icon.setImageResource(leftmenuBean.getIcon());
        baseHolder.tv_left_menu_title.setText(leftmenuBean.getTitle());
        if (position == selected) {
            baseHolder.tv_left_menu_title.setTextColor(ResourceUtil.getColor(R.color.main_color));
        } else {
            baseHolder.tv_left_menu_title.setTextColor(ResourceUtil.getColor(R.color.white));
        }
    }

    class LeftMenuHolder extends BaseRecyclerHolder {
        @Bind(R.id.leftmenu_rllayout)
        LinearLayout leftmenu_rllayout;
        @Bind(R.id.iv_left_menu_icon)
        ImageView iv_left_menu_icon;
        @Bind(R.id.tv_left_menu_title)
        TextView tv_left_menu_title;


        public LeftMenuHolder(View itemView) {
            super(itemView);
        }
    }

    public void setSelected(int selected) {
        this.selected = selected;
        notifyDataSetChanged();
    }



}
