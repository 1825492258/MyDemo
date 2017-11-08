package com.item.demo.mvpsample.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.item.demo.R;
import com.item.demo.activity.load.DialogActivity;
import com.item.demo.mvpsample.presenter.ITestMvpPresenter;
import com.item.demo.mvpsample.presenter.TestMapPresenterImpl;

/**
 * MVP 模式的初体验
 * (点击一个按钮，请求接口，出现弹窗，数据返回弹窗消失再将返回数据展示在TextView上)
 */
public class TestMvpActivity extends AppCompatActivity implements ITestMvpView, View.OnClickListener {

    private ITestMvpPresenter mPresenter;
    private Button btnHttp;
    private TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mvp);
        btnHttp = (Button) findViewById(R.id.btn_test_http);
        tvText = (TextView) findViewById(R.id.tv_test_text);
        mPresenter = new TestMapPresenterImpl(this);
        btnHttp.setOnClickListener(this);
        findViewById(R.id.btn_test_dialog).setOnClickListener(this);
    }

    /**
     * 返回的数据,TextView来展示
     *
     * @param info
     */
    @Override
    public void onInfoUpdate(String info) {
        tvText.setText(info);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void dissimssLoading() {

    }

    @Override
    public void showError(int code) {
        tvText.setText("返回码为错误的" + code);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_test_http: // MVP请求网络
                mPresenter.requestLogin("18356025758", "1111");
                break;
            case R.id.btn_test_dialog: // 去弹窗
                startActivity(new Intent(this, DialogActivity.class));
                break;
        }
    }
}