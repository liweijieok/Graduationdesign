package com.liweijie.design.graduation.gallery.bean;

/**
 * Created by liweijie on 2016/4/4.
 */
public class LeftmenuBean {
    private int icon;
    private int iconSelected;
    private String title;
    public LeftmenuBean(){}
    public LeftmenuBean(boolean isSelected, int icon, int iconSelected, String title) {
        this.icon = icon;
        this.iconSelected = iconSelected;
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIconSelected() {
        return iconSelected;
    }

    public void setIconSelected(int iconSelected) {
        this.iconSelected = iconSelected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
