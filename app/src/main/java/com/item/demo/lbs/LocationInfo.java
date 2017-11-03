package com.item.demo.lbs;

import java.io.Serializable;

/**
 * Created by wuzongjie on 2017/10/27.
 * 定位信息：实体类
 */

public class LocationInfo implements Serializable {
    private String key; // 唯一值
    private String name; // 地址名称
    private double latitude; // 精度
    private double longitude; // 维度
    private float rotation; // 角度
    private String district; // 详细地址

    public LocationInfo(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationInfo(String key, double latitude, double longitude) {
        this.key = key;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationInfo(String key, double latitude, double longitude, float rotation) {
        this.key = key;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rotation = rotation;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
