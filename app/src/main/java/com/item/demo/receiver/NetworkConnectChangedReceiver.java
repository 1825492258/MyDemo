package com.item.demo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by wuzongjie on 2017/11/6.
 * 监听网络的改变状态，只有在用户操作网络连接开关(wifi, mobile)的时候接受广播
 */

public class NetworkConnectChangedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
