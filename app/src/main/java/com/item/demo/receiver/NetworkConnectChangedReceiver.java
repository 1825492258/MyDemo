package com.item.demo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.item.demo.activity.base.BaseActivity;
import com.item.demo.utils.NetWorkUtils;

/**
 * Created by wuzongjie on 2017/11/6.
 * 监听网络的改变状态，只有在用户操作网络连接开关(wifi, mobile)的时候接受广播
 */

public class NetworkConnectChangedReceiver extends BroadcastReceiver {

    private static final String TAG = "jiejie";
    public NetEvevt netEvevt = BaseActivity.evevt;
    @Override
    public void onReceive(Context context, Intent intent) {
        // 这个监听WiFi的打开与关闭，与WiFi的连接无关
        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            Log.d(TAG ,"------------");
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,0);
            int netState = NetWorkUtils.getNetWorkState(context);
            Log.d(TAG,"状态发生变化---" + netState + "           wifiState"+wifiState) ;
            if(netEvevt!=null){

                netEvevt.onNetChange(netState);
            }
        }
    }
    public interface NetEvevt{
        void onNetChange(int netMobile);
    }
}
