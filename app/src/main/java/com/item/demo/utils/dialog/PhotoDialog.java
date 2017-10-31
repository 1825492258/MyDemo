package com.item.demo.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.item.demo.R;

/**
 * Created by wuzongjie on 2017/10/30.
 * 建立一个弹窗，用户实现更换头像的弹窗
 */

public class PhotoDialog extends Dialog implements View.OnClickListener {
    private DisplayMetrics dm; // 屏幕分辨率
    private TextView tvCamera; // 拍照
    private TextView tvPhoto; // 相册
    private TextView tvCancel; // 取消
    private TakePhotoInterface photoInterface;

    public TakePhotoInterface getPhotoInterface() {
        return photoInterface;
    }

    public void setPhotoInterface(TakePhotoInterface photoInterface) {
        this.photoInterface = photoInterface;
    }

    public PhotoDialog(@NonNull Context context) {
        super(context, R.style.SheetDialogStyle);
        dm = context.getResources().getDisplayMetrics();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_photo_layout);
        initView();
    }

    private void initView() {
        Window dialogWindow = getWindow();
        assert dialogWindow != null;
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = dm.widthPixels;
        dialogWindow.setAttributes(lp);
        tvCamera = (TextView) findViewById(R.id.take_camera);
        tvPhoto = (TextView) findViewById(R.id.take_photo);
        tvCancel = (TextView) findViewById(R.id.take_cancel);
        tvCamera.setOnClickListener(this);
        tvPhoto.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.take_camera: // 拍照
                if (photoInterface != null) photoInterface.onTakeCamera();
                break;
            case R.id.take_photo: // 相册上传
                if (photoInterface != null) photoInterface.onTakePhoto();
                break;
            case R.id.take_cancel: // 取消

                break;
        }
        dismiss();
    }

    public interface TakePhotoInterface {
        void onTakeCamera();

        void onTakePhoto();
    }
}
