package com.taogu.replacerecyclerview;

import java.io.Serializable;
import java.util.List;

public class ShopBean implements Serializable {
    private String id;
    private String title;
    private String subTitle;
    private List<String> image;
    private String province;
    private String city;
    private String price;
    private List<String> detailsImage;
    private String  time;
    private String sendTime;

    public ShopBean() {
    }

    public ShopBean(String id, String title, String subTitle, List<String> image, String province, String city, String price, List<String> detailsImage, String time, String sendTime) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.image = image;
        this.province = province;
        this.city = city;
        this.price = price;
        this.detailsImage = detailsImage;
        this.time = time;
        this.sendTime = sendTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getDetailsImage() {
        return detailsImage;
    }

    public void setDetailsImage(List<String> detailsImage) {
        this.detailsImage = detailsImage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
