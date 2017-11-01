package com.item.demo.activity.refresh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.item.demo.R;
import com.item.demo.activity.base.BaseActivity;
import com.scwang.smartrefresh.layout.internal.ProgressDrawable;

public class RefreshActivity extends BaseActivity implements View.OnClickListener {

    private Button btnOne;
    private Button btnTwo;
    private Button btnThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        setBackBtn();
        setTitle("RefreshDemo使用");
        btnOne = (Button) findViewById(R.id.btn_text_one);
        btnTwo = (Button) findViewById(R.id.btn_text_two);
        btnThree = (Button)findViewById(R.id.btn_text_three);
        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_text_one: // 自定义Header
                startActivity(new Intent(this, BasicUsingActivity.class));
                break;
            case R.id.btn_text_two: // 基本使用
                startActivity(new Intent(this, TextRefreshActivity.class));
                break;
            case R.id.btn_text_three: // ListView 头部展示效果
                startActivity(new Intent(this,MyListActivity.class));
                break;
        }
    }
}
