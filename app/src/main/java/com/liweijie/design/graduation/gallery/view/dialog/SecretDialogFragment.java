package com.liweijie.design.graduation.gallery.view.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.liweijie.design.graduation.gallery.R;
import com.liweijie.design.graduation.gallery.app.GalleryConstants;
import com.liweijie.design.graduation.gallery.util.SPUtil;
import com.liweijie.design.graduation.gallery.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liweijie on 2016/5/25.
 */
public class SecretDialogFragment extends android.support.v4.app.DialogFragment implements View.OnClickListener {

    private String passwort;

    @Bind(R.id.serret_dialog_pw)
    EditText serret_dialog_pw;
    @Bind(R.id.serret_dialog_pw1)
    EditText serret_dialog_pw1;

    public SecretDialogFragment() {

    }

    public static SecretDialogFragment getInstance() {
        return new SecretDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.secret_dialog_fragment, container, false);
        ButterKnife.bind(this, view);
        view.findViewById(R.id.serret_dialog_btn_cancel).setOnClickListener(this);
        view.findViewById(R.id.serret_dialog_btn_sure).setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        passwort = SPUtil.get(GalleryConstants.SECRET_PASSWORD, null);
        if (null != passwort) {
            serret_dialog_pw1.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    public interface OnEnterSecretListener {
        void enterResult(boolean isSuccess);
    }

    private OnEnterSecretListener listener;

    public void setEnterResultistener(OnEnterSecretListener listener) {
        this.listener = listener;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.serret_dialog_btn_cancel:
                dismiss();
                break;
            case R.id.serret_dialog_btn_sure:
                String p1 = serret_dialog_pw.getText().toString().trim();
                // 第一次，需要输入多重密码
                if (passwort == null) {
                    String p2 = serret_dialog_pw1.getText().toString().trim();
                    if (TextUtils.isEmpty(p1) || TextUtils.isEmpty(p2) || p1.length() <= 6 || !p1.equals(p2)) {
                        ToastUtil.showLong("设置密码错误");
                    } else {
                        SPUtil.set(GalleryConstants.SECRET_PASSWORD, p1);
                        listener.enterResult(true);
                        dismiss();
                    }
                } else {
                    if (passwort.equals(p1)) {
                        if (listener != null) {
                            listener.enterResult(true);
                            dismiss();
                        }
                    } else {
                        ToastUtil.showLong("输入密码错误");
                    }
                }
                break;
        }
    }


}
