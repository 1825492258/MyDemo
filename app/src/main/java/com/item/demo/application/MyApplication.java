package com.item.demo.application;

import android.app.Application;
import android.util.Log;

import com.item.demo.push.MyPushIntentService;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;

/**
 * 基类
 * Created by wuzongjie on 2017/10/23.
 */

public class MyApplication extends Application {
    private static MyApplication mApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        PushAgent mPushAgent = PushAgent.getInstance(this);
        // sdk 开启通知声音
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        // 注册推送服务,每次调用register都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                Log.d("jiejie", "device token====" + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.d("jiejie", "register failed" + s + "   " + s1);
            }
        });
        // 此处是完全自定义处理设置
        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
    }

    public static MyApplication getInstance() {
        return mApplication;
    }
}
