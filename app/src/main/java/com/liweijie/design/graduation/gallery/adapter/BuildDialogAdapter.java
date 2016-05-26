package com.liweijie.design.graduation.gallery.adapter;

import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.liweijie.design.graduation.gallery.R;
import com.liweijie.design.graduation.gallery.bean.BuildDialogBean;
import com.liweijie.design.graduation.gallery.util.L;

import java.util.List;

import butterknife.Bind;

/**
 * Created by liweijie on 2016/5/26.
 */
public class BuildDialogAdapter extends BaseRecyclerAdapter<BuildDialogBean> {
    public BuildDialogAdapter(List<BuildDialogBean> mDatas) {
        super(mDatas, 0, 0);
    }


    @Override
    public BaseRecyclerHolder getHolder(View itemtView) {
        return new BuildDialogHolder(itemtView);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_build_dialog;
    }

    private String dir;
    public String getDir(){
        return dir;
    }


    @Override
    public void converHolderView(BaseRecyclerHolder holder, final BuildDialogBean buildDialogBean, final int position) {
        final BuildDialogHolder localHolder = (BuildDialogHolder) holder;
        localHolder.item_build_dialog_tv1.setText(buildDialogBean.getName());
        localHolder.item_build_dialog_cb.setChecked(buildDialogBean.isCheck());
        if (buildDialogBean.isCheck()) {
            dir = buildDialogBean.getName();
        }
        localHolder.item_build_dialog_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.i("Check", position + "" + localHolder.item_build_dialog_cb.isChecked());
                if (position == 0) {
                    if (!localHolder.item_build_dialog_cb.isChecked()) {
                        localHolder.item_build_dialog_cb.setChecked(true);
                    }
                    getDatas().get(0).setCheck(true);
                    getDatas().get(1).setCheck(false);

                } else {
                    if (!localHolder.item_build_dialog_cb.isChecked()) {
                        localHolder.item_build_dialog_cb.setChecked(true);
                    }
                    getDatas().get(1).setCheck(true);
                    getDatas().get(0).setCheck(false);
                }
                notifyItemRangeChanged(0, 2);
                dir = buildDialogBean.getName();
            }
        });

    }

    class BuildDialogHolder extends BaseRecyclerHolder {
        @Bind(R.id.item_build_dialog_tv1)
        TextView item_build_dialog_tv1;
        @Bind(R.id.item_build_dialog_cb)
        CheckBox item_build_dialog_cb;

        public BuildDialogHolder(View itemView) {
            super(itemView);
        }
    }
}
