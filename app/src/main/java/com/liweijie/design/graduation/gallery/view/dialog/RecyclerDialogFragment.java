package com.liweijie.design.graduation.gallery.view.dialog;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.liweijie.design.graduation.gallery.R;
import com.liweijie.design.graduation.gallery.adapter.DialogItemAdapter;
import com.liweijie.design.graduation.gallery.app.App;
import com.liweijie.design.graduation.gallery.event.OnRecyclerViewItemClickListener;
import com.liweijie.design.graduation.gallery.util.ResourceUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liweijie on 2016/5/26.
 */
public class RecyclerDialogFragment extends DialogFragment {
    private DialogItemAdapter mAdapter;
    @Bind(R.id.core_recycler_view)
    RecyclerView core_recycler_view;
    private View mContentView;


    private List<String> mDatas;
    private static final String DATA_KEY = "data_key";
    public RecyclerDialogFragment(){

    }
    public static RecyclerDialogFragment getInstance(ArrayList<String> mDatas){
        RecyclerDialogFragment dialogFragment = new RecyclerDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(DATA_KEY,mDatas);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContentView = inflater.inflate(R.layout.recycler_dialog_fragment, container, false);
        ButterKnife.bind(this,mContentView);
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDatas = getArguments().getStringArrayList(DATA_KEY);
        mAdapter = new DialogItemAdapter(mDatas);
        core_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        core_recycler_view.setAdapter(mAdapter);
        core_recycler_view.setBackgroundResource(R.drawable.build_dialog_bg);
        core_recycler_view.addItemDecoration(new HorizontalDividerItemDecoration.Builder(App.me())
                .color(Color.parseColor("#dddddd"))
                .size((int) ResourceUtil.getDimen(R.dimen.item_decoration_size)).build());
        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<String>() {
            @Override
            public void onItemClick(View view, String o, int position) {
                if (listener != null) {
                    listener.menuDialogClick(o,position);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    // 事件回调
    public interface OnMenuDialogListener{
        void menuDialogClick(String str, int position);
    }
    private OnMenuDialogListener listener;
    public void setOnMenuDialogListener( OnMenuDialogListener listener){
        this.listener = listener;
    }

}
