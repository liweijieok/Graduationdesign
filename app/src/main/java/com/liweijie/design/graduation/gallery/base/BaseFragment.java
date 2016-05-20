package com.liweijie.design.graduation.gallery.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liweijie.design.graduation.gallery.impl.ComponentsImpl;
import com.liweijie.design.graduation.gallery.poxy.ComponentsImplProxy;
import com.liweijie.design.graduation.gallery.util.LG;

/**
 * Created by liweijie on 2016/5/18.
 * BaseFragment
 */
public abstract class BaseFragment extends Fragment implements ComponentsImpl {
    protected View mContentView;
    protected LG BL;
    private ComponentsImplProxy mComponentsImplProxy;

    public BaseFragment() {
        super();
        BL = new LG(this.getClass());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        BL.d("Fragment onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BL.d("Fragment onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BL.d("Fragment onCreateView");
        mComponentsImplProxy = new ComponentsImplProxy(this);
        int layoutId = mComponentsImplProxy.getLayoutId();
        mContentView = inflater.inflate(layoutId, null);
        mComponentsImplProxy.init(savedInstanceState);
        return mContentView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BL.d("Fragment onViewCreated");
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BL.d("Fragment onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        BL.d("Fragment onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        BL.d("Fragment onResume");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BL.d("Fragment onDestroyView");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        BL.d("Fragment onLowMemory");
    }

    @Override
    public void onPause() {
        super.onPause();
        BL.d("Fragment onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        BL.d("Fragment onStop");
    }


    @Override
    public void onDetach() {
        super.onDetach();
        BL.d("Fragment onDetach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BL.d("Fragment onDestroy");
        mComponentsImplProxy.destroyComponents();
    }
}
