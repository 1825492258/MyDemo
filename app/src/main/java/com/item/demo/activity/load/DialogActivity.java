package com.item.demo.activity.load;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.item.demo.R;
import com.item.demo.utils.dialog.widget.ShapeLoadingDialog;

public class DialogActivity extends AppCompatActivity {
    private ShapeLoadingDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        findViewById(R.id.btn_dialog_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDialog == null) {
                    mDialog = new ShapeLoadingDialog.Builder(DialogActivity.this)
                            .loadText("加载中...").build();
                }
                mDialog.show();
            }
        });
    }
}
