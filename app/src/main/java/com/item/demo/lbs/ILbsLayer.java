package com.item.demo.lbs;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import java.util.List;

/**
 * Created by wuzongjie on 2017/10/27.
 * 定义地图服务通用抽象接口
 */

public interface ILbsLayer {
    /**
     * 获取地图
     */
    View getMapView();

    /**
     * 开始定位
     */
    void startLocationMap();

    /**
     * 设置位置变化的监听
     */
    void setLocationChangeListener(CommonLocationChangeListener locationChangeListener);

    /**
     * 移动相机到某个点
     *
     * @param locationInfo 地址
     * @param scale        缩放系数
     */
    void moveCameraToPoint(LocationInfo locationInfo, int scale);

    /**
     * 移动相机到2点之间的视野范围
     */
    void moveCameraTwo(LocationInfo locationInfo1, LocationInfo locationInfo2);

    /**
     * 获取当前城市
     */
    String getCity();

    /**
     * 添加更新标记点，包括位置，角度
     */
    void addOnUpdateMarker(LocationInfo locationInfo, Bitmap bitmap);
    /**
     * 搜索 输入内容自动提示
     * @param key 搜索地名
     * @param listener 监听
     */
    void poiSearch(String key, OnSearchedListener listener);

    /**
     * 搜索附近信息
     * @param info
     * @param listener
     */
    void poiBoundSearch(LocationInfo info, OnSearchedListener listener);
    /**
     * 生命周期函数
     */
    void onCreate(Bundle state);

    void onResume();

    void onSaveInstanceState(Bundle outState);

    void onPause();

    void onDestroy();

    // 清除地图所有marker
    void clearAllMarkers();

    interface CommonLocationChangeListener {
        void onLocationChanged(LocationInfo locationInfo);

        void onLocation(LocationInfo locationInfo);
    }

    /**
     * POI 搜索结果监听器
     */
    interface OnSearchedListener {
        void onSearched(List<LocationInfo> results);

        // void onError(int rCode);
    }
}
