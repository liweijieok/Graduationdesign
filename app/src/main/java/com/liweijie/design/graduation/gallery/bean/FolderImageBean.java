package com.liweijie.design.graduation.gallery.bean;

/**
 * Created by liweijie on 2016/5/27.
 */
public class FolderImageBean {
    private String path;
    private boolean isSelected;
    public FolderImageBean(){

    }
    public FolderImageBean(String path, boolean isSelected) {
        this.path = path;
        this.isSelected = isSelected;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
