package com.item.demo.utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.item.demo.application.MyApplication;

/**
 * Created by wuzongjie on 2017/11/6.
 * 网络工具类
 */

public class NetWorkUtils {
    /**
     * 判断是否有网络连接
     *
     * @return boolean
     */
    public static boolean isNetworkConnected() {
        // 获取手机所有连接管理对象
        ConnectivityManager manager = (ConnectivityManager) MyApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取NetworkInfo对象
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 判断WIFI 是否可用
     *
     * @return boolean
     */
    public static boolean isWifiConnected() {
        // 获取手机所有连接管理对象
        ConnectivityManager manager = (ConnectivityManager) MyApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取NetworkInfo对象
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        // 判断判断NetworkInfo对象是否为空 并且类型是否为WIFI
        return networkInfo != null && networkInfo.getType() ==
                ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 判断MOBILE是否可用
     *
     * @return Boolean
     */
    public static boolean isMobileConnected() {
        ConnectivityManager manager = (ConnectivityManager) MyApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取NetworkInfo对象
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 获取连接类型
     * @return
     */
    public static int getNetWorkState(Context context) {
        // 获取手机所有连接管理对象
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取NetworkInfo对象
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return 1;
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return 2;
            } else {
                return 0;
            }
        }
        return 0;
    }

    /**
     * 判断GPS是否打开
     *
     * @return boolean
     */
    public static boolean isGPSEnabled() {
        LocationManager locationManager = ((LocationManager) MyApplication.getInstance()
                .getSystemService(Context.LOCATION_SERVICE));
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

}
