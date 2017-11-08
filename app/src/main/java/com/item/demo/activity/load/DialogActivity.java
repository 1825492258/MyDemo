package com.item.demo.activity.load;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.item.demo.R;
import com.item.demo.utils.dialog.MyLoadDialog;
import com.item.demo.utils.dialog.widget.ShapeLoadingDialog;

public class DialogActivity extends AppCompatActivity implements View.OnClickListener {
    private ShapeLoadingDialog mDialog;
    private MyLoadDialog mLoadDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        findViewById(R.id.btn_dialog_dialog).setOnClickListener(this);
        findViewById(R.id.btn_dialog_one).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_dialog_dialog: // 显示一个弹窗
                if (mDialog == null) {
                    mDialog = new ShapeLoadingDialog.Builder(DialogActivity.this)
                            .loadText("加载中...")
                            .canceledOnTouchOutside(false)
                            .build();
                }
                mDialog.show();
                break;
            case R.id.btn_dialog_one: // 显示一个自定义的弹窗
                if(mLoadDialog == null){
                    mLoadDialog = new MyLoadDialog(DialogActivity.this);
                }
                mLoadDialog.show();
                break;
        }
    }
}
