package com.item.demo.application;

import android.app.Application;

/**
 * Created by wuzongjie on 2017/10/23.
 */

public class MyApplication extends Application{
    private static MyApplication mApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }
    public static MyApplication getInstance(){
        return mApplication;
    }
}
