package com.item.demo.utils.push;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.item.demo.R;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageService;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by wuzongjie on 2017/10/27.
 * 参考博客：http://blog.csdn.net/nyb378680049/article/details/75038225
 * 完全自定义处理类
 */

public class MyPushIntentService extends UmengMessageService {

    private static final String TAG = "jiejie";

    @Override
    public void onMessage(Context context, Intent intent) {
        try {
            // 可以通过MESSAGE_BODY取得消息体
            String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
            UMessage msg = new UMessage(new JSONObject(message));
            Log.d(TAG, "message ==== " + message); // 消息体
            Log.d(TAG, "text=" + msg.text); // 通知内容
            // todo code to handle message here

            // 对完全自定义消息的处理方式，点击或忽略
            boolean isClickOrDismissed = true;
            if (isClickOrDismissed) {
                // 完全自定义消息的点击统计
                UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
            } else {
                // 完全自定义消息的忽略统计
                UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
            }
            if (isRunningForeground(this)) {
                Intent intent1 = new Intent();
//                intent1.setClass(context, TestActivity.class);
//                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                showNotifications(context, msg, intent1);
            }
            Log.d("jiejie", "-------" + isRunningForeground(this));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showNotifications(Context context, UMessage msg, Intent intent) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(msg.title)
                .setContentText(msg.text)
                .setTicker(msg.ticker)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                // .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher))
                .setColor(Color.GREEN)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        mNotificationManager.notify(100, builder.build());
    }

    /**
     * 判断是否运行在前台
     *
     * @param context
     */
    private boolean isRunningForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    return true; // 后台
                }
            }
        }
        return false;
    }
}
