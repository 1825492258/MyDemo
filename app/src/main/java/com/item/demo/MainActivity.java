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
import com.item.demo.activity.map.PositionActivity;
import com.item.demo.activity.recycler.PullToRefreshUseActivity;
import com.item.demo.activity.recycler.TestOneActivity;
import com.item.demo.activity.recycler.adapter.HomeAdapter;
import com.item.demo.activity.refresh.BasicUsingActivity;
import com.item.demo.activity.refresh.RefreshActivity;
import com.item.demo.activity.refresh.TextRefreshActivity;
import com.item.demo.activity.ult.UltActivity;
import com.item.demo.entity.TestBean;
import com.item.demo.mvpsample.view.TestMvpActivity;
import com.item.demo.network.http.IHttpClient;
import com.item.demo.network.http.IRequest;
import com.item.demo.network.http.impl.BaseRequest;
import com.item.demo.network.http.impl.BaseResponse;
import com.item.demo.network.http.impl.OKHttpClientImp;
import com.item.demo.test.TestTwoActivity;
import com.item.demo.utils.HttpConstants;
import com.item.demo.utils.ToastUtils;
import com.item.demo.utils.databus.RegisterBus;
import com.item.demo.utils.databus.RxBus;
import com.umeng.message.PushAgent;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RxBus.getInstance().register(this);
        PushAgent.getInstance(this).onAppStart(); // 统计应用启动数据
        Log.d("jiejie", "main create");
        findViewById(R.id.btn_one).setOnClickListener(this);
        findViewById(R.id.btn_two).setOnClickListener(this);
        findViewById(R.id.btn_three).setOnClickListener(this);
        findViewById(R.id.btn_four).setOnClickListener(this);
        findViewById(R.id.btn_five).setOnClickListener(this);
        findViewById(R.id.btn_six).setOnClickListener(this);
        findViewById(R.id.btn_seven).setOnClickListener(this);
        findViewById(R.id.btn_eight).setOnClickListener(this);
        findViewById(R.id.btn_nine).setOnClickListener(this);
        findViewById(R.id.btn_ten).setOnClickListener(this);
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

    @RegisterBus
    public void onPush(TestBean bean) {
        Log.d("jiejie", "接受到的信息 ： " + bean);
        ToastUtils.showToast(bean.getMsg());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        com.luck.picture.lib.rxbus2.RxBus.getDefault().register(this);
        RxBus.getInstance().unRegister(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_one:
                // 到TestOne
                startActivity(new Intent(MainActivity.this, TestOneActivity.class));
                break;
            case R.id.btn_two://RefreshView
                startActivity(new Intent(MainActivity.this, RefreshActivity.class));
                break;
            case R.id.btn_three: // 测试网络请求
                //   fetchSMSCode("12121212");
                startActivity(new Intent(MainActivity.this, HttpOneActivity.class));
                break;
            case R.id.btn_four:
                // 调用系统更换头像
                startActivity(new Intent(MainActivity.this, PhotoActivity.class));
                break;
            case R.id.btn_five:
                // 使用框架更换头像
                startActivity(new Intent(MainActivity.this, PhotoTwoActivity.class));
                break;
            case R.id.btn_six:
                // 测试视频
                startActivity(new Intent(this, TestTwoActivity.class));
                break;
            case R.id.btn_seven: // 测试高德地图的封装
                startActivity(new Intent(this, MyMapActivity.class));
                break;
            case R.id.btn_eight: // 测试高德地图
                startActivity(new Intent(this, PositionActivity.class));
                break;
            case R.id.btn_nine: // UltraViewPager的使用
                startActivity(new Intent(this, UltActivity.class));
                break;
            case R.id.btn_ten: // MVP的初体验
                startActivity(new Intent(this, TestMvpActivity.class));
                break;
        }
    }
}
