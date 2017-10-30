package com.item.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.item.demo.activity.http.HttpOneActivity;
import com.item.demo.activity.map.MyMapActivity;
import com.item.demo.activity.recycler.PullToRefreshUseActivity;
import com.item.demo.activity.recycler.TestOneActivity;
import com.item.demo.activity.recycler.adapter.HomeAdapter;
import com.item.demo.activity.refresh.BasicUsingActivity;
import com.item.demo.activity.refresh.RefreshActivity;
import com.item.demo.activity.refresh.TextRefreshActivity;
import com.item.demo.network.http.IHttpClient;
import com.item.demo.network.http.IRequest;
import com.item.demo.network.http.impl.BaseRequest;
import com.item.demo.network.http.impl.BaseResponse;
import com.item.demo.network.http.impl.OKHttpClientImp;
import com.item.demo.utils.HttpConstants;
import com.umeng.message.PushAgent;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PushAgent.getInstance(this).onAppStart(); // 统计应用启动数据
        Log.d("jiejie", "main create");
        Button btn = (Button) findViewById(R.id.btn_one);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TestOneActivity.class));
            }
        });
        Button btnTwo = (Button) findViewById(R.id.btn_two);
        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, RefreshActivity.class));
            }
        });
        findViewById(R.id.btn_three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchSMSCode("12121212");
                startActivity(new Intent(MainActivity.this, HttpOneActivity.class));
            }
        });
    }

    /**
     * 获取验证码
     */
    private void fetchSMSCode(String phone) {
        IRequest request = new BaseRequest(HttpConstants.VERIFICATION_CODE_URL + phone);
        OKHttpClientImp.getInstance(this).myGet(request, false, new IHttpClient.MyCallBack() {
            @Override
            public void onMyCallBack(BaseResponse response) {
                Log.d("jiejie", response.getCode() + "   " + response.getData());
            }

            @Override
            public void onMyFial(int code) {
                Log.d("jiejie", "shibai" + code);
            }
        });
    }
}
