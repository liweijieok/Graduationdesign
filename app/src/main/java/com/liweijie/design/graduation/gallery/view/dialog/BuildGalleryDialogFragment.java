package com.liweijie.design.graduation.gallery.view.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import com.liweijie.design.graduation.gallery.R;
import com.liweijie.design.graduation.gallery.adapter.BuildDialogAdapter;
import com.liweijie.design.graduation.gallery.app.App;
import com.liweijie.design.graduation.gallery.bean.BuildDialogBean;
import com.liweijie.design.graduation.gallery.event.OnRecyclerViewItemClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liweijie on 2016/5/25.
 */
public class BuildGalleryDialogFragment extends android.support.v4.app.DialogFragment {
    @Bind(R.id.build_dialog_edt_name)
    EditText build_dialog_edt_name;
    @Bind(R.id.core_recycler_view)
    RecyclerView core_recycler_view;
    private BuildDialogAdapter mAdapter;
    private List<BuildDialogBean> mDatas;
    private String dir;
    private String file;


    public BuildGalleryDialogFragment() {

    }

    public static BuildGalleryDialogFragment getInstance() {
        return new BuildGalleryDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = inflater.inflate(R.layout.build_dialog_fragment, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDatas = new ArrayList<>();
        BuildDialogBean bean1 = new BuildDialogBean();
        // 获得手机内部存储控件的状态
        File dataFileDir = Environment.getDataDirectory();
        String dataMemory = getMemoryInfo(dataFileDir);
        bean1.setName("手机剩余大小(" + dataMemory + ")");
        bean1.setCheck(true);

        // 获得sd卡的内存状态
        File sdcardFileDir = Environment.getExternalStorageDirectory();
        String sdcardMemory = getMemoryInfo(sdcardFileDir);
        BuildDialogBean bean2 = new BuildDialogBean();
        bean2.setCheck(false);
        bean2.setName("SD卡剩余大小(" + sdcardMemory + ")");
        mDatas.add(bean1);
        mDatas.add(bean2);
        mAdapter = new BuildDialogAdapter(mDatas);
        core_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        core_recycler_view.setAdapter(mAdapter);
//        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<BuildDialogBean>() {
//            @Override
//            public void onItemClick(View view, BuildDialogBean o, int position) {
//                dir = o.getName();
//            }
//        });
    }

    /**
     * 根据路径获取内存状态
     *
     * @param path
     * @return
     */
    private String getMemoryInfo(File path) {
        // 获得一个磁盘状态对象
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();   // 获得一个扇区的大小
        //long totalBlocks = stat.getBlockCount();    // 获得扇区的总数
        long availableBlocks = stat.getAvailableBlocks();   // 获得可用的扇区数量
        // 总空间
        //String totalMemory =  Formatter.formatFileSize(App.me(), totalBlocks * blockSize);
        // 可用空间
        String availableMemory = Formatter.formatFileSize(App.me(), availableBlocks * blockSize);
        return availableMemory;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.build_dialog_btn_cancel, R.id.build_dialog_btn_sure})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.build_dialog_btn_sure:
                // 回调
                dismiss();
                if (listener != null) {
                    file = build_dialog_edt_name.getText().toString().trim();
                    String dir = mAdapter.getDir();
                    File folder = mDatas.get(0).getName().contains(dir)?Environment.getDataDirectory():Environment.getDataDirectory();
                    listener.buildDialog(file, folder);
                }
                break;
            case R.id.build_dialog_btn_cancel:
                dismiss();
        }
    }

    public interface OnBuildDialogListener {
        void buildDialog(String file, File dir);
    }

    private OnBuildDialogListener listener;

    public void setOnBuildDialogListener(OnBuildDialogListener listener) {
        this.listener = listener;
    }


}
