package com.liweijie.design.graduation.gallery.bean.app;

import java.io.Serializable;

/**
 * Created by liweijie on 2016/5/18.
 */
public class GalleryUser implements Serializable{
    private int userID;
    private String userName;
    private String userPassword;
    private String userIcon;
    private String userPhone;

    public GalleryUser() {
    }

    public GalleryUser(int userID, String userName, String userPassword, String userIcon, String userPhone) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userIcon = userIcon;
        this.userPhone = userPhone;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
