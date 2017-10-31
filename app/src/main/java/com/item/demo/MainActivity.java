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
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.item.demo.activity.http.HttpOneActivity;
import com.item.demo.activity.http.PhotoActivity;
import com.item.demo.activity.http.PhotoTwoActivity;
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
                // 到TestOne
                startActivity(new Intent(MainActivity.this, TestOneActivity.class));
            }
        });
        Button btnTwo = (Button) findViewById(R.id.btn_two);
        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Refresh
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
        findViewById(R.id.btn_four).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 调用系统更换头像
                startActivity(new Intent(MainActivity.this, PhotoActivity.class));
            }
        });
        findViewById(R.id.btn_five).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 使用框架更换头像
                startActivity(new Intent(MainActivity.this, PhotoTwoActivity.class));
            }
        });
    }

    /**
     * 获取验证码
     */
    private void fetchSMSCode(String phone) {
        IRequest request = new BaseRequest(HttpConstants.VERIFICATION_CODE_URL + phone);
        OKHttpClientImp.getInstance().get(request, false, new IHttpClient.RequestCallBack() {
            @Override
            public void onSuccess(BaseResponse response) {
                Log.d("jiejie", response.getCode() + "   " + response.getData());
                Toast.makeText(MainActivity.this, "" + response.getCode(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code) {

            }
        });
    }
}
