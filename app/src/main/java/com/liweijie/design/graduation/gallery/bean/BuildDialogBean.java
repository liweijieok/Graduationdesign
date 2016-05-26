package com.liweijie.design.graduation.gallery.bean;

/**
 * Created by liweijie on 2016/5/26.
 */
public class BuildDialogBean {
    public BuildDialogBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    private String name;
    private boolean isCheck;

    public BuildDialogBean(String name, boolean isCheck) {
        this.name = name;
        this.isCheck = isCheck;
    }
}
