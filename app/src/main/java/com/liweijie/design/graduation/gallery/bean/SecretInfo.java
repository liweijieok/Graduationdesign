package com.liweijie.design.graduation.gallery.bean;

/**
 * Created by liweijie on 2016/5/18.
 */
public class SecretInfo {
    private String fileName;
    private String format;

    public SecretInfo() {

    }

    public SecretInfo(String fileName, String format) {
        this.fileName = fileName;
        this.format = format;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFormat() {
        return format;
    }

    @Override
    public String toString() {
        return "SecretInfo{" +
                "fileName='" + fileName + '\'' +
                ", format='" + format + '\'' +
                '}';
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
