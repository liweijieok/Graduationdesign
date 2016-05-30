package com.liweijie.design.graduation.gallery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.liweijie.design.graduation.gallery.R;
import com.liweijie.design.graduation.gallery.activity.user.UserInfoActivity;
import com.liweijie.design.graduation.gallery.adapter.LeftMenuAdapter;
import com.liweijie.design.graduation.gallery.base.BaseFragment;
import com.liweijie.design.graduation.gallery.bean.LeftmenuBean;
import com.liweijie.design.graduation.gallery.event.OnRecyclerViewItemClickListener;
import com.liweijie.design.graduation.gallery.util.ResourceUtil;
import com.liweijie.design.graduation.gallery.util.ToastUtil;
import com.liweijie.design.graduation.gallery.view.dialog.SecretDialogFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by liweijie on 2016/5/19.
 *
 */
public class GalleryLeftMenuFragment extends BaseFragment implements SecretDialogFragment.OnEnterSecretListener {

    @Bind(R.id.core_recycler_view)
    RecyclerView core_recycler_view;
    @Bind(R.id.leftmenu_user_icon)
    CircleImageView leftmenu_user_icon;
    @Bind(R.id.leftmenu_user_tv)
    TextView leftmenu_user_tv;
    private ArrayList<LeftmenuBean> mLeftMenuData;
    private LeftMenuAdapter mAdapter;
    private SecretDialogFragment mDialog;


    private String[] titles;
    private int[] icons;

    private int mCurrentIndex;
    private static String KEY_INDEX = "key_index";

    public GalleryLeftMenuFragment() {
    }

    public static GalleryLeftMenuFragment getInstance(int mCurrentIndex) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_INDEX, mCurrentIndex);
        GalleryLeftMenuFragment leftMenuFragment = new GalleryLeftMenuFragment();
        leftMenuFragment.setArguments(bundle);
        return leftMenuFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.leftmenu_fragment;
    }

    @Override
    public void recoverAndBeforeInfalter(@Nullable Bundle savedInstanceState) {
        mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    public void beforeInit() {
        titles = ResourceUtil.getStringArray(R.array.left_menu_title);
        icons = new int[]{R.drawable.main_gallery, R.drawable.main_collect, R.drawable.main_secret, R.drawable.main_setting,
                R.drawable.main_about};
        mCurrentIndex = getArguments().getInt(KEY_INDEX);
    }

    @Override
    public void initEvent() {
        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<LeftmenuBean>() {
            @Override
            public void onItemClick(View view, LeftmenuBean bean, int position) {
                // 假如是保密，需要先输入密码
                if (position == 2) {
                    mDialog = (SecretDialogFragment) getFragmentManager().findFragmentByTag("secretDialog");
                    if (mDialog == null) {
                        mDialog = SecretDialogFragment.getInstance();
                        mDialog.setEnterResultistener(GalleryLeftMenuFragment.this);
                    }
                    mDialog.show(getFragmentManager(),"secretDialog");
                } else {
                    updateMenu(position, bean.getTitle());
                }

            }
        });
    }

    public void updateMenu(int position, String title) {
        if (mListener != null) {
            mCurrentIndex = position;
            mAdapter.setSelected(position);
            mListener.menuItemSelected(title, position);

        }
    }

    @Override
    public void initData() {
        mLeftMenuData = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            LeftmenuBean bean = new LeftmenuBean();
            bean.setIcon(icons[i]);
            bean.setTitle(titles[i]);
            mLeftMenuData.add(bean);
        }

        mAdapter = new LeftMenuAdapter(mLeftMenuData, mCurrentIndex);
        core_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        core_recycler_view.setAdapter(mAdapter);

    }

    @Override
    public void enterResult(boolean isSuccess) {
        if (isSuccess) {
            updateMenu(2, ResourceUtil.getStringArray(R.array.left_menu_title)[2]);
        }
    }

    // 回调
    public interface OnMenuItemSelectedListener {
        void menuItemSelected(String title, int position);
    }

    public OnMenuItemSelectedListener mListener;

    public void setOnMenuItemSelectedListener(OnMenuItemSelectedListener mListener) {
        this.mListener = mListener;
    }

    @OnClick(R.id.leftmenu_user_root)
    public void leftmenu_user_root(){
        Intent intent = new Intent(getActivity(), UserInfoActivity.class);
        startActivity(intent);
    }

}
