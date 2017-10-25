package com.item.demo.utils;

import android.content.Context;
import android.widget.Toast;

import com.item.demo.application.MyApplication;

/**
 * Created by wuzongjie on 2017/10/23.
 */

public class ToastUtils {
    private static Toast mToast;//吐司

    /**
     * 自定义的Toast
     *
     * @param msg      信息
     */
    public static void showToast( String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.getInstance(), "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }


}
