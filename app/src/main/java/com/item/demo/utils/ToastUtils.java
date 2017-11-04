package com.item.demo.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.item.demo.application.MyApplication;

import java.io.File;

/**
 * Created by wuzongjie on 2017/10/23.
 */

public class ToastUtils {
    private static Toast mToast;//吐司

    /**
     * 自定义的Toast
     *
     * @param msg 信息
     */
    public static void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.getInstance(), "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }

    /**
     * 判断是否有指定的权限
     *
     * @param activity    上下文
     * @param permissions 权限
     * @return 是否有权限
     */
    public static boolean hasPermission(AppCompatActivity activity, String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * DP转为PX
     * @param context 上下文对象
     * @param dpValue 转化的长度
     * @return PX
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f); // // 4.9->5 4.4->4
    }
    /**
     * 申请指定的权限
     *
     * @param activity    上下文
     * @param code        code
     * @param permissions 权限
     */
    public static void requestPermission(AppCompatActivity activity, int code, String... permissions) {
        if (Build.VERSION.SDK_INT >= 23) {
            activity.requestPermissions(permissions, code);
        }
    }

    /**
     * 检查文件是否存在
     *
     * @param dirPath 文件名
     * @return
     */
    public static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }
}
