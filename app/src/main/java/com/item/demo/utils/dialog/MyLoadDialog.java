package com.item.demo.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.item.demo.R;

/**
 * Created by wuzongjie on 2017/11/8.
 * 自定义的一个弹窗
 * 注：设置屏幕背景不变暗 可以在style中添加如下代码则可
 * <item name="android:windowBackground"> @android:color/transparent </item>
 * <item name="android:backgroundDimEnabled">false</item>
 */

public class MyLoadDialog extends Dialog {

    private Context mContext;
    public MyLoadDialog(@NonNull Context context) {
        super(context, R.style.custom_dialog);
        this.mContext = context;
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_my_layout);
        ImageView imgView = (ImageView)findViewById(R.id.img_load);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.color.color_price);
        Glide.with(mContext).load(R.drawable.loading_large).apply(options).into(imgView);
    }
}
